package com.pedidovenda.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "cliente", cascade= CascadeType.ALL, fetch= FetchType.LAZY, targetEntity=Endereco.class)
	private List<Endereco> endereco = new ArrayList<>();

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length=16)
	private TipoPessoa tipoPessoa;

	@NotBlank
	@Column(nullable= false, length=100)
	private String nome;

	@Email
	@NotBlank
	@Column(nullable=false)
	private String email;


	@NotNull @NotBlank
	@Column(name="doc_receita_federal", length=16, nullable=false)
	private String documentoReceitaFederal;

	@Transient
	public boolean isPessoaFisica() {
		return TipoPessoa.isPessoaFisica(this.tipoPessoa);
	}
}
