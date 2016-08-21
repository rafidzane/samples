package com.zane.generic.util;

import com.zane.generic.obj.GenericError;
 

public class ErrorConvertor {

	public String convertToXML(GenericError error){
		StringBuilder builder = new StringBuilder();		
		builder.append("<Error>\n");
		builder.append("<code>").append(error.getCode()).append("</code>\n");
		builder.append("<message><![CDATA[").append(error.getMessage()).append("]]></message>\n");
		if(error.getStackTrace() != null){
			builder.append("<stack-trace><![CDATA[").append(error.getStackTrace()).append("]]></stack-trace>\n");	
		}		
		builder.append("</Error>\n");
		
		
		return builder.toString();
	}

}
