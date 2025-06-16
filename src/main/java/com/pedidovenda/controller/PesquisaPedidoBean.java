package com.pedidovenda.controller;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;
import com.pedidovenda.repository.PedidoRepository;
import com.pedidovenda.repository.filter.PedidoFilter;
import com.pedidovenda.util.jsf.FacesUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PesquisaPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	PedidoRepository pedidos;
	PedidoFilter filtro;
	Pedido pedidoSelecionado;
	
	public void setPedidoSelecionado(Pedido pedidoSelecionado) {
		this.pedidoSelecionado = pedidoSelecionado;
	}

	private List<Pedido> pedidosFiltrados;
	
	public PesquisaPedidoBean(){
		filtro = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}
	
	public void pesquisar(){
		pedidosFiltrados = pedidos.filtrados(filtro);
	}
	
	public void remover(){
		pedidos.remover(pedidoSelecionado);
		FacesUtil.addInfoMessage("Pedido: " + pedidoSelecionado.getId() + " Removido com Sucesso.");
	}
	
	public StatusPedido[] getStatuses(){
		return StatusPedido.values();
	}
	
	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}
	
	public PedidoFilter getFiltro() {
		return filtro;
	}
	
	public Pedido getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	
}
