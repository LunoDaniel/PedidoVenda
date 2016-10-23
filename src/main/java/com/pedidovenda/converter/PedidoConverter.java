package com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Pedido.class)
public class PedidoConverter implements Converter {

	private PedidoRepository pedidoRepository;
	
	public PedidoConverter() {
		this.pedidoRepository = CDIServiceLocator.getBean(PedidoRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? pedidoRepository.getById(new Long(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Pedido pedido = (Pedido) value;
			return ( pedido.getId() == null ) ? null : pedido.getId().toString();
		}
		return  "";
	}

}