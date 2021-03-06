package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.GruposRepository;
import com.pedidovenda.service.CadastroUsuarioService;
import com.pedidovenda.util.jsf.FacesUtil;

@ViewScoped
@Named
public class CadastroUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroUsuarioService usuarioService;

	@Inject
	private GruposRepository gruposRepository;

	private List<Grupo> grupos;
	private Usuario usuario;
	private List<Grupo> gruposSelecionados = new ArrayList<>();

	public CadastroUsuarioBean() {
		this.usuario = new Usuario();
		this.grupos = new ArrayList<>();
	}

	public void inicializar() {
		grupos = gruposRepository.findAll();
	}

	public void salvar() {
		try {
			usuarioService.salvar(this.usuario);
			FacesUtil.addInfoMessage("Usuário Salvo com Sucesso.");
		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao Cadastrar o Usuário. " + e.getMessage());
		}

	}
	
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}
	
	public List<Grupo> getGruposSelecionados() {
		return this.gruposSelecionados;
	}

	public void setGruposSelecionados(List<Grupo> gruposSelecionados) {
		this.gruposSelecionados = gruposSelecionados;
	}

}
