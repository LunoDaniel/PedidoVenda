package com.pedidovenda.controller;

import com.pedidovenda.model.Grupo;
import com.pedidovenda.model.Usuario;
import com.pedidovenda.repository.data.GrupoRepository;
import com.pedidovenda.service.CadastroUsuarioService;
import com.pedidovenda.util.jsf.FacesUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
@AllArgsConstructor
public class CadastroUsuarioBean implements Serializable {

	@Inject
	private CadastroUsuarioService usuarioService;

	@Inject
	private GrupoRepository gruposRepository;

    @Getter @Setter
	private List<Grupo> grupos;
    @Getter @Setter
	private Usuario usuario;
    @Getter @Setter
	private List<Grupo> gruposSelecionados = new ArrayList<>();

	public CadastroUsuarioBean() {
		this.usuario = new Usuario();
		this.grupos = new ArrayList<>();
	}

	public void inicializar() {
		grupos = gruposRepository.findAllByOrderByNome();
	}

	public void salvar() {
		try {
			usuarioService.salvar(this.usuario);
			FacesUtil.addInfoMessage("Usuário Salvo com Sucesso.");
		} catch (Exception e) {
			FacesUtil.addErrorMessage("Erro ao Cadastrar o Usuário. " + e.getMessage());
		}

	}
}
