package com.pedidovenda.converter;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.data.PedidoDataRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter
public class PedidoConverter implements Converter<Pedido> {

	@Inject
	private PedidoDataRepository pedidoRepository;

	@Override
	public Pedido getAsObject(FacesContext context, UIComponent component, String value) {
		return pedidoRepository.findById(Long.valueOf(value)).orElse(null);
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Pedido pedido) {
		return ( pedido.getId() == null ) ? "" : pedido.getId().toString();
	}

}