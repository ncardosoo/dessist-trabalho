package br.pucrs.segmanager.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.pucrs.segmanager.dao.SeguradoraDAO;
import br.pucrs.segmanager.model.Seguradora;

@FacesConverter(forClass = Seguradora.class)
public class SeguradoraConverter implements Converter {
	    
	@Override
	    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
	        if (value != null && !value.isEmpty()) {
	        	SeguradoraDAO dao = new SeguradoraDAO();
	        	Seguradora seguradora = new Seguradora();
	        	seguradora.setId(Long.valueOf(value));
	        	Seguradora aux = dao.findByOneFilter(seguradora);
	        	
	            return aux;
	        }
	        return null;
	    }

	    @Override
	    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
	        if (value instanceof Seguradora) {
	        	Seguradora entity = (Seguradora) value;
	            if (entity != null && entity instanceof Seguradora && entity.getId() != null) {
	                uiComponent.getAttributes().put( entity.getId().toString(), entity);
	                return entity.getId().toString();
	            }
	        }
	        return "";
	    }
}