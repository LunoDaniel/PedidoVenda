package com.pedidovenda.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.Usuario;
import com.pedidovenda.model.vo.DataValor;
import com.pedidovenda.repository.filter.PedidoFilter;
import com.pedidovenda.service.NegocioException;
import com.pedidovenda.util.jpa.Transactional;

public class PedidoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

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

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Pedido.class).createAlias("cliente", "c").createAlias("usuario",
				"u");

		if (filtro.getNumeroDe() != null) {
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(filtro.getNomeUsuario())) {
			criteria.add(Restrictions.ilike("u.nome", filtro.getNomeUsuario(), MatchMode.ANYWHERE));
		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		return criteria.addOrder(Order.asc("id")).list();
	}

	public Pedido getById(Long id) {
		return manager.find(Pedido.class, id);
	}

	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor) {
		Session session = this.manager.unwrap(Session.class);
		
		numeroDeDias -= 1;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		Criteria criterios = session.createCriteria(Pedido.class);
		criterios
				.setProjection(Projections.projectionList()
						.add(Projections.sqlGroupProjection("date(data_criacao) as data", "date(data_criacao)",
								new String[] { "data" }, new Type[] { StandardBasicTypes.DATE }))
						.add(Projections.sum("valorTotal").as("valor")))
				.add(Restrictions.ge("dataCriacao", dataInicial.getTime()));

		if (criadoPor != null) {
			criterios.add(Restrictions.eq("usuario", criadoPor));
		}

		List<DataValor> valoresPorData = criterios
				.setResultTransformer(Transformers.aliasToBean(DataValor.class))
				.list();
		
		valoresPorData.forEach(valorData->{
			resultado.put(valorData.getData(), valorData.getValor());
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