package com.pedidovenda.service;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.data.PedidoDataRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;

public class EstoqueService implements Serializable {
	
	@Inject
	private PedidoDataRepository pedidos;

	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		var pedidoEntity = this.pedidos.findById(pedido.getId());

        pedidoEntity.ifPresent(value -> value.getItens().forEach(item -> {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }));

	}


	public void retornarItensEstoque(Pedido pedido) {
        var pedidoEntity = this.pedidos.findById(pedido.getId());

        pedidoEntity.ifPresent(value -> value.getItens().forEach(item->{
            item.getProduto().adicionarEstoque(item.getQuantidade());
        }));
	}
	
}
