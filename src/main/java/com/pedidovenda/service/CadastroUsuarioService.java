package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.data.UsuarioRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;

import static com.pedidovenda.util.security.Md5PasswordEncoder.encodePassword;

public class CadastroUsuarioService implements Serializable {

	@Inject
	private UsuarioRepository usuarios;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		Usuario usuarioExistente = usuarios.findById(usuario.getId()).orElse(null);

		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um Usuario com esses dados.");
		}

		return usuarios.save(encriptedUser(usuario));
	}

	public Usuario encriptedUser(Usuario usuario) {
		String encriptedPass = (!usuario.getSenha().isEmpty()) ? encodePassword(usuario.getSenha())
				: usuario.getSenha();
		usuario.setSenha(encriptedPass);
		return usuario;
	}

}