package com.pedidovenda.converter;

import com.pedidovenda.model.Categoria;
import com.pedidovenda.repository.data.CategoriaDataRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(forClass=Categoria.class)
public class CategoriaConverter implements Converter<Categoria> {
	@Inject
	private CategoriaDataRepository categoria;

	@Override
	public Categoria getAsObject(FacesContext context, UIComponent component, String value) {
		return categoria.findById(Long.valueOf(value)).orElse(null);
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Categoria value) {
		return (value != null) ? value.getId().toString() : "";
	}

}