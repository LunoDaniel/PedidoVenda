package com.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.pedidovenda.events.PedidoAlateradoEvent;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.service.CancelamentoPedidoService;
import com.pedidovenda.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;

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
