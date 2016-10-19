package com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pedidovenda.model.Categoria;

public class CategoriaRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
