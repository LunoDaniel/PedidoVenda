package com.pedidovenda.converter;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.PedidoRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter
public class PedidoConverter implements Converter<Pedido> {

	@Inject
	private PedidoRepository pedidoRepository;

	@Override
	public Pedido getAsObject(FacesContext context, UIComponent component, String value) {
		return pedidoRepository.find(Pedido.class, Long.valueOf(value));
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Pedido pedido) {
		return ( pedido.getId() == null ) ? "" : pedido.getId().toString();
	}

}