package com.zane.generic.handler;

import java.util.Collection;
import java.util.Map;

import com.zane.generic.handler.interceptor.SearchInterceptor;
/**
 * 
 * @author rafidz
 *
 */
public interface DataEngineHandler {

	public void setInputData(Map<String,Object> input);
	public Map<String, Object> getInputData();
	
	public Collection<SearchInterceptor> getPreProcessors();
	public Collection<SearchInterceptor> getPostProcessors();
	
	public Object processRequest();
	public Object postProcessRequest();
	
	
	public void setRequestedOutputter(String requestedOutputter);
	public Object getOutput();
	
}
