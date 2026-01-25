package com.pedidovenda.repository.data;

import com.pedidovenda.model.Categoria;
import com.pedidovenda.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * Repositório Jakarta Data para Categoria
 */
@ApplicationScoped
public class CategoriaRepository extends AbstractRepository<Categoria> {

    public long countProdutosByCategoria(Long categoriaId) {
        return em.createQuery("SELECT c FROM Categoria c WHERE c.categoriaPai.id = :categoriaPaiId", Categoria.class)
                .setParameter("categoriaPaiId", categoriaId)
                .getResultList()
                .size();
    }

    public long countByCategoriaPai(Categoria categoria) {
        return em.createQuery("SELECT c FROM Categoria c WHERE c.categoriaPai.id = :categoriaPaiId", Categoria.class)
                .setParameter("categoriaPaiId", categoria.getId())
                .getResultList()
                .size();
    }

    public boolean existsByNome(String nome) {
        return false;
    }

    public List<Categoria> findCategoriasPai() {
        return em.createQuery("SELECT c.categoriaPai FROM Categoria c WHERE c.categoriaPai != null", Categoria.class)
                .getResultList();
    }

    public List<Categoria> findByCategoriaPai(Categoria categoriaPai) {
        return em.createQuery("SELECT c FROM Categoria c WHERE c.categoriaPai = :categoriaPai", Categoria.class)
                .setParameter("categoriaPai", categoriaPai)
                .getResultList();
    }
}
