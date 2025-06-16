package com.pedidovenda.model;

import java.math.BigDecimal;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.validation.SKU;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "produto")
@AllArgsConstructor
@NoArgsConstructor
public class Produto {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	private String nome;

	@NotBlank
	@SKU
	@Column(nullable = false, length = 20, unique = true)
	private String sku;

	@NotNull(message = "é obrigatório")
	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorUnitario;

	@NotNull
	@Min(0)
	@Max(value = 9999, message = "tem um valor muito alto")
	@Column(name = "quantidade_estoque", nullable = false, length = 5)
	private Integer quantidadeEstoque;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;


	public void baixarEstoque(Integer quantidade) {
		int novaQuantidade = this.getQuantidadeEstoque() - quantidade;
		if (novaQuantidade < 0) {
			throw new NegocioException(
					"Não há disponibilidade no Estoque de " + quantidade + " itens do produto " + this.getSku() + ".");
		}
		this.setQuantidadeEstoque(novaQuantidade);
	}

	public void adicionarEstoque(Integer quantidade) {
		this.setQuantidadeEstoque(getQuantidadeEstoque()  + quantidade);
		
	}
}