package com.pedidovenda.events;

import com.pedidovenda.model.Pedido;

public class PedidoAlateradoEvent {
	
	private Pedido pedido;
	
	public PedidoAlateradoEvent(Pedido pedido){
		this.pedido = pedido;
	}
	
	public Pedido getPedido(){
		return this.pedido;
	}
}
