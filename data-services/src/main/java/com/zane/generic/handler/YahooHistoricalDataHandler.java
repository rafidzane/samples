package com.zane.generic.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zane.generic.obj.GenericError;
import com.zane.generic.obj.InstrumentData;
import com.zane.generic.util.DataServiceKeys;
import com.zane.generic.util.DateUtil;
import com.zane.generic.util.FileKeys;
import com.zane.generic.util.GenericKeys;
/**
 * Sample Handler to Copy and Paste to create new handler as a template
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 28, 2008
 */
public class YahooHistoricalDataHandler extends AbstractDataEngineHandler{


	private static final SimpleDateFormat yyyymmddFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat mmddyyyFormat = new SimpleDateFormat("mmddyyyy");
	private static final Logger logger = Logger.getLogger(YahooHistoricalDataHandler.class);
	public static final String START_DATE = "startdate";
	public static final String END_DATE = "enddate";
	public YahooHistoricalDataHandler(){}


	public Object processRequest(){
		Map searchParameters = getData();		 	
		String userId = getUserId();		
		String tempUserId = (String)searchParameters.get(DataServiceKeys.UID);

		if(tempUserId != null && !"".equals(tempUserId)){
			userId = tempUserId;
		}

		Object result = null;
		long start = System.currentTimeMillis();		
		Object output = null;
		try {

			String startDate = (String)searchParameters.get(GenericKeys.START_DATE);
			String endDate = (String)searchParameters.get(GenericKeys.END_DATE);
			if(endDate == null)endDate = mmddyyyFormat.format(new Date());

			Map<String,InstrumentData> symbolData = new LinkedHashMap<String, InstrumentData>();

			ExecutorService service = Executors.newFixedThreadPool(5);
			Collection<YahooHistoricalCallable> urlCallables = new ArrayList<YahooHistoricalCallable>();
			int count = 1;
			while (searchParameters.get("s"+count) != null){
				String eqtyData = (String) searchParameters.get("s"+count);
				InstrumentData instrument = convertToInstrument(eqtyData);
				symbolData.put(instrument.toString(),instrument);
				YahooHistoricalCallable callable = new YahooHistoricalCallable(instrument, 
						startDate, endDate);
				urlCallables.add(callable);
				count++;
			} 
			List<Future<YahooHistoricalCallable>> f = service.invokeAll(urlCallables, 60l, TimeUnit.SECONDS);

			if(!service.isTerminated()){
				service.shutdown();
			}
			for (Future<YahooHistoricalCallable> future : f) {
				if(future.isDone()  && !future.isCancelled()){
					if(future.get().isDataOk()){
						InstrumentData id = future.get().getInstrument(); 		
					}else{
						//errorCodes.append(future.get().getTicker()+"\n");
					}	
				}
			}
			//result = assemblyAndLineUpData(symbolData, mmddyyyFormat.parse(startDate), mmddyyyFormat.parse(endDate)); 
			long completion = System.currentTimeMillis()-start;
			getData().put(DataServiceKeys.USER_ID, userId);		
			getData().put(START_DATE, startDate);
			getData().put(END_DATE, endDate);
			getData().put(DataServiceKeys.DATA_RESULT, symbolData);
			
			getData().put(DataServiceKeys.DATA_RETRIEVAL_TIME, completion);		
			output = getOutputter().generateOutput(getData());
			setOutput(output);
		} catch (Exception e) {
			GenericError error = new GenericError(500,e.getMessage(),e);			
			error.setStackTrace(e);		
			setOutput(error);
		}

		return output;		
	}


	


	private InstrumentData convertToInstrument(String eqtyData) {
		int indexOpen = eqtyData.indexOf("[");
		int indexClose = eqtyData.indexOf("]:");
		InstrumentData instrument = null;
		try {
			String name = eqtyData.substring(0,indexOpen);
			String security =  eqtyData.substring(indexOpen+1,indexClose).toUpperCase();
			String series =  eqtyData.substring(indexClose+2,eqtyData.length());
			instrument = new InstrumentData(name,security,series);

		} catch (Exception e) {
			throw new RuntimeException("Cannout Parse Data Expression", e);
		}
		return instrument;
	}











}
