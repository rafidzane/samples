package com.zane.generic.handler.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * Class to inject debug values into incoming request
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 28, 2008
 */
public class DebugInterceptor implements SearchInterceptor {

	private static final Logger logger = Logger.getLogger(DebugInterceptor.class);	
	
	private Map<String,String> debugValues;
	
	public Object processIntercept(Object input){		
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.putAll((Map<String, Object>)input);
		requestMap.putAll(debugValues);
		return requestMap;
	}

	public Map<String, String> getDebugValues() {
		return debugValues;
	}

	public void setDebugValues(Map<String, String> debugValues) {
		this.debugValues = debugValues;
	}
	
	 
 

}
