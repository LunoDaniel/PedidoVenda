package com.pedidovenda.repository.filter;

import com.pedidovenda.validation.SKU;
import lombok.Data;

@Data
public class ProdutoFilter {

	@SKU
	private String sku;
	private String nome;

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}

}