package com.pedidovenda.service;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Optional;

@ApplicationScoped
public class EstoqueService implements Serializable {
	
	@Inject
	private PedidoRepository pedidos;

	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		var pedidoEntity = Optional.ofNullable(this.pedidos.find(Pedido.class, pedido.getId()));

        pedidoEntity.ifPresent(value -> value.getItens().forEach(item -> {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }));

	}


	public void retornarItensEstoque(Pedido pedido) {
        var pedidoEntity = Optional.ofNullable(this.pedidos.find(Pedido.class, pedido.getId()));

        pedidoEntity.ifPresent(value -> value.getItens().forEach(item->{
            item.getProduto().adicionarEstoque(item.getQuantidade());
        }));
	}
	
}
