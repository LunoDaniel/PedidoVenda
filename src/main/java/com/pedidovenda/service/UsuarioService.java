package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.repository.data.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Optional;

/**
 * Serviço para operações de negócio com Usuários
 */
@ApplicationScoped
public class UsuarioService implements Serializable {

    @Inject
    private UsuarioRepository usuarioRepository;
    @Inject
    private PedidoRepository pedidoRepository;

    /**
     * Salva ou atualiza um usuário
     */
    @Transactional
    public Usuario salvar(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    /**
     * Remove um usuário
     */
    @Transactional
    public void remover(Long usuarioId) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isPresent()) {
            // Verificar se usuário tem pedidos antes de remover
            long pedidos = pedidoRepository.countPedidosByUsuario(usuarioId);
            if (pedidos > 0) {
                throw new NegocioException("Usuário não pode ser excluído pois possui pedidos associados.");
            }
            usuarioRepository.removeById(usuarioId, Usuario.class);
        } else {
            throw new NegocioException("Usuário não encontrado.");
        }
    }

    /**
     * Busca usuário por ID
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca usuário por email
     */
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    /**
     * Verifica se email já existe
     */
    public boolean emailJaExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }


    /**
     * Validações de negócio do usuário
     */
    private void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new NegocioException("Nome do usuário é obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new NegocioException("Email do usuário é obrigatório.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new NegocioException("Senha do usuário é obrigatória.");
        }

        if (usuario.getGrupos() == null || usuario.getGrupos().isEmpty()) {
            throw new NegocioException("Usuário deve pertencer a pelo menos um grupo.");
        }

        // Verificar se email já existe (apenas para novos usuários ou mudança de email)
        if (usuario.getId() == null || !usuario.getEmail().equals(buscarPorId(usuario.getId()).map(Usuario::getEmail).orElse(""))) {
            if (emailJaExiste(usuario.getEmail())) {
                throw new NegocioException("Já existe um usuário com este email.");
            }
        }

        // Validar formato do email
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new NegocioException("Formato de email inválido.");
        }
    }
}
