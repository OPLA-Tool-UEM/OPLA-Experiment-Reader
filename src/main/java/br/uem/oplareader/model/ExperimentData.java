package br.uem.oplareader.model;

import java.util.LinkedList;
import java.util.List;

public class ExperimentData {

	private Integer qtdSolucoes;
	private Integer qtdFuncoesObjetivo;
	private List<Metric> metricas = new LinkedList<>();
	private List<ExecutionTime> temposExecucao = new LinkedList<>();

	public ExperimentData(Integer qtdSolucoes, Integer qtdFuncoesObjetivo) {
		this.qtdSolucoes = qtdSolucoes;
		this.qtdFuncoesObjetivo = qtdFuncoesObjetivo;
	}

	public List<Metric> getMetricas() {
		return metricas;
	}

	public List<ExecutionTime> getTemposExecucao() {
		return temposExecucao;
	}

	public Integer getQtdSolucoes() {
		return qtdSolucoes;
	}

	public Integer getQtdFuncoesObjetivo() {
		return qtdFuncoesObjetivo;
	}

	public void add(List<String> nomeMetricas, List<String> valores) {
		for (int index = 0; index < nomeMetricas.size(); index++) {
			metricas.add(new Metric(nomeMetricas.get(index), valores.get(index)));
		}
	}

	public void add(ExecutionTime executionTime) {
		this.temposExecucao.add(executionTime);
	}

}
