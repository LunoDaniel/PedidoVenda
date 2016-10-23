package com.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.pedidovenda.events.PedidoAlateradoEvent;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.service.EmissaoPedidoService;
import com.pedidovenda.util.jsf.FacesUtil;

@Named
@RequestScoped
public class EmissaoPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmissaoPedidoService emissaoPedidoService;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
    Event<PedidoAlateradoEvent> pedidoAlteradoEnvent;
	
	public void emitirPedido(){
		this.pedido.removerItemVazio();
		try {
			this.pedido = this.emissaoPedidoService.emitir(this.pedido);
			this.pedidoAlteradoEnvent.fire(new PedidoAlateradoEvent(this.pedido));
			FacesUtil.addInfoMessage("Pedido Emidito com Sucesso.");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}

}
