package com.pedidovenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@Entity
@Table(name = "pedido")
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@Embedded
	private EnderecoEntrega enderecoEntrega;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 24)
	private StatusPedido status = StatusPedido.ORCAMENTO;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false, length = 20)
	private FormaPagamento formaPagamento;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_entrega", nullable = false)
	private LocalDateTime dataEntrega;

	@NotNull
	@Column(columnDefinition = "text")
	private String observacao;

	@NotNull
	@Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorFrete = BigDecimal.ZERO;

	@NotNull
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorDesconto = BigDecimal.ZERO;

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorTotal = BigDecimal.ZERO;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ItemPedido> itens = new ArrayList<>();

	@Transient
	public boolean isNovo() {
		return getId() == null;
	}

	@Transient
	public boolean isExistente() {
		return !isNovo();
	}

	@Transient
	public BigDecimal getValorSubtotal() {
		return this.getValorTotal().subtract(this.getValorFrete()).add(this.getValorDesconto());
	}

	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;

		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());

		for (ItemPedido item : this.getItens()) {
			if (item.getProduto() != null && item.getProduto().getId() != null) {
				total = total.add(item.getValorTotal());
			}
		}

		this.setValorTotal(total);
	}

	@Transient
	public void adicionarItemVazio() {
		if (this.isOrcamento()) {
			Produto produto = new Produto();

			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setProduto(produto);
			itemPedido.setPedido(this);

			this.getItens().add(0, itemPedido);
		}
	}

	@Transient
	public boolean isOrcamento() {
		return StatusPedido.ORCAMENTO.equals(this.getStatus());
	}

	public void removerItemVazio() {
		ItemPedido primeiroItem = this.getItens().get(0);

		if (primeiroItem != null && primeiroItem.getProduto().getId() == null) {
			this.getItens().remove(0);
		}
	}

	@Transient
	public boolean isValorTotalNegativo() {
		return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0;
	}

	@Transient
	public boolean isEmitido() {
		return StatusPedido.EMITIDO.equals(this.getStatus());
	}

	@Transient
	public boolean isNaoEmissivel() {
		return !this.isEmissivel();
	}

	@Transient
	private boolean isEmissivel() {
		return this.isExistente() && this.isOrcamento();
	}

	@Transient
	public boolean isNaoCancelavel() {
		return !isCancelavel();
	}

	@Transient
	private boolean isCancelavel() {
		return this.isExistente() && !this.isCancelado();
	}

	@Transient
	private boolean isCancelado() {
		return StatusPedido.CANCELADO.equals(this.getStatus());
	}

	@Transient
	public boolean isNaoAlteravel() {
		return !isAlteravel();
	}

	@Transient
	private boolean isAlteravel() {
		return this.isOrcamento();
	}

	@Transient
	public boolean isEmailNaoEnviavel(){
		return this.isCancelado() || this.isNovo();
	}

}
