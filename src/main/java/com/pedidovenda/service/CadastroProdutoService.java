package com.pedidovenda.service;


import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;

import java.io.Serializable;

public class CadastroProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtos;
	
	@Transactional
	public Produto salvar(Produto produto) {
		Produto produtoExistente = produtos.porSku(produto.getSku());
		
		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o SKU informado.");
		}
		
		return produtos.guardar(produto);
	}
	
}