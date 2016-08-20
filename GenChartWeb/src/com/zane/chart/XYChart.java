package com.zane.chart;

public class XYChart {
	
	protected String title;
	protected String xAxisLabel;
	protected String yAxisLabel;
	protected boolean xAxisLabelVisible;
	protected boolean yAxisLabelVisible;
	
	protected boolean legendVisible;
	protected boolean legendBorderVisible;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXAxisLabel() {
		return xAxisLabel;
	}

	public void setXAxisLabel(String axisLabel) {
		xAxisLabel = axisLabel;
	}

	public String getYAxisLabel() {
		return yAxisLabel;
	}

	public void setYAxisLabel(String axisLabel) {
		yAxisLabel = axisLabel;
	}

	public boolean isXAxisLabelVisible() {
		return xAxisLabelVisible;
	}

	public void setXAxisLabelVisible(boolean axisLabelVisible) {
		xAxisLabelVisible = axisLabelVisible;
	}

	public boolean isYAxisLabelVisible() {
		return yAxisLabelVisible;
	}

	public void setYAxisLabelVisible(boolean axisLabelVisible) {
		yAxisLabelVisible = axisLabelVisible;
	}

	public boolean isLegendVisible() {
		return legendVisible;
	}

	public void setLegendVisible(boolean legendVisible) {
		this.legendVisible = legendVisible;
	}

	public boolean isLegendBorderVisible() {
		return legendBorderVisible;
	}

	public void setLegendBorderVisible(boolean legendBorderVisible) {
		this.legendBorderVisible = legendBorderVisible;
	}
}
