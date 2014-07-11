package com.android.easyfitness.model;

public class DietType {
	private String name;
	private double[] macroPercentages;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getMacroPercentages() {
		return macroPercentages;
	}

	public void setMacroPercentages(double[] macroPercentages) {
		this.macroPercentages = macroPercentages;
	}
}
