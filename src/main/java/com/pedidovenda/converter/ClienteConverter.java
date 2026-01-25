package com.pedidovenda.converter;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.repository.data.ClienteRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(forClass=Cliente.class)
public class ClienteConverter implements Converter<Cliente> {

	@Inject
	private ClienteRepository clienteRepository;

	@Override
	public Cliente getAsObject(FacesContext context, UIComponent component, String value) {
		return clienteRepository.findById(Long.valueOf(value)).orElse(null);
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Cliente value) {
		return ( value == null ) ? "" : value.getId().toString();
	}

}