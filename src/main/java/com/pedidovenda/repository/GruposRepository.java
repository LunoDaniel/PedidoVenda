package com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.service.NegocioException;
import com.pedidovenda.util.jpa.Transactional;

public class GruposRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
