package com.pedidovenda.converter;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.repository.GruposRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(value="grupoConverter")
public class GrupoConverter implements Converter<Grupo> {

	@Inject
	private GruposRepository grupoRepository;
	
	@Override
	public Grupo getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? grupoRepository.getById(Long.valueOf(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Grupo value) {
		return ( value.getId() == null ) ? "" : value.getId().toString();
	}

}