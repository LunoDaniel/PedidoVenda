package com.pedidovenda.repository;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.filter.PedidoFilter;
import com.pedidovenda.exceptions.NegocioException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
public class PedidoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public Pedido guardar(Pedido pedido) {
		return manager.merge(pedido);
	}

	@Transactional
	public void remover(Pedido pedido) {
		try {
			pedido = getById(pedido.getId());
			manager.remove(pedido);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Pedido não pode ser excluído.");
		}
	}

	public List<Pedido> filtrados(PedidoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
		Root<Pedido> root = query.from(Pedido.class);

		// Join com as entidades relacionadas
		Join<Pedido, Cliente> clienteJoin = root.join("cliente", JoinType.INNER);
		Join<Pedido, Usuario> usuarioJoin = root.join("usuario", JoinType.INNER);

		List<Predicate> predicates = new ArrayList<>();

		if (filtro.getNumeroDe() != null) {
			predicates.add(builder.ge(root.get("id"), filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			predicates.add(builder.le(root.get("id"), filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			predicates.add(builder.like(
					builder.upper(clienteJoin.get("nome")),
					"%" + filtro.getNomeCliente().toUpperCase() + "%"));
		}

		if (StringUtils.isNotBlank(filtro.getNomeUsuario())) {
			predicates.add(builder.like(
					builder.upper(usuarioJoin.get("nome")),
					"%" + filtro.getNomeUsuario().toUpperCase() + "%"));
		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			predicates.add(root.get("status").in((Object[]) filtro.getStatuses()));
		}

		query.where(predicates.toArray(new Predicate[0]));
		query.orderBy(builder.asc(root.get("id")));

		return manager.createQuery(query).getResultList();
	}

	public Pedido getById(Long id) {
		return manager.find(Pedido.class, id);
	}

	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {
		numeroDeDias -= 1;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
		Root<Pedido> root = query.from(Pedido.class);

		// Função SQL para extrair a data
		Expression<Date> dataCriacaoDate = builder.function(
				"DATE",
				Date.class,
				root.get("dataCriacao"));

		query.multiselect(
				dataCriacaoDate.alias("data"),
				builder.sum(root.get("valorTotal")).alias("valor")
		);

		query.where(builder.greaterThanOrEqualTo(root.get("dataCriacao"), dataInicial.getTime()));

		if (criadoPor != null) {
			query.where(builder.equal(root.get("usuario"), criadoPor));
		}

		query.groupBy(dataCriacaoDate);
		query.orderBy(builder.asc(dataCriacaoDate));

		List<Tuple> resultados = manager.createQuery(query).getResultList();

		resultados.forEach(tuple -> {
			Date data = tuple.get("data", Date.class);
			BigDecimal valor = tuple.get("valor", BigDecimal.class);
			resultado.put(data, valor != null ? valor : BigDecimal.ZERO);
		});

		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();
		dataInicial = (Calendar) dataInicial.clone();

		for (int i = 0; i <= numeroDeDias; ++i) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}
}