package com.pedidovenda.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.UsuarioRepository;
import com.pedidovenda.util.cdi.CDIServiceLocator;

public class AppUserDetailService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsuarioRepository usuarios  = CDIServiceLocator.getBean(UsuarioRepository.class);
		Usuario usuario = usuarios.getByEmail(email);
		UsuarioSistema user = null;
		
		if(usuario != null){
			user = 	new UsuarioSistema(usuario, getGrupos(usuario));
		}
		return user ;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		usuario.getGrupos().forEach(grupo->{
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		});
		
		return authorities;
	}

}
