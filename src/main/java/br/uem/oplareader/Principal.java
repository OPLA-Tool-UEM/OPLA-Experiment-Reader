package br.uem.oplareader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.log4j.Logger;

import br.uem.oplareader.model.ExperimentData;

public class Principal {

	private static final Logger LOG = Logger.getLogger(Principal.class);
	
	private static final Path OUTPUT_FOLDER = Paths.get("src/main/resources");
	
	
	public static void main(String[] args) throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		Map<String, ExperimentData> listData = calculoFitness.calcular(OUTPUT_FOLDER);
		listData.forEach((key, value) -> {
			ExperimentData data = listData.get(key);
			LOG.info("File: " + key + ", Hypervolume: " + data.getHypervolume() + ", Execution Time: " + data.getTemposExecucaoEmHoras());
		}); 
//		
//		ChartGenerator chartGenerator = new ChartGenerator();
//		chartGenerator.generate(OUTPUT_FOLDER);
	}
}
