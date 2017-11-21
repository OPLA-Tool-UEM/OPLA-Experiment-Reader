package br.uem.oplareader.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExperimentData {

	private Integer qtdSolucoes;
	private Integer qtdFuncoesObjetivo;
	private List<Metric> metricas = new LinkedList<>();
	private ExecutionTime tempoExecucao;
	private double[][] front;
	private Double hypervolume;

	public ExperimentData(Integer qtdSolucoes, Integer qtdFuncoesObjetivo) {
		this.qtdSolucoes = qtdSolucoes;
		this.qtdFuncoesObjetivo = qtdFuncoesObjetivo;
	}

	public List<Metric> getMetricas() {
		return metricas;
	}

	public ExecutionTime getTemposExecucao() {
		return tempoExecucao;
	}
	
	public Long getTemposExecucaoEmHoras() {
		return TimeUnit.MILLISECONDS.toHours(tempoExecucao.getTempoExecucao());
	}


	public Integer getQtdSolucoes() {
		return qtdSolucoes;
	}

	public Integer getQtdFuncoesObjetivo() {
		return qtdFuncoesObjetivo;
	}
	
	public double[][] getFrontValue() {
		return front;
	}
	
	public Double getHypervolume() {
		return hypervolume;
	}
	
	public void setFrontValue(double[][] value) {
		this.front = value;
	}
	
	public void setHypervolume(Double hypervolume) {
		this.hypervolume = hypervolume;
	}	

	public void add(List<String> nomeMetricas, List<String> valores) {
		IntStream.range(0, valores.size()).forEach(index -> {
			metricas.add(new Metric(String.valueOf(index), valores.get(index)));
		});
	}

	public void put(ExecutionTime executionTime) {
		this.tempoExecucao =  executionTime;
	}

	@Override
	public String toString() {
		return "QTD Funções: " + qtdFuncoesObjetivo + ", QTD soluções: " + qtdSolucoes;
	}

	
}
