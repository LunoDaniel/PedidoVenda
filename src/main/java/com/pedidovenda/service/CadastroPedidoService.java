package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

public class CadastroPedidoService {

	@Inject
	private PedidoRepository pedidos;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(LocalDateTime.now());
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