package com.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidos;

	@Inject
	private EstoqueService estoqueService;

	@Transactional
	public Pedido cancelar(Pedido pedido) {
		pedidos.getById(pedido.getId());

		if (pedido.isNaoCancelavel()) {
			throw new NegocioException(
					"O Pedido não pode ser Cancelado no Status: " + pedido.getStatus().getDescricao() + ".");
		}
		
		if(pedido.isEmitido()){
			this.estoqueService.retornarItensEstoque(pedido);
		}
		pedido.setStatus(StatusPedido.CANCELADO);
		
		return this.pedidos.guardar(pedido); 
	}

}
