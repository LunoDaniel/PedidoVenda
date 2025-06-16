package com.pedidovenda.model;

import lombok.Getter;

@Getter
public enum TipoPessoa {
	FISICA("Física"),
	JURIDICA("Jurídica");
	
	private String descricao;
	
	TipoPessoa(String descricao){
		this.descricao = descricao;
	}
	
}
