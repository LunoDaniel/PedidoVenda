package com.pedidovenda.util.report;

import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class ExecutorRelatorio {

	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;

	public void execute(Connection connection) throws SQLException {
		/* TODO implementar execução do relatorio com JasperReports
		 * Usar EclipseLink em vez de Hibernate para obter conexão
		 */
	}
	
	// Getters e Setters
	public String getCaminhoRelatorio() {
		return caminhoRelatorio;
	}

	public void setCaminhoRelatorio(String caminhoRelatorio) {
		this.caminhoRelatorio = caminhoRelatorio;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public String getNomeArquivoSaida() {
		return nomeArquivoSaida;
	}

	public void setNomeArquivoSaida(String nomeArquivoSaida) {
		this.nomeArquivoSaida = nomeArquivoSaida;
	}
}
