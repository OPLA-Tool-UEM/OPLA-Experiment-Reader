package br.uem.oplareader;

import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.SortOrder;

import br.uem.oplareader.model.ExperimentData;

public class ChartGenerator extends AbstractFileReader {

	private static final Logger LOG = Logger.getLogger(ChartGenerator.class);

	public void generate(Path outputFolder) throws IOException {
		LOG.info("Gerando gráfico");

		ExperimentData data = readData(outputFolder);
		
		new Thread(() -> {
			createChart(data);
		}).start();
		
		new Thread(() -> {
			createXyChart(data);
		}).start();

	}

	private void createXyChart(ExperimentData data) {
		XYSeriesCollection dataSet = new XYSeriesCollection();

		metricName.stream().forEach(name -> {
			XYSeries xySerie = new XYSeries(name);
			data.getMetricas().stream()
				.filter(item -> item.getName().equals(name))
				.forEach(item -> {
				XYDataItem xyDataset = new XYDataItem(item.getValue(), item.getValue());
				xySerie.add(xyDataset);
			});
			dataSet.addSeries(xySerie);
		});

		JFreeChart chart = ChartFactory.createScatterPlot("Grafico", "Funcões", "fitnes", dataSet);

		ChartPanel chartPanel = new ChartPanel(chart);
		addToFrame(chartPanel, "");
	}

	private void createChart(ExperimentData data) {
		DefaultKeyedValues values = new DefaultKeyedValues();
		data.getMetricas().forEach(result -> {
			values.addValue(result.getValue(), result.getValue());
		});
		values.sortByValues(SortOrder.DESCENDING);
		KeyedValues cumulative = DataUtilities.getCumulativePercentages(values);
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset("Soluções", values);

		JFreeChart chart = ChartFactory.createBarChart("Grafico", "Funcões", "fitnes", dataset);
		CategoryPlot plot = chart.getCategoryPlot();

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.02);
		domainAxis.setUpperMargin(0.02);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();

		final CategoryDataset dataset2 = DatasetUtilities.createCategoryDataset("Cumulative", cumulative);
		final NumberAxis axis2 = new NumberAxis("Percent");
		axis2.setNumberFormatOverride(NumberFormat.getPercentInstance());
		plot.setRangeAxis(1, axis2);
		plot.setDataset(1, dataset2);
		plot.setRenderer(1, renderer2);
		plot.mapDatasetToRangeAxis(1, 1);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		ChartPanel chartPanel = new ChartPanel(chart);

		addToFrame(chartPanel, "");
	}

	private void addToFrame(ChartPanel chartPanel, String name) {
		JFrame frame = new JFrame(name);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().add(chartPanel);
	}

}
