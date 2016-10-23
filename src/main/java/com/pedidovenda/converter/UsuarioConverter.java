package com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.UsuarioRepository;
import com.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Usuario.class)
public class UsuarioConverter implements Converter {

	private UsuarioRepository usuarioRepository;
	
	public UsuarioConverter() {
		this.usuarioRepository = CDIServiceLocator.getBean(UsuarioRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? usuarioRepository.getById(new Long(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Usuario usuario = (Usuario) value;
			return ( usuario.getId() == null ) ? null : usuario.getId().toString();
		}
		return  "";
	}

}