package com.pedidovenda.repository;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.exceptions.NegocioException;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UsuarioRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	@Transactional
	public void remover(Usuario usuario) {
		try {
			usuario = getById(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído.");
		}
	}

	public List<Usuario> findAll() {
		return manager.createQuery("from Usuario", Usuario.class).getResultList();
	}

	public Usuario getById(Long Id) {
		return manager.find(Usuario.class, Id);
	}

	public List<Usuario> getByName(String nome) {
		return manager.createQuery("from Usuario where upper(nome) like :nome", Usuario.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	public Usuario getByEmail(String email) {
		try {
			return manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
}
