package com.pedidovenda.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="cliente")
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private List<Endereco> endereco = new ArrayList<>();
	private TipoPessoa tipoPessoa;
	private String nome;
	private String email;
	private String documentoReceitaFederal;

	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	public List<Endereco> getEndereco() {
		return endereco;
	}
	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length=16)
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	@Column(nullable= false, length=100)
	@NotNull
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(nullable=false, length=255)
	@Email
	@NotNull
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="doc_receita_federal", length=16, nullable=false)
	public String getDocumentoReceitaFederal() {
		return documentoReceitaFederal;
	}
	public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
		this.documentoReceitaFederal = documentoReceitaFederal;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	 
}
