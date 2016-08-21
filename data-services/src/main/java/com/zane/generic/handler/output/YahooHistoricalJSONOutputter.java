package com.zane.generic.handler.output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zane.generic.handler.YahooHistoricalDataHandler;
import com.zane.generic.obj.InstrumentData;
import com.zane.generic.util.DataServiceKeys;
import com.zane.generic.util.FileKeys;

/**
 * Sample Class for Copy and Paste
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 28, 2008
 */
public class YahooHistoricalJSONOutputter implements DataEngineOutput {

	private static final SimpleDateFormat yyyymmddFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat mmddyyyFormat = new SimpleDateFormat("mmddyyyy");
	private static final Logger logger = Logger.getLogger(YahooHistoricalDataHandler.class);
	
	public Object generateOutput(Map<String, Object>  input) {
		Map<String,InstrumentData> symbolData = (Map<String,InstrumentData>)input.get(DataServiceKeys.DATA_RESULT); 
		String startDate = (String)input.get(YahooHistoricalDataHandler.START_DATE);
		String endDate = (String)input.get(YahooHistoricalDataHandler.END_DATE);
		try {
			Object result = assemblyAndLineUpData(symbolData, mmddyyyFormat.parse(startDate), mmddyyyFormat.parse(endDate)); 
			if(input.get("jsonp")!= null){
				String resultStr = result.toString();
				resultStr = input.get("jsonp").toString()+"("+resultStr+")";
				return resultStr;
			}
			
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
		
	}
	
	private Object assemblyAndLineUpData(Map<String, InstrumentData> symbolData, Date startDate, Date endDate) throws IOException, ParseException {
		long start = System.currentTimeMillis();
		Collection<InstrumentData> dataList = symbolData.values();
		Map<Date,String[]> assembledData = new TreeMap<Date, String[]>();
		StringBuilder builder = new StringBuilder("[");//TradeDate"); 

		String data = null;
		int count = 0;
		for (InstrumentData instrumentData : dataList) {
			data = instrumentData.getData();
			//builder.append(",").append(instrumentData.getSecurity()).append("_").append(instrumentData.getSeries());
			if(data != null){
				BufferedReader reader = new BufferedReader(new StringReader(data));
				String line;
				try {
					line = reader.readLine();
					while((line=reader.readLine()) != null){
						String[] tokens = StringUtils.splitPreserveAllTokens(line, FileKeys.DELIMITER);
						String dateStr = tokens[0];
						Date date;
						try {
							date = yyyymmddFormat.parse(dateStr);						
						} catch (ParseException e) {
							throw e;
						}

						String[] series = assembledData.get(date);
						if(series == null){						 
							series = new String[dataList.size()];
							for (int i = 0; i < series.length; i++) {
								series[i] = "";
							} 
							assembledData.put(date, series);
						}
						series[count] = tokens[1];
					}
				} catch (IOException e) {
					throw e;
				}
				count++;	
			}

		}
		//builder.append("\n");

		Set<Entry<Date, String[]>> entries = assembledData.entrySet();
		count = 0;
		for (Entry<Date, String[]> entry : entries) {
			if(count > 0) builder.append(",");
			builder.append("["+entry.getKey().getTime());//yyyymmddFormat.format(entry.getKey()));
			String[] seriesData = entry.getValue();
			for (int i = 0; i < seriesData.length; i++) {
				builder.append(",").append(seriesData[i]+"]");
			}
			count++;
			builder.append("\n");
		}
		builder.append("]");
		logger.info("Ending Assembling of Data in: "+(System.currentTimeMillis()-start)+"ms");
		return builder.toString();
	}

	 
	
	

}
