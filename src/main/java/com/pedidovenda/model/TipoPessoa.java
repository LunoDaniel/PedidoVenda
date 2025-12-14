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

	public boolean isPessoaFisica(){
		return this == FISICA;
	}

	public static boolean isPessoaFisica(TipoPessoa tipo){
		return FISICA.equals(tipo);
	}
	
}
