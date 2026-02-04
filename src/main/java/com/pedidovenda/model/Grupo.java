package com.pedidovenda.model;

import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Table(name = "grupo")
public class Grupo {
	private Long id;
	private String nome;
	private String descricao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

    @Column(nullable=false, length=40)
	public String getNome() {
		return nome;
	}

    @Column(nullable=false, length=80)
	public String getDescricao() {
		return descricao;
	}

}