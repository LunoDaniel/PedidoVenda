package com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.repository.ClienteRepository;
import com.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Cliente.class)
public class ClienteConverter implements Converter {

	private ClienteRepository clienteRepository;
	
	public ClienteConverter() {
		this.clienteRepository = CDIServiceLocator.getBean(ClienteRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? clienteRepository.getById(new Long(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Cliente cliente = (Cliente) value;
			return ( cliente.getId() == null ) ? null : cliente.getId().toString();
		}
		return  "";
	}

}