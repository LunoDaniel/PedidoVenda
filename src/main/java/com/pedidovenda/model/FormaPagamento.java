package com.pedidovenda.model;

import lombok.Getter;

@Getter
public enum FormaPagamento {
	DINHEIRO("Dinheiro"),
	CARTAO_CREDITO("Cartão  de Crédito"),
	CARTAO_DEBITO("Cartão  de Débito"),
	CHEQUE("Cheque"),
	BOLETO_BANCARIO("Boleto  Bancário"),
	DEPOSITO_BANCARIO("Depósito Bancário");

	private String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}
}
