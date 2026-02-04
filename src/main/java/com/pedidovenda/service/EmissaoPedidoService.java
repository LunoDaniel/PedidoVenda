package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;

@ApplicationScoped
public class EmissaoPedidoService implements Serializable {

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
		return pedidos.save(pedido);
	}

}
