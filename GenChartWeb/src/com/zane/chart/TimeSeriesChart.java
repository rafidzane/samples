package com.zane.chart;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class TimeSeriesChart extends XYChart{


	private JFreeChart chart = null; 



	public TimeSeriesChart(String title, String xAxisLabel, String yAxisLabel, boolean legendVisible) throws Exception {
		this.title = title;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel; 
		this.legendVisible = legendVisible;
	}

	public JFreeChart generateChart(Dataset dataset, List<XYRenderOption> chartOptions) throws Exception{
		chart = ChartFactory.createTimeSeriesChart(
				title, 
				xAxisLabel, 
				yAxisLabel,
				(XYDataset)dataset, 
				legendVisible, 
				true, 
				false
		);

		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(); 

		setChartOptions(chart);

		if(((XYDataset)dataset).getSeriesCount() == 0){
			plot.setNoDataMessage("No Data Found");
			plot.getDomainAxis().setTickLabelsVisible(false);
			plot.getDomainAxis().setTickLabelsVisible(false);
			
			plot.getRangeAxis().setTickMarksVisible(false);
			plot.getRangeAxis().setTickLabelsVisible(false);
			
		}else{
			for (int i = 0; i < chartOptions.size(); i++) {
				XYRenderOption o = chartOptions.get(i);
				renderer.setSeriesLinesVisible(i, o.isLinesVisible());
				renderer.setSeriesShapesVisible(i, o.isShapesVisible());	
				renderer.setSeriesFillPaint(i, o.getLineColor());
				renderer.setSeriesPaint(i, o.getLineColor());  
			}                
			plot.setRenderer(renderer);
		}
		return chart;
	}


	private void setChartOptions(JFreeChart chart) {
		ValueAxis rangeAxis = chart.getXYPlot().getRangeAxis();
		ValueAxis domainAxis = chart.getXYPlot().getDomainAxis();
		if(!legendBorderVisible){
			chart.getLegend().setFrame(BlockBorder.NONE);	
		}
		if(!xAxisLabelVisible){
			domainAxis.setLabel("");
		}
		if(!yAxisLabelVisible){
			rangeAxis.setLabel("");
		}

		chart.getPlot().setBackgroundPaint(Color.WHITE);
		chart.getPlot().setOutlineVisible(false);

	}

	public XYDataset createFileDataset(File dataFile) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(dataFile));
		return createFileDataset(reader);
	}
	public XYDataset createFileDataset(String dataString) throws Exception {
		BufferedReader reader = new BufferedReader(new StringReader(dataString));
		return createFileDataset(reader);
	}

	private XYDataset createFileDataset(BufferedReader reader) throws Exception { 
		String line = null;
		boolean isHeaderLine = true;
		TimeSeries[] series = null;
		boolean isDate=false;
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		while( (line=reader.readLine()) != null){
			if("".equals(line)) continue;
			String[] parts = StringUtils.splitPreserveAllTokens(line, ","); 
			if(isHeaderLine){
				isDate = "TradeDate".equalsIgnoreCase(parts[0])?true:false;
				series = new TimeSeries[parts.length-1];
				for (int i = 0; i < series.length; i++) {	    				 
					series[i] = new TimeSeries(parts[i+1]); 
				}	    			
				isHeaderLine = false;
			}else{
				if(isDate){
					String dateStr = parts[0];					
					for (int i = 1; i < parts.length; i++) {
						if(!"".equalsIgnoreCase(parts[i].trim())){
							series[i-1].addOrUpdate(new Day(dateFormat.parse(dateStr)),
									Double.parseDouble(parts[i]));	
						}						
					}
				}else{
					String dateStr = parts[0];		    			
					for (int i = 1; i < parts.length; i++) {
						if(!"".equalsIgnoreCase(parts[i].trim())){
							series[i-1].addOrUpdate(new Second(timeFormat.parse(dateStr)),
									Double.parseDouble(parts[i]));
						}
					}
				}	    				
			}	    		
		}
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i = 0; i < series.length; i++) {
			dataset.addSeries(series[i]);
		}	    	 
		return dataset;
	}




	public static void main(final String[] args) throws Exception {

		final TimeSeriesChart tsc = new TimeSeriesChart("TestChart", "Time", "Price", true);
		tsc.setYAxisLabelVisible(false);
		tsc.setXAxisLabelVisible(true);

		XYDataset dataset = tsc.createFileDataset(new File("test.txt"));
		List<XYRenderOption> co = new ArrayList<XYRenderOption>();
		XYRenderOption o = new XYRenderOption();
		o.setLegendVisible(true);
		o.setLinesVisible(true);
		o.setLineColor(Color.BLUE);
		co.add(o);

		o = new XYRenderOption();
		o.setLegendVisible(true);
		o.setLinesVisible(true);
		o.setLineColor(Color.GREEN);
		co.add(o);


		o = new XYRenderOption();
		o.setLegendVisible(true);
		o.setLinesVisible(true);
		o.setLineColor(Color.RED);
		co.add(o);


		o = new XYRenderOption();
		o.setLegendVisible(true);
		o.setLinesVisible(true);
		o.setLineColor(Color.BLUE);
		co.add(o);

		JFreeChart chart = tsc.generateChart(dataset,co);

		ChartUtilities.saveChartAsJPEG(new File("files/chart.jpg"), chart, 500, 300);

	}

}
