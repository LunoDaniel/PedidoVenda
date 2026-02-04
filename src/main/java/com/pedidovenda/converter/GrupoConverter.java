package com.pedidovenda.converter;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.repository.data.GrupoRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(forClass=Grupo.class)
public class GrupoConverter implements Converter<Grupo> {

	@Inject
	private GrupoRepository grupoRepository;
	
	@Override
	public Grupo getAsObject(FacesContext context, UIComponent component, String value) {
		return grupoRepository.find(Grupo.class, Long.valueOf(value));
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Grupo value) {
		return ( value.getId() == null ) ? "" : value.getId().toString();
	}

}