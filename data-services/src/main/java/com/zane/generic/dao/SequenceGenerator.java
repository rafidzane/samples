package com.zane.generic.dao;

import java.text.SimpleDateFormat;
import java.util.Random;

import org.apache.log4j.Logger;

public class SequenceGenerator {
	
	private static final Logger logger = Logger.getLogger(SequenceGenerator.class);
	private static final SimpleDateFormat idFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static Random r = new Random();
	
	public static synchronized Long generateSequenceId(){		 
		String id = idFormat.format(new java.util.Date())+ Math.abs(r.nextInt());
		if(id.length()>19){
			id = id.substring(0,19);
		}		
		return new Long(id);
	}
	
}
