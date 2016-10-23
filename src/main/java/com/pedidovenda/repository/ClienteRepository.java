package com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.service.NegocioException;
import com.pedidovenda.util.jpa.Transactional;

public class ClienteRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cliente guardar(Cliente cliente) {
		return manager.merge(cliente);
	}

	@Transactional
	public void remover(Cliente cliente) {
		try {
			cliente = getById(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído.");
		}
	}

	public List<Cliente> findAll() {
		return manager.createQuery("from Cliente", Cliente.class).getResultList();
	}

	public Cliente getById(Long Id) {
		return manager.find(Cliente.class, Id);
	}

	public List<Cliente> getByName(String nome) {
		return manager.createQuery("from Cliente where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
}
