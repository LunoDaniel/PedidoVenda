package com.pedidovenda.events;

import com.pedidovenda.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoAlateradoEvent {
	
	private Pedido pedido;
}
