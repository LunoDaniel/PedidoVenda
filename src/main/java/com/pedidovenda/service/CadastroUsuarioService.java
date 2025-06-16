package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;

import static com.pedidovenda.util.security.Md5PasswordEncoder.encodePassword;

public class CadastroUsuarioService {

	@Inject
	private UsuarioRepository usuarios;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		Usuario usuarioExistente = (usuario.getId() != null) ? usuarios.getById(usuario.getId()) : null;

		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um Usuario com esses dados.");
		}

		return usuarios.guardar(encriptedUser(usuario));
	}

	public Usuario encriptedUser(Usuario usuario) {
		String encriptedPass = (!usuario.getSenha().isEmpty()) ? encodePassword(usuario.getSenha())
				: usuario.getSenha();
		usuario.setSenha(encriptedPass);
		return usuario;
	}

}