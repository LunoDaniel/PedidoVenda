package com.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidos;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}

		pedido.recalcularValorTotal();

		if (pedido.isNaoAlteravel()) {
			throw new NegocioException(
					"O Pedido não pode ser Alterado no Status: " + pedido.getStatus().getDescricao() + ".");
		}

		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("A Lista de Itens deve conter pelo menos UM item.");
		}

		if (pedido.isValorTotalNegativo()) {
			throw new NegocioException("Valor Total do Pedido não pode ser Negativo.");
		}

		return pedidos.guardar(pedido);
	}

}