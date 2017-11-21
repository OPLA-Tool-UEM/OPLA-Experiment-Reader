package br.uem.oplareader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Test;

import br.uem.oplareader.model.ExperimentData;

public class CalculoFitnessTest {

	private static final int QTD_LINHAS_ARQUIVO_FITNES = 100;
	private static final int QTD_FUNCOES_OBJETIVO = 2;
	private Path outputFolder = Paths.get("src/main/resources");

	@Test
	public void leituraArquivoFitnessTest() throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		Map<String, ExperimentData> result = calculoFitness.readData(outputFolder);
		assertEquals(QTD_FUNCOES_OBJETIVO, result.get("FITNESS.txt").getQtdFuncoesObjetivo().intValue());
		assertEquals(QTD_LINHAS_ARQUIVO_FITNES, result.get("FITNESS.txt").getQtdSolucoes().intValue());
		assertEquals(QTD_LINHAS_ARQUIVO_FITNES * QTD_FUNCOES_OBJETIVO, result.get("FITNESS.txt").getMetricas().size());

	}
	
	@Test
	public void leituraArquivoFitnessMultipleFilesTest() throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		Map<String, ExperimentData> result = calculoFitness.readData(outputFolder);
		assertEquals(QTD_FUNCOES_OBJETIVO, result.get("FITNESS.txt").getQtdFuncoesObjetivo().intValue());
		assertEquals(QTD_LINHAS_ARQUIVO_FITNES, result.get("FITNESS.txt").getQtdSolucoes().intValue());
		assertEquals(QTD_LINHAS_ARQUIVO_FITNES * QTD_FUNCOES_OBJETIVO, result.get("FITNESS.txt").getMetricas().size());

	}

	@Test
	public void leituraArquivoTempoExecucaoTest() throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		Map<String, ExperimentData> result = calculoFitness.readData(outputFolder);
		assertEquals(10, result.get("FITNESS.txt").getTemposExecucaoEmHoras(), 0);
	}
	
	@Test
	public void calcularFitnessTest() throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		Map<String, ExperimentData> mapData = calculoFitness.calcular(outputFolder);
		double expected = 1.0;
		assertEquals(expected, mapData.get("FITNESS.txt").getHypervolume(), 0);

	}

}
