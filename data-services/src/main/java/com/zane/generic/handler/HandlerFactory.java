package com.zane.generic.handler;

import java.util.HashMap;
import java.util.Map;

import com.zane.generic.util.GenericContext;

/**
 * Handler class, used to generate new bean when based on key pass 
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 27, 2008
 */
public class HandlerFactory {
	
	private static Map<String, String> handlers = new HashMap<String, String>();
	private static String defaultHandler;

	public DataEngineHandler getSearchHandler(String handlerKey){ 
		String handlerName =handlers.get(handlerKey); 
		if( handlerName == null){
			handlerName =handlers.get(defaultHandler); 
		}	
		return (DataEngineHandler) GenericContext.getBean(handlerName);
	}
	
 

	public String getDefaultHandler() {
		return defaultHandler;
	}

	public void setDefaultHandler(String defaultH) {
		defaultHandler = defaultH;
	}



	public Map<String, String> getHandlers() {
		return handlers;
	}



	public void setHandlers(Map<String, String> handlers) {
		this.handlers = handlers;
	}
	

}
