package com.pedidovenda.controller;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@Named
//@ViewScoped
public class RelatorioPedidosEmitidosBean implements Serializable {

	private Date dataInicio;
	private Date dataFim;

//	@Inject
	private FacesContext facesContext;

//	@Inject
	private HttpServletResponse response;

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		
//		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioPedidosEmitidos.jasper",
//				this.response, parametros, "Pedidos emitidos.pdf");
//
//		Session session = manager.unwrap(Session.class);
//		session.doWork(executor);
//
//		if (executor.isRelatorioGerado()) {
//			facesContext.responseComplete();
//		} else {
//			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
//		}
	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
