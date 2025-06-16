package com.pedidovenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@NotNull
	@Column(name = "usuario_nome", nullable = false, length = 80)
	private String nome;

	@Email(message = "Deve ser um Email Válido.")
	@NotBlank
	@Column(name = "usuario_email", unique = true, nullable = false, length = 255)
	private String email;

	@Column(nullable = false, length = 255)
	private String senha;

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name = "grupo_usuario",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupos = new ArrayList<>();
}
