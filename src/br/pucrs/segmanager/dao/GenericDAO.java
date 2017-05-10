package br.pucrs.segmanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * DAO gen�rico para as opera��es b�sicas atrav�s do EntityManager.
 * Os demais DAOs deve extender esta classe para que tenham acesso aos m�todos, por�m podem ter seus pr�prios
 * metodos pois possuem o EntityManager por heran�a. 
 * 
 * @author Nelson
 *
 * @param <E>
 */
public abstract class GenericDAO<E>  {
	
	@PersistenceContext
	public EntityManager em;
	
    public GenericDAO() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("seg");
        this.em = emf.createEntityManager();
    }
    
    @SuppressWarnings("unchecked")
	public List<E> findAll(Object o) {
    	
    	StringBuilder hql = new StringBuilder();
    	
    	hql.append("select o from ");
    	hql.append(o.getClass().getName());
    	hql.append(" o order by 1");
    	
    	Query query = em.createQuery(hql.toString());
    	
    	return query.getResultList();
    	
    }
    
    /**
     * M�todo que realiza a persist�ncia de uma entity passada por parametro
     * @param a
     */
    public void save(Object a) throws RollbackException {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
    }
    
    /**
     * M�todo que realiza a persist�ncia de uma entity passada por parametro
     * @param a
     */
    public void save(Object a, boolean sync) throws RollbackException {
            em.getTransaction().begin();
            em.lock(a, LockModeType.WRITE);
            em.persist(a);
            em.getTransaction().commit();
    }
    
    /**
     * M�todo que realiza a remo��o de uma entity passada por parametro
     * @param a
     */
    public void delete(Object a) {
    	
    	try {
            em.getTransaction().begin();
            em.remove(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    
	
}
