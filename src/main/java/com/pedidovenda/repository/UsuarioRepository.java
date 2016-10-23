package com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.service.NegocioException;
import com.pedidovenda.util.jpa.Transactional;

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
