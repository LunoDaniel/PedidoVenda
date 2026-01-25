package com.pedidovenda.controller;

import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.data.ProdutoRepository;
import com.pedidovenda.repository.filter.ProdutoFilter;
import com.pedidovenda.util.jsf.FacesUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PesquisaProdutoBean implements Serializable{


    @Inject
    private ProdutoRepository produtos;
    private List<Produto> produtosFiltrados;
	@Getter
    private final ProdutoFilter filtro;
	private Produto produtoSelecionado;
	private Produto produto;
	
	public PesquisaProdutoBean(){
		filtro = new ProdutoFilter();
	}
	
	public void remover(){
		produtos.remove(produtoSelecionado);
		
		FacesUtil.addInfoMessage("Produto: " + produtoSelecionado.getSku() + " Removido com Sucesso.");
	}
	

	public void pesquisar(){
		produtosFiltrados = produtos.byFilter(filtro);
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
