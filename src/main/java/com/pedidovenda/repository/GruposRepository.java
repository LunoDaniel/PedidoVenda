package com.pedidovenda.repository;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.exceptions.NegocioException;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;

@ApplicationScoped
public class GruposRepository {
	
	@Inject
	private EntityManager manager;
	
	public Grupo guardar(Grupo grupo) {
		return manager.merge(grupo);
	}
	
	@Transactional
	public void remover(Grupo grupo) {
		try {
			grupo = getById(grupo.getId());
			manager.remove(grupo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Grupo não pode ser excluído.");
		}
	}
	
	public List<Grupo> findAll(){
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
	
	public Grupo getById(Long Id){
		return manager.find(Grupo.class, Id);
	}
	
	public List<Grupo> getByName(String nome) {
		return manager.createQuery("from Grupo where upper(nome) like :nome", Grupo.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}	
}
