package com.pedidovenda.repository.data;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.AbstractRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Jakarta Data para Usuario
 */
@ApplicationScoped
public class UsuarioRepository extends AbstractRepository<Usuario> {

    public Usuario findByEmail(String email) {
        return em.createQuery(
                        "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public Optional<Usuario> findById(Long id) {
        return Optional.of(find(Usuario.class, id));
    }

    public boolean existsByEmail(String email) {
        Usuario usuario =  findByEmail(email);
        return usuario != null;
    }

    public List<Usuario> findAllByOrderByNome() {
        return em.createQuery(
                        "SELECT u FROM Usuario u ORDER BY u.nome", Usuario.class)
                .getResultList();
    }
}
