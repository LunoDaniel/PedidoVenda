package com.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.UsuarioRepository;
import com.pedidovenda.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

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
		Md5PasswordEncoder encrypter = new Md5PasswordEncoder();
		String encriptedPass = (!usuario.getSenha().isEmpty()) ? encrypter.encodePassword(usuario.getSenha(), null)
				: usuario.getSenha();
		usuario.setSenha(encriptedPass);
		return usuario;
	}

}