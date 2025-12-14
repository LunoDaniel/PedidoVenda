package com.pedidovenda.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DataValor {
	
	private LocalDate data;
	private BigDecimal valor;

}
