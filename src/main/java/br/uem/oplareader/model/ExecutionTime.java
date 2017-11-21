package br.uem.oplareader.model;

import java.util.concurrent.TimeUnit;

public class ExecutionTime {

	private Long idExecucao;

	private Long tempoExecucao;

	public ExecutionTime(String idExecution, String tempoExecucao) {
		this.idExecucao = Long.valueOf(idExecution);
		this.tempoExecucao = Long.valueOf(tempoExecucao);
	}

	public Long getTempoExecucao() {
		return tempoExecucao;
	}

	@Override
	public String toString() {
		return idExecucao + " " + TimeUnit.MILLISECONDS.toHours(tempoExecucao);
	}
}
