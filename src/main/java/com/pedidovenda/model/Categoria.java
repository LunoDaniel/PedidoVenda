package com.pedidovenda.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categoria")
public class Categoria {


	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 60)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "categoria_pai_id")
	private Categoria categoriaPai;

	@OneToMany(mappedBy = "categoriaPai", cascade = CascadeType.ALL)
	private List<Categoria> subcategorias = new ArrayList<>();
}