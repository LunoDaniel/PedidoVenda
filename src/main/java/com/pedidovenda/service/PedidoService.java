package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.repository.filter.PedidoFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações de negócio com Pedidos
 * Utiliza Jakarta Data Repository para persistência
 */
@ApplicationScoped
public class PedidoService implements Serializable {

    @Inject
    private PedidoRepository pedidoRepository;

    /**
     * Salva ou atualiza um pedido
     */
    @Transactional
    public Pedido salvar(Pedido pedido) {
        if (pedido.isNovo()) {
            pedido.setDataCriacao(LocalDateTime.now());
        }
        
        // Validações de negócio
        validarPedido(pedido);
        
        return pedidoRepository.save(pedido);
    }

    /**
     * Remove um pedido
     */
    @Transactional
    public void remover(Long pedidoId) {
        Optional<Pedido> pedido = Optional.ofNullable(pedidoRepository.find(Pedido.class, pedidoId));
        if (pedido.isPresent()) {
            if (pedido.get().isEmitido()) {
                throw new NegocioException("Pedido emitido não pode ser excluído.");
            }
            pedidoRepository.deleteById(pedidoId);
        } else {
            throw new NegocioException("Pedido não encontrado.");
        }
    }

    /**
     * Validações de negócio do pedido
     */
    private void validarPedido(Pedido pedido) {
        if (pedido.getCliente() == null) {
            throw new NegocioException("Cliente é obrigatório.");
        }
        
        if (pedido.getUsuario() == null) {
            throw new NegocioException("Usuário é obrigatório.");
        }
        
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new NegocioException("Pedido deve ter pelo menos um item.");
        }
        
        if (pedido.getDataEntrega() != null && pedido.getDataEntrega().isBefore(LocalDateTime.now())) {
            throw new NegocioException("Data de entrega não pode ser no passado.");
        }
    }

    /**
     * Aplica filtros aos pedidos (implementação simplificada)
     */
    private List<Pedido> filtrarPedidos(List<Pedido> pedidos, PedidoFilter filtro) {
        // Esta é uma implementação simplificada
        // Em uma implementação real, você criaria métodos específicos no repository
        // ou usaria Criteria API para filtros mais complexos
        return pedidos.stream()
                .filter(pedido -> {
                    if (filtro.getNumeroDe() != null && pedido.getId() < filtro.getNumeroDe()) {
                        return false;
                    }
                    if (filtro.getNumeroAte() != null && pedido.getId() > filtro.getNumeroAte()) {
                        return false;
                    }
                    // Adicionar outros filtros conforme necessário
                    return true;
                })
                .toList();
    }
}
