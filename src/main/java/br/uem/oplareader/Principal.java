package br.uem.oplareader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

public class Principal {

	private static final Logger LOG = Logger.getLogger(Principal.class);
	
	private static final Path OUTPUT_FOLDER = Paths.get("src/main/resources");
	
	
	public static void main(String[] args) throws IOException {
		CalculoFitness calculoFitness = new CalculoFitness();
		Double valorCalculado = calculoFitness.calcular(OUTPUT_FOLDER);
		LOG.info("Hypervolume: " + valorCalculado);
		
		ChartGenerator chartGenerator = new ChartGenerator();
		chartGenerator.generate(OUTPUT_FOLDER);
	}
}
