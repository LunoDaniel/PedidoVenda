package com.pedidovenda.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class EnderecoEntrega implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;
	private String cep;
	
	@NotNull
	@Size(max=150)
	@Column(name="entrega_logradouro", nullable=false, length=150)
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@NotNull
	@Size(max=20)
	@Column(name="entrega_numero", nullable=false, length=20)
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Size(max=150)
	@Column(name="entrega_complemento", nullable=true, length=150)
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@NotNull
	@Size(max=60)
	@Column(name="entrega_cidade", nullable=false, length=60)
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	@NotNull
	@Size(max=60)
	@Column(name="entrega_uf", nullable=false, length=60)
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	@NotNull
	@Size(max=9)
	@Column(name="entrega_cep", nullable=false, length=9)
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

}
