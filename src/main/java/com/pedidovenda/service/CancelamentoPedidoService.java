package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.data.PedidoDataRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;

public class CancelamentoPedidoService implements Serializable {

	@Inject
	private PedidoDataRepository pedidos;

	@Inject
	private EstoqueService estoqueService;

	@Transactional
	public Pedido cancelar(Pedido pedido) {
		pedidos.findById(pedido.getId());

		if (pedido.isNaoCancelavel()) {
			throw new NegocioException(
					"O Pedido não pode ser Cancelado no Status: " + pedido.getStatus().getDescricao() + ".");
		}
		
		if(pedido.isEmitido()){
			this.estoqueService.retornarItensEstoque(pedido);
		}
		pedido.setStatus(StatusPedido.CANCELADO);
		
		return this.pedidos.save(pedido);
	}

}
