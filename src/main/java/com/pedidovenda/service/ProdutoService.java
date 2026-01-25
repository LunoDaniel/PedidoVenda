package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.data.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Serviço para operações de negócio com Produtos
 */
@ApplicationScoped
public class ProdutoService implements Serializable {

    @Inject
    private ProdutoRepository produtoRepository;

    /**
     * Salva ou atualiza um produto
     */
    @Transactional
    public Produto salvar(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    /**
     * Remove um produto
     */
    @Transactional
    public void remover(Long produtoId) {
        Optional<Produto> produto = Optional.ofNullable(produtoRepository.find(Produto.class, produtoId));
        if (produto.isPresent()) {
            // Verificar se produto tem itens de pedido antes de remover
            produtoRepository.removeById(produtoId, Produto.class);
        } else {
            throw new NegocioException("Produto não encontrado.");
        }
    }

    /**
     * Busca produto por ID
     */
    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtoRepository.find(Produto.class, id));
    }

    /**
     * Verifica se SKU já existe
     */
    public boolean skuJaExiste(String sku) {
        return produtoRepository.existsBySku(sku);
    }

    /**
     * Atualiza estoque do produto
     */
    @Transactional
    public void atualizarEstoque(Long produtoId, Integer quantidade) {
        produtoRepository.atualizarEstoque(produtoId, quantidade);
    }

    /**
     * Baixa estoque (para vendas)
     */
    @Transactional
    public void baixarEstoque(Long produtoId, Integer quantidade) {
        Optional<Produto> produtoOpt = buscarPorId(produtoId);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new NegocioException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            atualizarEstoque(produtoId, -quantidade);
        } else {
            throw new NegocioException("Produto não encontrado.");
        }
    }

    /**
     * Validações de negócio do produto
     */
    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new NegocioException("Nome do produto é obrigatório.");
        }

        if (produto.getSku() == null || produto.getSku().trim().isEmpty()) {
            throw new NegocioException("SKU do produto é obrigatório.");
        }

        if (produto.getValorUnitario() == null || produto.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("Valor unitário deve ser maior que zero.");
        }

        if (produto.getQuantidadeEstoque() == null || produto.getQuantidadeEstoque() < 0) {
            throw new NegocioException("Quantidade em estoque não pode ser negativa.");
        }

        if (produto.getCategoria() == null) {
            throw new NegocioException("Categoria do produto é obrigatória.");
        }

        // Verificar se SKU já existe (apenas para novos produtos ou mudança de SKU)
        if (produto.getId() == null || !produto.getSku().equals(buscarPorId(produto.getId()).map(Produto::getSku).orElse(""))) {
            if (skuJaExiste(produto.getSku())) {
                throw new NegocioException("Já existe um produto com este SKU.");
            }
        }
    }
}
