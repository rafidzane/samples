package com.zane.generic.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class UrlInvoker {

	private static final Logger logger = Logger.getLogger(UrlInvoker.class);


	HttpClient client = new HttpClient();

 	
 	public String getStringFromURL(String url) throws HttpException, IOException{	
		int iGetResultCode = -1; 
		String result = null;
		GetMethod post = new GetMethod(url);			
		logger.info("Invoking:"+url);		
		iGetResultCode = client.executeMethod(post);		
		//logger.info("Result ["+iGetResultCode+"]: "+url);		
		result = post.getResponseBodyAsString();
		post.releaseConnection();
		return result;
	}
 	
 	public static void main(String[] args) throws Exception{
 		UrlInvoker in = new UrlInvoker();
 		String data = in.getStringFromURL("http://127.0.0.1:8080/ds/idal?rh=yahoo&sdate=01012007&edate=01312009&s1=EQTY[A]:OPEN&s2=EQTY[B]:OPEN&s3=EQTY[C]:OPEN&s4=EQTY[D]:OPEN&");
 		System.out.println(data);
 		
 	}
 	

  	
}
