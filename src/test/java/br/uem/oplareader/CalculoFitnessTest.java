package br.uem.oplareader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import br.uem.oplareader.model.ExperimentData;

public class CalculoFitnessTest {

	private static final int QTD_LINHAS_ARQUIVO_FITNES = 376;
	private static final int QTD_FUNCOES_OBJETIVO = 3;
	private Path outputFolder = Paths.get("src/main/resources");

	@Test
	public void leituraArquivoFitnessTest() throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		ExperimentData result = calculoFitness.readData(outputFolder);
		assertEquals(QTD_FUNCOES_OBJETIVO, result.getQtdFuncoesObjetivo().intValue());
		assertEquals(QTD_LINHAS_ARQUIVO_FITNES, result.getQtdSolucoes().intValue());
		assertEquals(QTD_LINHAS_ARQUIVO_FITNES * QTD_FUNCOES_OBJETIVO, result.getMetricas().size());

	}

	@Test
	public void leituraArquivoTempoExecucaoTest() throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		ExperimentData result = calculoFitness.readData(outputFolder);
		assertEquals(30, result.getTemposExecucao().size());

	}
	
	@Test
	public void calcularFitnessTest() throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		Double calcular = calculoFitness.calcular(outputFolder);
		double expected = 25912.946428571428;
		assertEquals(expected, calcular.doubleValue(), 0);

	}

}
