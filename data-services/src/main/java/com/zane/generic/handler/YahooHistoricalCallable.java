package com.zane.generic.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zane.generic.cache.GenericCache;
import com.zane.generic.cache.GenericCacheException;
import com.zane.generic.obj.InstrumentData;
import com.zane.generic.util.DateUtil;
import com.zane.generic.util.FileKeys;
import com.zane.generic.util.GenericContext;


public class YahooHistoricalCallable implements Callable<YahooHistoricalCallable> {


	private static final Logger logger = Logger.getLogger(YahooHistoricalCallable.class);

	private SimpleDateFormat yyyymmddFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat mmddyyyFormat = new SimpleDateFormat("MMddyyyy");

	private boolean isDataOk;
	private InstrumentData instrument;

	private String startDate;
	private String endDate;

	private String startDay;
	private String startMonth;
	private String startYear;

	private String endDay;
	private String endMonth;
	private String endYear;



	public YahooHistoricalCallable(
			InstrumentData instrument, 
			String startDate, String endDate 
	){
		this.instrument = instrument;
		this.startDate = startDate;
		this.endDate = endDate;
		String[] startArr = convertDateToArray(startDate);
		setStartDay(startArr[1]);
		setStartMonth(startArr[0]);
		setStartYear(startArr[2]);

		String[] endArr = convertDateToArray(endDate);	
		setEndMonth(endArr[1]);
		setEndDay(endArr[0]);
		setEndYear(endArr[2]);

	}
 
	public YahooHistoricalCallable call()  {

		String data = null;
		String[] currentDate = convertDateToArray(mmddyyyFormat.format(new Date()));
		GenericCache cache = GenericContext.getHistoricalDataCache();
		try {
			data = (String) cache.getFromCache(instrument.getSecurity()+"_"+instrument.getSeries());
		} catch (GenericCacheException e1) {
		}
		if(data == null){
			HttpClient client = new HttpClient();
			GetMethod method = null; 
			try {
				long start = System.currentTimeMillis();	
				client.getHttpConnectionManager().getParams().setSoTimeout(3000);
				String url = "http://ichart.finance.yahoo.com/table.csv?s="+URLEncoder.encode(instrument.getSecurity(),"utf-8")+"&a=01&b=01&c=1900&g=d&d="+(Integer.parseInt(currentDate[0])-1)+"&e="+currentDate[1]+"&f="+currentDate[2]+"&ignore=.csv";
				logger.info("Executing:"+url);
				method = new GetMethod(url);
				client.executeMethod(method);
				if(method.getStatusCode() == 404){
					isDataOk = false;
				}else{
					isDataOk = true;
					InputStream s = method.getResponseBodyAsStream(); 
					data = IOUtils.toString(s); 									
				}
				logger.info("Finished Retrieving "+instrument.getSecurity()+" in "+(System.currentTimeMillis()-start));
			} catch (Exception e) { 
				e.printStackTrace();
			} 
			method = null;
			client = null;	
		}
		if(data != null){
			isDataOk = true;			
			try {
				cache.putInCache(instrument.toString(), data);
				long startTime = System.currentTimeMillis();
				filterRequestedSeries(data);
				logger.info("Completed in: "+(System.currentTimeMillis()-startTime)+"ms for "+instrument.getSecurity());
			} catch (Exception e) {
				isDataOk = false;
				e.printStackTrace();
			}
		}  
		return this;
	}

	private void filterRequestedSeries(String data) throws IOException, ParseException {
		BufferedReader reader = new BufferedReader(new StringReader(data));
		StringBuilder dataBuffer = new StringBuilder();
		try {
			String line = reader.readLine();
			String series = instrument.getSeries();
			dataBuffer.append("TDATE").append(FileKeys.DELIMITER)
			.append(instrument.getSecurity()).append("_").append(series)
			.append(FileKeys.LINE_BREAK);
			//Open,High,Low,Close,Volume,Adj Close 


			Date sDate = mmddyyyFormat.parse(startDate);
			Date eDate = mmddyyyFormat.parse(endDate);
			while((line=reader.readLine()) != null){
				String[] tokens = StringUtils.splitPreserveAllTokens(line, FileKeys.DELIMITER);
				//System.out.println(line);

				Date date = null;;
				try {
					date = yyyymmddFormat.parse(tokens[0]);
				} catch (Exception e) {
					System.out.println("writing:"+line);
					e.printStackTrace();
				}
				if(!date.before(sDate) && !date.after(eDate)) {
					dataBuffer.append(tokens[0]).append(FileKeys.DELIMITER);
					if("Open".equalsIgnoreCase(series)){
						dataBuffer.append(tokens[1]).append(FileKeys.DELIMITER);					
					}else if("High".equalsIgnoreCase(series)){
						dataBuffer.append(tokens[2]).append(FileKeys.DELIMITER);
					}else if("Low".equalsIgnoreCase(series)){
						dataBuffer.append(tokens[3]).append(FileKeys.DELIMITER);
					}else if("Close".equalsIgnoreCase(series)){
						dataBuffer.append(tokens[4]).append(FileKeys.DELIMITER);
					}else if("Volume".equalsIgnoreCase(series)){
						dataBuffer.append(tokens[5]).append(FileKeys.DELIMITER);
					}else if("Adj Close".equalsIgnoreCase(series)){
						dataBuffer.append(tokens[6]).append(FileKeys.DELIMITER);
					}
					dataBuffer.append(FileKeys.LINE_BREAK);
				}
			}
		} catch (IOException e) {
			throw e;
		}
		instrument.setData(dataBuffer.toString());

	}

	private String[] convertDateToArray(String date) {
		if(date != null){
			String[] array = new String[3];
			array[0] = date.substring(0,2);
			array[1] = date.substring(2,4);
			array[2] = date.substring(4,8);
			return array;
		}
		return null;
	}

	public boolean isDataOk() {
		return isDataOk;
	}

	public void setDataOk(boolean isDataOk) {
		this.isDataOk = isDataOk;
	}



	/**
	 * @return the startDay
	 */
	public String getStartDay() {
		int day = Integer.parseInt(startDay)+1;
		return String.valueOf(day);
	}

	/**
	 * @param startDay the startDay to set
	 */
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	/**
	 * @return the startMonth
	 */
	public String getStartMonth() {
		int month = Integer.parseInt(startMonth)-1;
		return String.valueOf(month);
	}

	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	/**
	 * @return the startYear
	 */
	public String getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	/**
	 * @return the endDay
	 */
	public String getEndDay() {
		return endDay;
	}

	/**
	 * @param endDay the endDay to set
	 */
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	/**
	 * @return the endMonth
	 */
	public String getEndMonth() {
		int month = Integer.parseInt(endMonth)-1;
		return String.valueOf(month);
	}

	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	/**
	 * @return the endYear
	 */
	public String getEndYear() {
		return endYear;
	}

	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	/**
	 * @return the instrument
	 */
	public InstrumentData getInstrument() {
		return instrument;
	}

	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(InstrumentData instrument) {
		this.instrument = instrument;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



}
