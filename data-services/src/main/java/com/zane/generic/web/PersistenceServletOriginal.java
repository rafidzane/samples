package com.zane.generic.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zane.generic.util.GenericContext;
import com.zane.generic.util.GenericKeys;

public class PersistenceServletOriginal extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PersistenceServlet.class); 

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {		
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
	throws ServletException, IOException {		
		long start = System.currentTimeMillis();	
		String userId = getUser(request);		
		
		Map<String, Object> requestMap = convertToStringString(request.getParameterMap());
		Object output = null;
		 
		logger.info("URL is :"+request.getRequestURI());
		/*
		if(output != null  && output instanceof GenericError){
			// dont continue..
		}else{
			StoreOperationHandler handler = new StoreOperationHandler();
			output = handler.handlerOperation(userId, requestMap);	
		}
		*/
		
		request.setAttribute(GenericKeys.RESPONSE, output);
		try {					
		} catch (Exception e) {			
			logger.error(e,e);
		}	finally{			
		}			
	}



	@Override
	public void init(ServletConfig config) throws ServletException {		
		String springXML = config.getInitParameter("springXML");
		GenericContext context = new GenericContext();
		context.loadSpringXML(springXML);		
		
		String log4j = config.getInitParameter("log4j");		
		PropertyConfigurator.configure(log4j);
		logger = Logger.getLogger(PersistenceServlet.class);
		String[] names = context.getAllBeanNames();
		for (int i = 0; i < names.length; i++) {
			logger.info("Loaded Bean:"+names[i]);
		}
	}

	private String getUser (HttpServletRequest request) {
		String userId = request.getRemoteUser();
		if(userId == null) {
			userId = request.getHeader("Proxy-Remote-User");
			if(userId == null) {
				userId = request.getHeader("proxy-remote-user");
				if(userId == null) {
					userId = request.getHeader("PROXY-REMOTE-USER");
					if(userId == null){
						userId = request.getParameter(GenericKeys.USER_ID);
						if(userId == null)
							userId = new String("UNKNOWN");
					}				 
				}
			}
		}
		request.getSession().setAttribute(GenericKeys.USER_ID, userId);
		return  userId;
	}
	
	private Map<String, Object> convertToStringString(Map<String,Object> searchParameters) {
		Map<String,Object> m = new HashMap<String, Object>(searchParameters.size(),.75f);
		Set<Entry<String,Object>> set = searchParameters.entrySet();
		for (Entry<String, Object> entry : set) {	
			if(entry.getValue() instanceof String[]){
				String value = ((String[])entry.getValue())[0];
				if(value != null){
					try {
						value = URLDecoder.decode(value,"utf-8");
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
