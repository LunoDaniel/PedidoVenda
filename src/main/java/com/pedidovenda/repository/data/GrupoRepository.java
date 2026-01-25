package com.pedidovenda.repository.data;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * Repositório Jakarta Data para Grupo
 */
@ApplicationScoped
public class GrupoRepository extends AbstractRepository<Grupo> {
    public List<Grupo> findAllByOrderByNome() {
        return em.createQuery(
                        "SELECT c FROM Grupo c ORDER BY name", Grupo.class)
                .getResultList();
    }
}
