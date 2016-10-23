package com.pedidovenda.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.pedidovenda.model.StatusPedido;

public class PedidoFilter implements  Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomeCliente;
	private String nomeUsuario;
	private StatusPedido[] statuses;


	public void setNumeroDe(Long numeroDe) {
		this.numeroDe = numeroDe;
	}

	public void setNumeroAte(Long numeroAte) {
		this.numeroAte = numeroAte;
	}

	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}

	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public void setStatuses(StatusPedido[] statuses) {
		this.statuses = statuses;
	}

	public Long getNumeroDe() {
		return this.numeroDe;
	}

	public Long getNumeroAte() {
		return this.numeroAte;
	}

	public Date getDataCriacaoDe() {
		return this.dataCriacaoDe;
	}
	
	public Date getDataCriacaoAte() {
		return this.dataCriacaoAte;
	}

	public String getNomeCliente() {
		return this.nomeCliente;
	}

	public StatusPedido[] getStatuses() {
		return this.statuses;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	
}
