package com.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.util.jpa.Transactional;

public class EstoqueService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoRepository pedidos;

	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidos.getById(pedido.getId());
		
		pedido.getItens().forEach(item->{
			item.getProduto().baixarEstoque(item.getQuantidade());
		});
	}


	public void retornarItensEstoque(Pedido pedido) {
		pedido = this.pedidos.getById(pedido.getId());
		
		pedido.getItens().forEach(item->{
			item.getProduto().adicionarEstoque(item.getQuantidade());
		});
	}
	
}
