package com.pedidovenda.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.repository.GruposRepository;
import com.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Grupo.class, value="grupoConverter")
public class GrupoConverter implements Converter {

	private GruposRepository grupoRepository;
	
	public GrupoConverter() {
		this.grupoRepository = CDIServiceLocator.getBean(GruposRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? grupoRepository.getById(new Long(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Grupo grupo = (Grupo) value;
			return ( grupo.getId() == null ) ? null : grupo.getId().toString();
		}
		return  "";
	}

}