package com.pedidovenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable=false, length=155)
	private String logradouro;

	@NotBlank
	@Column(nullable=false, length=20)
	private String numero;
	@Column(length=150)
	private String complemento;

	@NotBlank
	@Column(nullable=false, length=60)
	private String cidade;

	@NotBlank
	@Column(nullable=false, length=60)
	private String uf;

	@NotBlank
	@Column(nullable=false, length=12)
	private String cep;

	@ManyToOne
	@JoinColumn(name="cliente_id", nullable=false)
	private Cliente cliente;
}
