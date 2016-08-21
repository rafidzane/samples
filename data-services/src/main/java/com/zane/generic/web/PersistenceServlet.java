package com.zane.generic.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zane.generic.handler.DataEngineHandler;
import com.zane.generic.handler.HandlerFactory;
import com.zane.generic.handler.interceptor.SearchInterceptor;
import com.zane.generic.util.DataServiceKeys;
import com.zane.generic.util.GenericContext;
import com.zane.generic.util.GenericKeys;

public class PersistenceServlet extends HttpServlet {

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
		//logger.info("User:"+userId);
		logger.info("Started Request: for "+userId+","+request.getQueryString());
		
		String requestedOutput = request.getParameter(DataServiceKeys.REQUESTED_OUTPUT);
		String requestHandler = request.getParameter(DataServiceKeys.REQUEST_HANDLER);
		HandlerFactory factory = GenericContext.getHandlerFactory();
		DataEngineHandler handler = factory.getSearchHandler(requestHandler);		
		Object result = null;
		try {
			if(requestedOutput != null){
				handler.setRequestedOutputter(requestedOutput);
			}			
					
			Map input = request.getParameterMap();
			
			Collection<SearchInterceptor> pres = handler.getPreProcessors();	
			for (SearchInterceptor pre : pres) {
				input = (Map) pre.processIntercept(input);
			}
			handler.setInputData(input);
			handler.processRequest();
			Collection<SearchInterceptor> posts = handler.getPreProcessors();
			handler.getInputData().clear();
			result = handler.getOutput();
			 
			request.setAttribute(GenericKeys.RESPONSE, result);
			
		} catch (Exception e) {			
			logger.error(e,e);
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
		logger.info("Starting to Log for DS");
		 
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
						userId = request.getParameter(DataServiceKeys.USER_ID);
						if(userId == null)
							userId = request.getRemoteHost();
					}				 
				}
			}
		}
		request.getSession().setAttribute(DataServiceKeys.USER_ID, userId);
		return  userId;
	}

}
