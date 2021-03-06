package br.pucrs.segmanager.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.pucrs.segmanager.dao.SeguradoDAO;
import br.pucrs.segmanager.model.Segurado;

@SuppressWarnings("rawtypes")
@ManagedBean(name="seguradoController")
@ViewScoped
public class SeguradoController {

	private Segurado segurado;
	private SeguradoDAO seguradoDAO;
	private String cpfEdicao;
	
	private List<Segurado> listSegurados;
	private List<Segurado> listSeguradosFiltrados;
	private boolean stExibeRelatorio = true;
	
	
	@PostConstruct
	public void init() {
		setSegurado(new Segurado());
		seguradoDAO = new SeguradoDAO();
		listSeguradosFiltrados = new ArrayList<Segurado>();
		setListSegurados(seguradoDAO.findAll(new Segurado()));
		cpfEdicao = "";
	}
	
	/**
	 * M�todo que persiste um Segurado
	 * @return 
	 */
	public String salvarSegurado() {
		
		boolean temErro = false;
		String msg = "";
		
		try {
			seguradoDAO.save(segurado);
		} catch (Exception e) {
			if(e.getMessage().contains("SQLIntegrityConstraintViolationException")) {
				msg = "J� existe um segurado cadastrado para este CPF!";
			}
			temErro = true;			
		}
		
		if(temErro) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.getFlash().setKeepMessages(true);
			FacesMessage message = null;
			if(msg.equals("")) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro inesperado, por favor, contate o suporte!");
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", msg);
			}
			RequestContext.getCurrentInstance().showMessageInDialog(message);
			throw new RuntimeException("");
		} else {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.getFlash().setKeepMessages(true); 
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro salvo com sucesso!" );
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
		
		segurado = new Segurado();
		return "segurados";
	}
	
	/**
	 * M�todo que remove um Segurado
	 * @return 
	 */
	public String removerSegurado() {
		boolean temErro = false;
		try {
			seguradoDAO.delete(segurado);
		} catch (Exception e) {
			temErro = true;
		}
		
		if(temErro) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.getFlash().setKeepMessages(true); 
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O registro n�o pode "
					+ "ser removido pois o mesmo possui depend�ncias!");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
//			throw new RuntimeException("");
		} else {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.getFlash().setKeepMessages(true); 
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro removido com sucesso!" );
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
		
		segurado = new Segurado();
		return "segurados";
	}
	
	/**
	 * Seleciona um segurado para edi��o.
	 * @param seg
	 * @return
	 */
	public String editarSegurado(Segurado seg)	{
		segurado = seg;
		stExibeRelatorio = false;
		cpfEdicao = seg.getCpf();
		
		return null;
	}
	
	
	public String adicionarSegurado()	{
		segurado = new Segurado();
		
		stExibeRelatorio = false;
		
		return null;
	}
	
	public String cancelar() {
		return "segurados";
	}
	
	public Segurado getSegurado() {
		return segurado;
	}

	public void setSegurado(Segurado segurado) {
		this.segurado = segurado;
	}

	public List<Segurado> getListSegurados() {
		return listSegurados;
	}

	public void setListSegurados(List<Segurado> listSegurados) {
		this.listSegurados = listSegurados;
	}

	public boolean isStExibeRelatorio() {
		return stExibeRelatorio;
	}

	public void setStExibeRelatorio(boolean stExibeRelatorio) {
		this.stExibeRelatorio = stExibeRelatorio;
	}

	public List<Segurado> getListSeguradosFiltrados() {
		return listSeguradosFiltrados;
	}

	public void setListSeguradosFiltrados(List<Segurado> listSeguradosFiltrados) {
		this.listSeguradosFiltrados = listSeguradosFiltrados;
	}
	
}
