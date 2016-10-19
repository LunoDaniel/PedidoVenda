package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.pedidovenda.model.EnderecoEntrega;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.Usuario;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Pedido pedido;
	private List<Integer> itens;
	
	public void salvar(){
		
	}
	
	public CadastroPedidoBean(){
		this.pedido = new Pedido();
		this.pedido.setEnderecoEntrega(new EnderecoEntrega());
		this.pedido.setUsuario(new Usuario());
		this.itens = new ArrayList<>();
		this.itens.add(1);
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public List<Integer> getItens() {
		return itens;
	}

}
