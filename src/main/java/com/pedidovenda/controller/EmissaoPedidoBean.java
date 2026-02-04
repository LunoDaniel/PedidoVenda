package com.pedidovenda.controller;

import com.pedidovenda.events.PedidoAlateradoEvent;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.service.EmissaoPedidoService;
import com.pedidovenda.util.jsf.FacesUtil;
import jakarta.enterprise.event.Event;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class EmissaoPedidoBean implements Serializable {
	
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
