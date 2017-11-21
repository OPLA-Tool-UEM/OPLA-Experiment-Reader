package br.uem.oplareader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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

	Map<String, ExperimentData> mapResult = new LinkedHashMap<>();

	protected void getFront(Path ouputFolder, Map<String, ExperimentData> mapData) throws IOException {
		Path path = ouputFolder.resolve("nsgaiii-3obj").resolve("fitness");
		mapData.forEach((key, value) -> {
			Path fitFile = path.resolve(key);
			ExperimentData experimentData = mapData.get(key);
			experimentData.setFrontValue(new MetricsUtil().readFront(fitFile.toFile().toString()));
			mapData.put(key, experimentData);
		});
	}

	public Map<String, ExperimentData> readData(Path ouputFolder) throws IOException {
		Path path = ouputFolder.resolve("nsgaiii-3obj");
		toExperimentResults(path);
		toExectionTimes(path);
		return mapResult;
	}

	private void toExperimentResults(Path path) throws IOException {
		Path fitnessPath = path.resolve("fitness");
		Files.walk(fitnessPath)
			.filter(Files::isRegularFile).forEach(file -> {
			try {
				readFitnessFile(file);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private void readFitnessFile(Path file) throws IOException {
		try {
			List<String> readAllLines = readAllLines(file);
			ExperimentData experimentData = new ExperimentData(getQtdSolucoes(readAllLines), getQtdFuncoesObjetivo(readAllLines.get(0)));
			readAllLines.forEach(linha -> {
				List<String> valores = Arrays.asList(linha.split(TAB));
				valores.removeIf(StringUtils::isBlank);
				experimentData.add(metricName, valores);
			});
			mapResult.put(file.getFileName().toString(), experimentData);
		} catch (IOException e) {
			LOG.error(e);
			throw new IOException("Falha ao ler arquivo de fitness");
		}
	}

	private int getQtdSolucoes(List<String> readAllLines) {
		return readAllLines.size();
	}

	private Integer getQtdFuncoesObjetivo(String linha) {
		return linha.split(TAB).length;
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
			AtomicInteger index = new AtomicInteger(0);
			mapResult.forEach((key, value) ->{
				String line = readAllLines.get(index.getAndIncrement());
				Matcher matcher = PATTERN_EXECUTION_TIME.matcher(line);
				if (matcher.find()) {
					ExperimentData experimentData = mapResult.get(key);
					ExecutionTime executionTime = new ExecutionTime(matcher.group(1), matcher.group(2));
					experimentData.put(executionTime);
				}
			});

		} catch (IOException e) {
			LOG.error(e);
			throw new IOException("Falha ao ler arquivo de fitness");
		}
	}
}
