package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.ProdutoRepository;
import com.pedidovenda.repository.filter.ProdutoFilter;
import com.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoRepository produtos;
	
	private List<Produto> produtosFiltrados;
	private ProdutoFilter filtro; 
	private Produto produtoSelecionado;
	private Produto produto;
	
	public PesquisaProdutoBean(){
		filtro = new ProdutoFilter();
	}
	
	public void remover(){
		produtos.remover(produtoSelecionado);
		
		FacesUtil.addInfoMessage("Produto: " + produtoSelecionado.getSku() + " Removido com Sucesso.");
	}
	

	public void pesquisar(){
		produtosFiltrados = produtos.filtrados(filtro);
	}
	
	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}
	
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
	public boolean editando(){
		return this.produto.getId() != null;
	}
}
