package com.pedidovenda.repository;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.filter.PedidoFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PedidoRepository extends AbstractRepository<Pedido> {


    public List<Pedido> findByStatus(StatusPedido status) {
        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE p.status = :status", Pedido.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Pedido> findByClientesId(Long clienteId) {
        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE p.cliente.id = :id", Pedido.class)
                .setParameter("id", clienteId)
                .getResultList();
    }

    public Pedido findByClienteId(Long clienteId) {
        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE p.cliente.id = :id", Pedido.class)
                .setParameter("id", clienteId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<Pedido> findByUsuarioId(Long usuarioId) {
        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE p.usuario.id = :id", Pedido.class)
                .setParameter("id", usuarioId)
                .getResultList();
    }

    public List<Pedido> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim) {
        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE p.dataCriacao BETWEEN :ini AND :fim", Pedido.class)
                .setParameter("ini", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

    public List<Pedido> findByClienteNomeContainingIgnoreCase(String nome) {
        return em.createQuery(
                        "SELECT p FROM Pedido p JOIN p.cliente c WHERE UPPER(c.nome) LIKE UPPER(:nome)",
                        Pedido.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public List<Pedido> findByUsuarioNomeContainingIgnoreCase(String nome) {
        return em.createQuery(
                        "SELECT p FROM Pedido p JOIN p.usuario u WHERE UPPER(u.nome) LIKE UPPER(:nome)",
                        Pedido.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public List<Pedido> findByStatusIn(List<StatusPedido> statuses) {
        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE p.status IN :statuses", Pedido.class)
                .setParameter("statuses", statuses)
                .getResultList();
    }

    public List<Pedido> findAllByOrderByDataCriacaoDesc() {
        return em.createQuery(
                        "SELECT p FROM Pedido p ORDER BY p.dataCriacao DESC", Pedido.class)
                .getResultList();
    }

    public long countByStatus(StatusPedido status) {
        return em.createQuery(
                        "SELECT COUNT(p) FROM Pedido p WHERE p.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }

    public List<Pedido> findPedidosDeHoje() {
        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE DATE(p.dataCriacao) = CURRENT_DATE",
                        Pedido.class)
                .getResultList();
    }

    public List<Pedido> findPedidosParaRelatorio(LocalDateTime inicio, LocalDateTime fim, Long usuarioId) {

        return em.createQuery(
                        "SELECT p FROM Pedido p WHERE p.dataCriacao BETWEEN :ini AND :fim " +
                                "AND p.usuario.id = :uid ORDER BY p.dataCriacao",
                        Pedido.class)
                .setParameter("ini", inicio)
                .setParameter("fim", fim)
                .setParameter("uid", usuarioId)
                .getResultList();
    }

    public List<Pedido> byFilter(PedidoFilter filtro) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Pedido p WHERE 1=1");

        if (filtro.getStatuses() != null) {
            jpql.append(" AND p.status IN (:status)");
        }

        TypedQuery<Pedido> query = em.createQuery(jpql.toString(), Pedido.class);

        if (filtro.getStatuses() != null) {
            String status = String.join( ",", filtro.getStatuses().stream().map(StatusPedido::name).toList());
            query.setParameter("status", status);
        }

        return query.getResultList();
    }

    public void deleteById(Long pedidoId) {
        em.remove(em.find(Pedido.class, pedidoId));
    }

    public long countPedidosByUsuario(Long usuarioId) {
        return em.createQuery(
                        "SELECT p FROM Pedido p JOIN Usuario u ON u.id = p.usuario.id" +
                                "WHERE p.usuario.id = :usuarioId ORDER BY p.dataCriacao",
                        Pedido.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList()
                .size();
    }
}
