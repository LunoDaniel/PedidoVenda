package com.pedidovenda.converter;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.UsuarioRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter
public class UsuarioConverter implements Converter<Usuario> {

	@Inject
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? usuarioRepository.getById(Long.valueOf(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Usuario usuario) {
		return ( usuario.getId() == null ) ? "" : usuario.getId().toString();
	}

}