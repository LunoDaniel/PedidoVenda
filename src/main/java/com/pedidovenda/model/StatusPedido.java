package com.pedidovenda.model;

import lombok.Getter;

@Getter
public enum StatusPedido {
	ORCAMENTO("Orçamento"),
	EMITIDO("Emitido"),
	CANCELADO("Cancelado");
	
	private String descricao;
	
	StatusPedido(String descricao) {
		this.descricao  = descricao;
	}
}
