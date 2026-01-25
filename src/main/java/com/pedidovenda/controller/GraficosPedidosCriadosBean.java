package com.pedidovenda.controller;

import com.pedidovenda.repository.PedidoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Named
@RequestScoped
public class GraficosPedidosCriadosBean implements Serializable {

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	@Inject
	private PedidoRepository pedidos;

	//private CartesianChartModel modelChart;
	/*
	public void preRender(){
		this.modelChart = new LineChartModel();
		this.modelChart.setTitle("Pedidos");
		this.modelChart.setLegendPosition("e");
		adicionarSerie("Todos os Pedidos", null);
		adicionarSerie("Meus Pedido", new Usuario());
	}

	private void adicionarSerie(String rotulo, Usuario criadoPor) {
		Map<Date, BigDecimal> valoresPorData = this.pedidos.valoresTotaisPorData(15, criadoPor);

		ChartSeries series = new ChartSeries(rotulo);
	
		
		for(Date data : valoresPorData.keySet()){
			series.set(DATE_FORMAT.format(data),  valoresPorData.get(data) );
		}
		
		this.modelChart.addSeries(series);
	}

	public CartesianChartModel getModelChart() {
		return modelChart;
	}
	 * 
	 */
	

}
