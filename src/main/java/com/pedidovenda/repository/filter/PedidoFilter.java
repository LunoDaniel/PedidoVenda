package com.pedidovenda.repository.filter;

import com.pedidovenda.model.StatusPedido;
import lombok.Data;

import java.util.Date;

@Data
public class PedidoFilter {
	
	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomeCliente;
	private String nomeUsuario;
	private StatusPedido[] statuses;
}
