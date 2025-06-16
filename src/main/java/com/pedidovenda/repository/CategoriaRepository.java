package com.pedidovenda.repository;

import com.pedidovenda.model.Categoria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class CategoriaRepository {
	
	@Inject
	private EntityManager manager;
	
	public List<Categoria> raizes(){
		return manager.createQuery("from Categoria where categoriaPai is null", Categoria.class).getResultList();
	}
	
	public Categoria getById(Long Id){
		return manager.find(Categoria.class, Id);
	}

	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return manager.createQuery("from Categoria where categoriaPai = :raiz", 
				Categoria.class).setParameter("raiz", categoriaPai).getResultList();
	}
	
	

}
