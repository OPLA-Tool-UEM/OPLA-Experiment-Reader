package br.uem.oplareader;

import java.io.IOException;
import java.nio.file.Path;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.Solution;

import br.uem.oplareader.model.ExperimentData;

public class CalculoFitness extends AbstractFileReader {

	public Double calcular(Path ouputFolder) throws IOException {
		ExperimentData data = readData(ouputFolder);
		PISAHypervolume<Solution<?>> hypervolume = new PISAHypervolume<>();
		double[][] front = getFront(ouputFolder);
		return hypervolume.calculateHypervolume(front, data.getQtdSolucoes(), data.getQtdFuncoesObjetivo());
	}

}
