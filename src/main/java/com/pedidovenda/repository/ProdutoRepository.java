package com.pedidovenda.repository;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.filter.ProdutoFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProdutoRepository {

	@Inject
	private EntityManager manager;

	@Transactional
	public Produto guardar(Produto produto) {
		return manager.merge(produto);
	}

	@Transactional
	public void remover(Produto produto) {
		try {
			produto = getById(produto.getId());
			manager.remove(produto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído.");
		}
	}

	public Produto porSku(String sku) {
		try {
			return manager.createQuery(
							"SELECT p FROM Produto p WHERE upper(p.sku) = :sku", Produto.class)
					.setParameter("sku", sku.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Produto> filtrados(ProdutoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getSku())) {
			predicates.add(builder.equal(root.get("sku"), filtro.getSku()));
		}

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(
					builder.upper(root.get("nome")),
					"%" + filtro.getNome().toUpperCase() + "%"));
		}

		criteria.where(predicates.toArray(new Predicate[0]));
		criteria.orderBy(builder.asc(root.get("nome")));

		return manager.createQuery(criteria).getResultList();
	}

	public List<Produto> porNome(String nome) {
		return manager.createQuery(
						"SELECT p FROM Produto p WHERE upper(p.nome) like :nome", Produto.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}

	public Produto getById(Long id) {
		return manager.find(Produto.class, id);
	}

	public List<Produto> getByNome(String nome) {
		return manager.createQuery(
						"SELECT p FROM Produto p WHERE upper(p.nome) like :nome", Produto.class)
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getResultList();
	}
}