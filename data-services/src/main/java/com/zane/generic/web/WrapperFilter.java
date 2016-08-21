package com.zane.generic.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.zane.generic.cache.GenericCache;
import com.zane.generic.cache.GenericCacheException;
import com.zane.generic.handler.CRUDKeys;
import com.zane.generic.obj.GenericError;
import com.zane.generic.util.DateUtil;
import com.zane.generic.util.ErrorConvertor;
import com.zane.generic.util.GenericContext;
import com.zane.generic.util.GenericKeys;
import com.zane.generic.xml.CDATAPrinterDriver;
import com.zane.generic.xml.ResponseBuilder;

public class WrapperFilter implements Filter {

	private static final Logger logger = Logger.getLogger(WrapperFilter.class);
	private static XStream x = new XStream(new CDATAPrinterDriver());

	static{

	}

	public void init(FilterConfig config) {		
	}


	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		String llUserID = getUser(req);
		logger.info("Starting Engine Request "+llUserID+","+req.getQueryString());
		long start = System.currentTimeMillis();

		GenericCache cache = (GenericCache) GenericContext.getBean("dataEngineCache");

		boolean debug = Boolean.valueOf(req.getParameter("debug"));
		boolean dataOnly = Boolean.valueOf(req.getParameter(GenericKeys.DATA_ONLY));
		boolean useCache = true;
		if(req.getParameter(GenericKeys.USE_CACHE) != null){
			useCache = Boolean.valueOf(req.getParameter(GenericKeys.USE_CACHE));
		} 

		String cacheKey = generateCacheKey(llUserID, req.getParameterMap());
		String responseXml = null;
		if(useCache){
			try {
				responseXml = (String) cache.getFromCache(cacheKey);
			} catch (GenericCacheException e1) {
			}
		}
		Object attribute = null;
		if(!dataOnly && debug){
			response.setContentType("text/xml");	
		}else{
			response.setContentType("text/plain");
		}

		Date requestDate = new Date();
		if (responseXml == null){ 
			StringBuilder preBuilder = new StringBuilder();
			StringBuilder postBuilder = new StringBuilder();
			if(!dataOnly){
				preBuilder.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");		
				preBuilder.append("<GenericEnvelope>");

				preBuilder.append("<DataRequest>");
				preBuilder.append("<UserId>${userId}</UserId>");
				preBuilder.append("<OriginalRequestTime>").append(DateUtil.covertToMMDDYYYYHHMMSS(requestDate)).append("</OriginalRequestTime>");
				preBuilder.append("<CacheRequestTime>${cacheDate}</CacheRequestTime>");
				
				if(debug){
					preBuilder.append("<RequestParameters>");
					Map<String,String[]> params = req.getParameterMap();
					Set<Entry<String, String[]>> entrySet = params.entrySet();
					for (Entry<String, String[]> entry : entrySet) {
						preBuilder.append("<parameter>");
						preBuilder.append("<key>").append(entry.getKey()).append("</key>");
						preBuilder.append("<value>").append("<![CDATA[").append(entry.getValue()[0]).append("]]></value>");			
						preBuilder.append("</parameter>");			
					}
					preBuilder.append("</RequestParameters>");
				}
				preBuilder.append("</DataRequest>");

				preBuilder.append("<DataResponse>");		
			}

			//response.getWriter().write(preBuilder.toString());		
			chain.doFilter(request, response);
			attribute = request.getAttribute(GenericKeys.RESPONSE);
			//action = translateAction(req);
			if(!dataOnly){
				//postBuilder.append("<Action>").append(action).append("</Action>");
				
				postBuilder.append("<ExecutedIn>${executedTime}</ExecutedIn>");
			} 
			int status = 200;
			ErrorConvertor c = null;
			if(attribute instanceof GenericError){
				status = ((GenericError)attribute).getCode();
				c = new ErrorConvertor();			
			}
			if(attribute instanceof GenericError){
				if(!dataOnly){
					postBuilder.append("<Status>").append("Error").append("</Status>");
					postBuilder.append(c.convertToXML((GenericError) attribute));
					postBuilder.append("</DataResponse>");
					postBuilder.append("</GenericEnvelope>");
				}else{
					postBuilder.append(((GenericError) attribute).getStackTrace());
				}

			}else{
				if(!dataOnly){
					postBuilder.append("<Status>").append("Successful").append("</Status>");
					postBuilder.append("<Response>").append(ResponseBuilder.generateResponseXML(attribute)).append("</Response>");

				}else{
					postBuilder.append(ResponseBuilder.generateResponseXML(attribute));
				} 
				if(!dataOnly){
					postBuilder.append("</DataResponse>");
					postBuilder.append("</GenericEnvelope>");
				}
			}
			try {
				if(responseXml == null){
					cache.putInCache(cacheKey, preBuilder.append(postBuilder).toString());
				}
			} catch (Exception e) {
				logger.warn(e.getMessage(),e);
			}
			if(!dataOnly){
				
			}
			responseXml = preBuilder.toString();
			responseXml = populateParameterMarkers(llUserID, start, responseXml, requestDate);
			
			response.getWriter().write(responseXml);		
		}else{

			responseXml = populateParameterMarkers(llUserID, start, responseXml, requestDate);
			response.getWriter().write(responseXml);
		}
		long finish = System.currentTimeMillis();
		logger.info("Ending Engine Request Filter for "+llUserID+" in: "+(finish-start)+"ms");
	}


	private String populateParameterMarkers(String llUserID, long start,
			String responseXml, Date requestDate) {
		responseXml = responseXml.replace("${userId}", llUserID);
		responseXml = responseXml.replace("${cacheDate}", DateUtil.covertToMMDDYYYYHHMMSS(requestDate));
		responseXml = responseXml.replace("${executedTime}", System.currentTimeMillis()-start+"ms");
		return responseXml;
	}

	private String translateAction(HttpServletRequest req) {
		String action = req.getParameter(GenericKeys.ACTION);
		if(CRUDKeys.DELETE.equalsIgnoreCase(action)){
			action = "Delete";
		}else if(CRUDKeys.SAVEUPDATE.equalsIgnoreCase(action)){
			action = "Save Update";
		}else if(CRUDKeys.RETRIEVE.equalsIgnoreCase(action)){
			action = "Retrieve";
		}else{
			action = "N/A";
		}
		return action;
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
							userId = new String(request.getRemoteAddr());
					}				 
				}
			}
		}
		request.getSession().setAttribute(GenericKeys.USER_ID, userId);
		return  userId;
	}

	private String generateCacheKey(String llUserID, Map params) {		
		String[] bypassUserArr = (String[])params.get(GenericKeys.USER_ID);
		StringBuilder builder = new StringBuilder();
		if(bypassUserArr != null){
			llUserID = bypassUserArr[0];			
		} 	
		builder.append(llUserID).append("_");

		Set<Entry<String, String[]>> set =  params.entrySet();
		for (Entry<String, String[]> entry : set) {
			if(!entry.getKey().equals(GenericKeys.USE_CACHE)){
				builder.append(entry.getKey()).append("_").append(entry.getValue()[0]).append("_");	
			}
			
		}		  
		return ""+builder.toString().hashCode();
	}

}
