package com.pedidovenda.repository.data;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Jakarta Data para Cliente
 */
@ApplicationScoped
public class ClienteRepository extends AbstractRepository<Cliente> {

    public List<Cliente> findByNomeLike(String nome) {
        return em.createQuery(
                        "SELECT c FROM Cliente c WHERE UPPER(c.nome) LIKE UPPER(:nome)", Cliente.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public Cliente findByEmail(@Email @NotBlank String email) {
        return em.createQuery(
                        "SELECT c FROM Cliente c WHERE UPPER(c.email) = UPPER(:email)", Cliente.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Optional<Cliente> findById(Long clienteId) {
        return Optional.of(em.createQuery(
                        "SELECT c FROM Cliente c WHERE UPPER(c.id) LIKE UPPER(:id)", Cliente.class)
                .setParameter("id", clienteId)
                .getSingleResult());
    }

    public boolean existsByDocumentoReceitaFederal(String documento) {
        return em.createQuery(
                        "SELECT c FROM Cliente c WHERE UPPER(c.documentoReceitaFederal) = :documentoReceitaFederal", Cliente.class)
                .setParameter("documentoReceitaFederal", documento)
                .getSingleResult() != null;
    }

    public boolean existsByEmail(String email) {
        Cliente cliente = findByEmail(email);
        return cliente != null;
    }

    public List<Cliente> findByNomeOrEmail(String name) {
        return em.createQuery(
                        "SELECT c FROM Cliente c WHERE UPPER(c.nome) LIKE UPPER(:nome) OR UPPER(c.email) LIKE UPPER(:name)", Cliente.class)
                .setParameter("nome", "%" + name + "%")
                .getResultList();
    }
}