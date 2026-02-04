package com.pedidovenda.repository.filter;

import com.pedidovenda.model.StatusPedido;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoFilter {
	
	private Long numeroDe;
	private Long numeroAte;
	private LocalDateTime dataCriacaoDe;
	private LocalDateTime dataCriacaoAte;
	private String nomeCliente;
	private String nomeUsuario;
	private List<StatusPedido> statuses;
}
