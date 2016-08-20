package com.zane.chart.web;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import com.zane.chart.TimeSeriesChart;
import com.zane.chart.XYRenderOption;

/**
 * Servlet implementation class ChartController
 */
public class ChartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChartController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get and convert parameters


		//s1=EQTY[IBM]:OPEN&s2=EQTY[ORCL]:OPEN&sdate=01012007&edate=01312009&debug=true
		long start = System.currentTimeMillis();
		StringBuilder builder = new StringBuilder();

		String startDate =  request.getParameter("sdate");
		String endDate = request.getParameter("edate");
		
		int width = 500;
		try {
			width = Integer.parseInt(request.getParameter("w"));
		} catch (NumberFormatException e1) {			 
		}
		
		int height = 300;
		try {
			height = Integer.parseInt(request.getParameter("h"));
		} catch (NumberFormatException e1) {			 
		}
		
		
		builder.append("sdate=").append(startDate).append("&");
		builder.append("edate=").append(endDate).append("&");

		String tickers= request.getParameter("tickers").toUpperCase();
		String[] tArr = StringUtils.splitPreserveAllTokens(tickers,",");
		String series = request.getParameter("series");

		int counter = 1;

		
		List<XYRenderOption> co = new ArrayList<XYRenderOption>();
		StringBuilder title = new StringBuilder(); 
		for (int i = 0; i < tArr.length; i++) {
			String security = tArr[i].trim();
			if(!"".equals(security)){
				title.append(security).append(" ");
				if ("HLOCV".equalsIgnoreCase(series)){
					builder.append("s").append(counter++).append("="+URLEncoder.encode("SEC["+security+"]:HIGH","UTF-8")).append("&");
					builder.append("s").append(counter++).append("="+URLEncoder.encode("SEC["+security+"]:LOW","UTF-8")).append("&");
					builder.append("s").append(counter++).append("="+URLEncoder.encode("SEC["+security+"]:OPEN","UTF-8")).append("&");
					builder.append("s").append(counter++).append("="+URLEncoder.encode("SEC["+security+"]:CLOSE","UTF-8")).append("&");
					builder.append("s").append(counter++).append("="+URLEncoder.encode("SEC["+security+"]:VOLUME","UTF-8")).append("&");
				}else{
					builder.append("s").append(counter++).append("="+URLEncoder.encode("SEC["+security+"]:"+series,"UTF-8")).append("&");
				}
				XYRenderOption o = new XYRenderOption();
				o.setLegendVisible(true);
				o.setLinesVisible(true);
				co.add(o);	
			}
		}
		UrlInvoker invoker = new UrlInvoker();
		String data = invoker.getStringFromURL(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ds/idal?ctr=yahoo&dataonly=true&"+builder.toString());
		 
		JFreeChart chart = null;

		try {
			
			final TimeSeriesChart tsc = new TimeSeriesChart(title.toString(), "Date", "Price", true);
			tsc.setYAxisLabelVisible(true);
			tsc.setXAxisLabelVisible(true); 

			XYDataset dataset = tsc.createFileDataset(data);
			chart = tsc.generateChart(dataset,co);
			chart.setTitle(chart.getTitle().getText()+" - "+(System.currentTimeMillis()-start)+"ms");

		} catch (Exception e) {
			e.printStackTrace();
		}


		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, width, height);
 
	}

}
