package com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pedidovenda.model.Categoria;
import com.pedidovenda.repository.CategoriaRepository;
import com.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Categoria.class)
public class CategoriaConverter implements Converter {

	private CategoriaRepository categoria;
	
	public CategoriaConverter() {
		this.categoria = CDIServiceLocator.getBean(CategoriaRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? categoria.getById(new Long(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (value != null) ? ((Categoria) value).getId().toString() : "";
	}

}