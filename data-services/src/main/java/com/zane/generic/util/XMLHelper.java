package com.zane.generic.util;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XMLHelper {
	private static final Logger logger = Logger.getLogger(XMLHelper.class);
	private static DocumentBuilderFactory factory;
	private static DocumentBuilder parser;

	
	private static synchronized void init() throws XMLAccessException{
		try {
			logger.info("Intializing XMLHelper..");
			factory = DocumentBuilderFactory.newInstance();
			parser = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {			
			logger.error(e.getMessage(),e);
			throw new XMLAccessException(e.getMessage(),e);
		}
	} 

	public static synchronized Document createDocument(String xml) throws XMLAccessException{
		try {
			if(parser == null){
				init();
			}
			return parser.parse(new InputSource(new StringReader(xml)));			
		} catch (Exception e) {			
			logger.error("Error occurred parsing xml",e);
			throw new XMLAccessException(e);
		}		
	}


	public static synchronized Document createDocument(File xml){
		try{
			if(parser == null){
				init();
			}
			return parser.parse(xml);
		} catch (Exception e) {					
			logger.error("Error occurred parsing xml");
			logger.error(xml);
			logger.error(e);
		}
		return null;
	}

}
