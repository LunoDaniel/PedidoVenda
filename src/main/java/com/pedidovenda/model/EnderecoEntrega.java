package com.pedidovenda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EnderecoEntrega {

	@NotNull
	@Size(max=150)
	@Column(name="entrega_logradouro", nullable=false, length=150)
	private String logradouro;

	@NotNull
	@Size(max=20)
	@Column(name="entrega_numero", nullable=false, length=20)
	private String numero;

	@Size(max=150)
	@Column(name="entrega_complemento", nullable=true, length=150)
	private String complemento;

	@NotNull
	@Size(max=60)
	@Column(name="entrega_cidade", nullable=false, length=60)
	private String cidade;

	@NotNull
	@Size(max=60)
	@Column(name="entrega_uf", nullable=false, length=60)
	private String uf;

	@NotNull
	@Size(max=9)
	@Column(name="entrega_cep", nullable=false, length=9)
	private String cep;

}
