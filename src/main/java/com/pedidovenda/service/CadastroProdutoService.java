package com.pedidovenda.service;


import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.data.ProdutoRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;

public class CadastroProdutoService implements Serializable {

	private final ProdutoRepository produtos;

    @Inject
    public CadastroProdutoService(ProdutoRepository produtos) {
        this.produtos = produtos;
    }

    @Transactional
	public Produto salvar(Produto produto) {
		Produto produtoExistente = produtos.findBySku(produto.getSku());
		
		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o SKU informado.");
		}
		
		return produtos.save(produto);
	}
	
}