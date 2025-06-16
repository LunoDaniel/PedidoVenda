package com.pedidovenda.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class DataValor {
	
	private Date data;
	private BigDecimal valor;

}
