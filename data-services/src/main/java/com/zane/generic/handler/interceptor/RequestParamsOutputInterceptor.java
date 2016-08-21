package com.zane.generic.handler.interceptor;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * Class to log out request parameters.
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 28, 2008
 */
public class RequestParamsOutputInterceptor implements SearchInterceptor {

	private static final Logger logger = Logger.getLogger(RequestParamsOutputInterceptor.class);	
	 
	
	public Object processIntercept(Object input){		
		Map<String, Object> requestMap = (Map<String, Object>)input;
		StringBuffer buffer = new StringBuffer();
		try {
			Set<Entry<String, Object>> entrySet = requestMap.entrySet();
			buffer = new StringBuffer();
			for (Entry<String, Object> entry : entrySet) {
				Object value = entry.getValue();
				if(value instanceof String[]){
					String[] strValues = (String[])value;
					for (int i = 0; i < strValues.length; i++) {
						buffer.append(entry.getKey()+"="+strValues[i]).append("&");	
						logger.info(entry.getKey()+"="+strValues[i]);
					}				
				}else{
					buffer.append(entry.getKey()+"="+entry.getValue()).append("&");
					//logger.info(entry.getKey()+"="+entry.getValue());
				}			
			}
		} catch (RuntimeException e) {
			logger.error(e,e);
		}		
		logger.info(buffer.toString());		
		return requestMap;
	}
	
	 
 

}
