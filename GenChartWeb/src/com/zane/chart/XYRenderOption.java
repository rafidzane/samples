package com.zane.chart;

import java.awt.Color;
import java.awt.Shape;

public class XYRenderOption {
	
	private boolean linesVisible;
	private boolean shapesVisible;
	private Color lineColor;
	private Color shapeColor;
	private Shape  lineShape;
	private boolean legendVisible;
	private boolean legendLabel;
	
	
	public boolean isLinesVisible() {
		return linesVisible;
	}
	public void setLinesVisible(boolean linesVisible) {
		this.linesVisible = linesVisible;
	}
	public boolean isShapesVisible() {
		return shapesVisible;
	}
	public void setShapesVisible(boolean shapesVisible) {
		this.shapesVisible = shapesVisible;
	}
	public Color getLineColor() {
		return lineColor;
	}
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	public Color getShapeColor() {
		return shapeColor;
	}
	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	public Shape getLineShape() {
		return lineShape;
	}
	public void setLineShape(Shape lineShape) {
		this.lineShape = lineShape;
	}
	public boolean isLegendVisible() {
		return legendVisible;
	}
	public void setLegendVisible(boolean legendVisible) {
		this.legendVisible = legendVisible;
	}
	public boolean isLegendLabel() {
		return legendLabel;
	}
	public void setLegendLabel(boolean legendLabel) {
		this.legendLabel = legendLabel;
	}
	
	

}
