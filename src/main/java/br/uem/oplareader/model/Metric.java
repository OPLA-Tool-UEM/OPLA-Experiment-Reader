package br.uem.oplareader.model;

import java.math.BigDecimal;

/**
 * 
 * @author Fernando
 *
 */
public class Metric {

	private String name;
	private BigDecimal value;

	public Metric(String name, String value) {
		this.name = name;
		this.value = new BigDecimal(value);
	}

	public String getName() {
		return name;
	}

	public BigDecimal getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return name + " " + value;
	}
}
