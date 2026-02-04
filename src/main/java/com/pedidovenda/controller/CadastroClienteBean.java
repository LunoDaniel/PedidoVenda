package com.pedidovenda.controller;


import com.pedidovenda.model.Cliente;
import com.pedidovenda.model.Endereco;
import com.pedidovenda.model.TipoPessoa;
import com.pedidovenda.service.CadastroClienteService;
import com.pedidovenda.util.jsf.FacesUtil;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	
	@Inject
	private CadastroClienteService cadastroClienteService;
	
	private Cliente cliente;
	private Endereco enderecoCliente;
	
	public CadastroClienteBean(){
		this.cliente = new Cliente();
		this.enderecoCliente = new Endereco();
	}
	
	public void adicionaEnderecoCliente(){
		if(this.enderecoCliente != null){
			this.cliente.getEndereco().add(this.enderecoCliente);
		}
	}
	
	public void salvar(){
		adicionaEnderecoCliente();
		cadastroClienteService.salvar(this.cliente);
		
		FacesUtil.addInfoMessage("Cliente Salvo com Sucesso.");
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public TipoPessoa[] getTipoPessoa() {
		return TipoPessoa.values();
	}
	public Endereco getEnderecoCliente() {
		return enderecoCliente;
	}
	
	public void setEnderecoCliente(Endereco enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}
}
