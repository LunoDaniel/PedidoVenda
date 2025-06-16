package com.pedidovenda.service;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;

public class EstoqueService {
	
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
