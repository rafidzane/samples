package com.zane.generic.handler.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * filter to intercept all values of String[] and reduce them to String if possible
 * Also decode values 
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 28, 2008
 */
public class StringArr2StringFormatInterceptor implements SearchInterceptor {

	private static final Logger logger = Logger.getLogger(StringArr2StringFormatInterceptor.class);	
	 
	
	public Object processIntercept(Object input){		
		Map<String, Object> requestMap = (Map<String, Object>)input;
		requestMap = convertToStringString(requestMap);		
		return requestMap;
	}
	
	protected Map<String, Object> convertToStringString(Map<String,Object> searchParameters) {
		Map<String,Object> m = new HashMap<String, Object>(searchParameters.size(),.75f);
		Set<Entry<String,Object>> set = searchParameters.entrySet();
		for (Entry<String, Object> entry : set) {	
			if(entry.getValue() instanceof String[]){
				String value = ((String[])entry.getValue())[0];
				if(value != null){
					try {
						if(value.indexOf("%") >=0){
							value = value.replace("+", "${plus}");							
							value = URLDecoder.decode(value,"utf-8");
							value = value.replace("${plus}","+");
						}						
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(),e);
					}
				}
				m.put(entry.getKey(), value);
			}else{
				m.put(entry.getKey(), entry.getValue());
			}		
		}
		return m;
	}

 

}
