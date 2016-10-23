package com.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;

	@Inject
	private EstoqueService estoqueSerice;

	@Inject
	private PedidoRepository pedidos;

	@Transactional
	public Pedido emitir(Pedido pedido) {
		pedido = this.cadastroPedidoService.salvar(pedido);
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException(
					"O Pedido não pode ser Emitido com o Status: " + pedido.getStatus().getDescricao() + ".");
		}

		this.estoqueSerice.baixarItensEstoque(pedido);

		pedido.setStatus(StatusPedido.EMITIDO);
		return pedidos.guardar(pedido);
	}

}
