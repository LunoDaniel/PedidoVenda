package com.pedidovenda.repository.filter;

import com.pedidovenda.validation.SKU;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoFilter {

	@SKU
	private String sku;
	private String nome;

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}

}