package com.pedidovenda.repository;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.exceptions.NegocioException;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;

@ApplicationScoped
public class ClienteRepository {

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
