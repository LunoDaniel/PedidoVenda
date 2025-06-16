package com.pedidovenda.repository;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.model.Endereco;
import com.pedidovenda.exceptions.NegocioException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EnderecoRepository {

	@Inject
	private EntityManager manager;

	public Endereco guardar(Endereco endereco) {
		return manager.merge(endereco);
	}

	@Transactional
	public void remover(Endereco endereco) {
		try {
			endereco = getById(endereco.getId());
			manager.remove(endereco);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Endereco não pode ser excluído.");
		}
	}

	public List<Endereco> findAll() {
		return manager.createQuery("from Endereco", Endereco.class).getResultList();
	}

	public Endereco getById(Long Id) {
		return manager.find(Endereco.class, Id);
	}

	public List<Endereco> getByCep(String cep) {
		return manager.createQuery("from Endereco where cep = :cep", Endereco.class)
				.setParameter("cep", cep).getResultList();
	}
	
	public List<Endereco> getByCliente(Cliente cliente){
		return manager.createQuery("from Endereco where cliente_id = :cliente_id", Endereco.class)
				.setParameter("cliente_id", cliente.getId()).getResultList();
	}
	
	public Cliente guardaEnderecosCliente(List<Endereco> enderecos, Cliente cliente) {
		if (cliente != null) {
			enderecos.forEach(endereco -> {
				if (endereco != null) {
					endereco.setCliente(cliente);
					this.manager.merge(endereco);
				}
			});
		}

		return cliente;
	}
}
