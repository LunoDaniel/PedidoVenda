package com.pedidovenda.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class AbstractRepository<T> {

    @PersistenceContext
    protected EntityManager em;

    public T save(T entity) {
        return em.merge(entity);
    }

    public void remove(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public void removeById(Long id, Class<T> clazz) {
        T entity = find(clazz, id);
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public T find(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }
}