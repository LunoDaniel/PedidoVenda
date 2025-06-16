package com.pedidovenda.util.report;

import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class ExecutorRelatorio implements Work {

	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;


	@Override
	public void execute(Connection connection) throws SQLException {
		/* TODO implementar execução do relatorio
		* */
	}
}
