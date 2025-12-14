package com.pedidovenda.controller;

import com.pedidovenda.model.Categoria;
import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.data.CategoriaDataRepository;
import com.pedidovenda.service.CadastroProdutoService;
import com.pedidovenda.util.jsf.FacesUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	@Inject
	private CategoriaDataRepository categorias;
	
	@Inject
	private CadastroProdutoService cadastroProdutoService;
	
	@Getter
    private Produto produto;
	private Categoria categoriaPai;
	
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;
	
	public CadastroProdutoBean() {
		limpar();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			this.categoriasRaizes = this.categorias.findCategoriasPai();
			if (this.categoriaPai != null) {
				carregarSubcategorias();
			}
		}
	}
	
	public void carregarSubcategorias() {
		this.subcategorias = categorias.findByCategoriaPai(categoriaPai);
	}
	
	private void limpar() {
		produto = new Produto();
		categoriaPai = null;
		subcategorias = new ArrayList<>();
	}
	
	public void salvar() {
		this.produto = cadastroProdutoService.salvar(this.produto);
		this.limpar();
		
		FacesUtil.addInfoMessage("Produto salvo com sucesso!");
	}

    public void setProduto(Produto produto) {
		this.produto = produto;
		
		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}

    @NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

    public boolean isEditando() {
		return this.produto.getId() != null;
	}
	
	
}
