package com.pedidovenda.controller;

import com.pedidovenda.events.PedidoAlateradoEvent;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.service.CancelamentoPedidoService;
import com.pedidovenda.util.jsf.FacesUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CancelamentoPedidoBean {

	@Inject
	private CancelamentoPedidoService cancelamentoPedidoService;
	
	@Inject
	Event<PedidoAlateradoEvent> pedidoAlteradoEvent;
	
	@Inject
	@PedidoEdicao
	Pedido pedido;
	
	public void cancelarPedido(){
		this.cancelamentoPedidoService.cancelar(this.pedido);
		this.pedidoAlteradoEvent.fire(new PedidoAlateradoEvent(this.pedido));
		
		FacesUtil.addInfoMessage("Pedido Cancelado com Sucesso.");
	}
	
}
