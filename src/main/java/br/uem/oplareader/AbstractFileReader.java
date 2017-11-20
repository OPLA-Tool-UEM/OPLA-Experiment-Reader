package br.uem.oplareader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.uem.oplareader.model.ExecutionTime;
import br.uem.oplareader.model.ExperimentData;
import jmetal4.qualityIndicator.util.MetricsUtil;

public class AbstractFileReader {

	private static final String TAB = "\t";
	private static final Logger LOG = Logger.getLogger(AbstractFileReader.class);
	private static final Pattern PATTERN_EXECUTION_TIME = Pattern.compile("Exec ([0-9]+) => ([0-9]+)ms", Pattern.DOTALL);
	protected List<String> metricName = Arrays.asList("COE", "ACLASS", "DC");
	
	ExperimentData experimentResult;
	
	protected double[][] getFront(Path ouputFolder) {
		Path path = ouputFolder.resolve("nsgaiii-3obj/FITNESS.txt");
		double[][] front = new MetricsUtil().readFront(path.toFile().toString());
		return front;
	}

	public ExperimentData readData(Path ouputFolder) throws IOException {
		Path path = ouputFolder.resolve("nsgaiii-3obj");
		toExperimentResults(path);
		toExectionTimes(path);
		return experimentResult;
	}

	private void toExperimentResults(Path path) throws IOException {
		Path fitnessFile = path.resolve("FITNESS.txt");
		try {
		List<String> readAllLines = readAllLines(fitnessFile);
		experimentResult = new ExperimentData(getQtdSolucoes(readAllLines), getQtdFuncoesObjetivo(readAllLines.get(0)));
		readAllLines.forEach(linha ->{
			List<String> valores = Arrays.asList(linha.split(TAB));
			valores.removeIf(StringUtils::isBlank);
			experimentResult.add(metricName, valores);
		});
		} catch (IOException e) {
			LOG.error(e);
			throw new IOException("Falha ao ler arquivo de fitness");
		}
	}

	private int getQtdSolucoes(List<String> readAllLines) {
		return readAllLines.size();
	}
	
	private Integer getQtdFuncoesObjetivo(String linha) {
		return linha.split(StringUtils.SPACE).length;
	}

	private List<String> readAllLines(Path fitnessFile) throws IOException {
		List<String> readAllLines = Files.readAllLines(fitnessFile);
		readAllLines.removeIf(StringUtils::isBlank);
		return readAllLines;
	}

	private void toExectionTimes(Path path) throws IOException {
		Path tempoExecFile = path.resolve("TEMPOEXEC.txt");
		try {
			List<String> readAllLines = Files.readAllLines(tempoExecFile);
			readAllLines.forEach(linha ->{
				Matcher matcher = PATTERN_EXECUTION_TIME.matcher(linha);
				if(matcher.find()) {
					experimentResult.add(new ExecutionTime(matcher.group(1), matcher.group(2)));
				}
			});

		}catch (IOException e) {
			LOG.error(e);
			throw new IOException("Falha ao ler arquivo de fitness");
		}
	}
}
