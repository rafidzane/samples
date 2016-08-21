package com.zane.generic.handler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zane.generic.handler.interceptor.SearchInterceptor;
import com.zane.generic.handler.output.DataEngineOutput;
/**
 * Abstract Base Handler which provides methods/variables used throughout
 * all handlers
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 27, 2008
 */
public abstract class AbstractDataEngineHandler implements DataEngineHandler{

	private Map<String,Object> data = new HashMap<String, Object>();
	private Map<String,DataEngineOutput> outputters; 
	private Object output;

	private Collection<SearchInterceptor> preProcessors;
	private Collection<SearchInterceptor> postProcessors; 



	private String defaultOutputter;
	private String requestedOutputter;
	private String userId;
	private static final Logger logger = Logger.getLogger(AbstractDataEngineHandler.class);


	
	public DataEngineOutput getOutputter() {
		DataEngineOutput outputter = null;
		if(requestedOutputter == null){
			outputter = outputters.get(defaultOutputter);
		}else{
			outputter = outputters.get(requestedOutputter);
			if(outputter == null){
				outputter = outputters.get(defaultOutputter);
			}
		}
		return outputter;
	}

	 


	public String getRequestedOutputter() {
		return requestedOutputter;
	}

	public void setRequestedOutputter(String requestedOutputter) {
		this.requestedOutputter = requestedOutputter;
	}

	public Map<String, DataEngineOutput> getOutputters() {
		return outputters;
	}

	public void setOutputters(Map<String, DataEngineOutput> outputters) {
		this.outputters = outputters;
	}

	public String getDefaultOutputter() {
		return defaultOutputter;
	}



	public void setDefaultOutputter(String defaultOutputter) {
		this.defaultOutputter = defaultOutputter;
	}

	public Map<String, Object> getData() {
		return data;
	}
	
	public Map<String, Object> getInputData() {
		return data;
	}

	public void setInputData(Map<String, Object> data) {
		this.data.putAll(data);
	}

	public void addInputData(String key, Object data) {
		this.data.put(key, data);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Object postProcessRequest() {		
		return null;
	}

	public Object preProcessRequest() {		
		return null;
	}

	public Collection<SearchInterceptor> getPreProcessors() {
		return preProcessors;
	}

	public void setPreProcessors(Collection<SearchInterceptor> preProcessors) {
		this.preProcessors = preProcessors;
	}

	public Collection<SearchInterceptor> getPostProcessors() {
		return postProcessors;
	}

	public void setPostProcessors(Collection<SearchInterceptor> postProcessors) {
		this.postProcessors = postProcessors;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}



}
