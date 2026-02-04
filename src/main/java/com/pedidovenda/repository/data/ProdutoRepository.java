package com.pedidovenda.repository.data;

import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.AbstractRepository;
import com.pedidovenda.repository.filter.ProdutoFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Repositório Jakarta Data para Produto
 */
@ApplicationScoped
public class ProdutoRepository extends AbstractRepository<Produto> {

    public List<Produto> findAll() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    public Produto findBySku(@NotBlank String sku) {
        return em.createQuery("SELECT p FROM Produto p WHERE p.sku = :sku", Produto.class)
                .setParameter("sku", sku)
                .getSingleResult();
    }

    public boolean existsBySku(String sku) {
        return findBySku(sku) != null;
    }

    public void atualizarEstoque(Long produtoId, Integer quantidade) {
        Produto produto = find(Produto.class, produtoId);
        produto.setQuantidadeEstoque(quantidade);
        save(produto);
    }

    public List<Produto> byFilter(ProdutoFilter filtro) {
        return em.createQuery("SELECT p FROM Produto p WHERE 1=1 OR (p.sku = :sku) OR (p.nome = :nome)", Produto.class)
                .setParameter("sku", filtro.getSku())
                .setParameter("nome", filtro.getNome())
                .getResultList();
    }

    public long countProdutosByCategoria(Long categoriaId) {
        return em.createQuery("SELECT p FROM Produto p JOIN Categoria c ON c.id = p.categoria.id WHERE c.id=:categoriaId", Produto.class)
                .setParameter("categoriaId", categoriaId)
                .getResultList()
                .size();
    }

}