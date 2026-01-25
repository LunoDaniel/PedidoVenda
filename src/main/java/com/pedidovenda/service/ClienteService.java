package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Cliente;
import com.pedidovenda.repository.data.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Optional;

/**
 * Serviço para operações de negócio com Clientes
 */
@ApplicationScoped
public class ClienteService implements Serializable {

    @Inject
    private ClienteRepository clienteRepository;

    /**
     * Salva ou atualiza um cliente
     */
    @Transactional
    public Cliente salvar(Cliente cliente) {
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    /**
     * Remove um cliente
     */
    @Transactional
    public void remover(Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            // Verificar se cliente tem pedidos antes de remover
            clienteRepository.removeById(clienteId, Cliente.class);
        } else {
            throw new NegocioException("Cliente não encontrado.");
        }
    }

    /**
     * Busca cliente por ID
     */
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }


    /**
     * Verifica se email já existe
     */
    public boolean emailJaExiste(String email) {
        return clienteRepository.existsByEmail(email);
    }

    /**
     * Verifica se documento já existe
     */
    public boolean documentoJaExiste(String documento) {
        return clienteRepository.existsByDocumentoReceitaFederal(documento);
    }

    /**
     * Validações de negócio do cliente
     */
    private void validarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new NegocioException("Nome do cliente é obrigatório.");
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new NegocioException("Email do cliente é obrigatório.");
        }

        if (cliente.getDocumentoReceitaFederal() == null || cliente.getDocumentoReceitaFederal().trim().isEmpty()) {
            throw new NegocioException("Documento do cliente é obrigatório.");
        }

        // Verificar se email já existe (apenas para novos clientes ou mudança de email)
        if (cliente.getId() == null || !cliente.getEmail().equals(buscarPorId(cliente.getId()).map(Cliente::getEmail).orElse(""))) {
            if (emailJaExiste(cliente.getEmail())) {
                throw new NegocioException("Já existe um cliente com este email.");
            }
        }

        // Verificar se documento já existe (apenas para novos clientes ou mudança de documento)
        if (cliente.getId() == null || !cliente.getDocumentoReceitaFederal().equals(buscarPorId(cliente.getId()).map(Cliente::getDocumentoReceitaFederal).orElse(""))) {
            if (documentoJaExiste(cliente.getDocumentoReceitaFederal())) {
                throw new NegocioException("Já existe um cliente com este documento.");
            }
        }
    }
}
