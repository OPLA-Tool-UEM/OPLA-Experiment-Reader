package br.uem.oplareader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.Solution;

import br.uem.oplareader.model.ExperimentData;

public class CalculoFitness extends AbstractFileReader {

	public Map<String, ExperimentData> calcular(Path ouputFolder) throws IOException {
		Map<String, ExperimentData> listData = readData(ouputFolder);
		PISAHypervolume<Solution<?>> hypervolume = new PISAHypervolume<>();
		getFront(ouputFolder, listData);
		listData.forEach((key, value) -> {
			ExperimentData data = listData.get(key);
			double hypervolumeValue = hypervolume.calculateHypervolume(data.getFrontValue(), data.getQtdSolucoes(), data.getQtdFuncoesObjetivo());
			data.setHypervolume(hypervolumeValue);
			listData.put(key, data);
		});
		
		return listData;
	}

}
