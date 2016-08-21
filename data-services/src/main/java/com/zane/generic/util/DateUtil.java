package com.zane.generic.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtil {

	private static final SimpleDateFormat MMddyyyyhhmmss = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	private static final SimpleDateFormat MMddyyyyhhmmss_ST = new SimpleDateFormat("MMddyyyy_hhmmss_a");
	private static final SimpleDateFormat MMddyyyy = new SimpleDateFormat("MM/dd/yyyy");
  private static final Logger logger = Logger.getLogger(DateUtil.class);
	
  public static String covertToMMDDYYYYHHMMSS(Date date){
		return MMddyyyyhhmmss.format(date);
	}

	public static String covertToMMDDYYYY(Date date){
		return MMddyyyy.format(date);
	}
	
	public static String covertToTimeStampFormat(Date date){
		return MMddyyyyhhmmss_ST.format(date);
	}

	public static String convertToMMDDYYYYDate(String dateShortcut) {	
		Calendar cal = Calendar.getInstance();
		int numValue = 0;
		try {
			numValue = Integer.parseInt(dateShortcut.substring(0,dateShortcut.length()-1));
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(),e);
		} 
		if(dateShortcut.endsWith("m")){
			cal.add(Calendar.MONTH, numValue);
		}else if(dateShortcut.endsWith("d")){
			cal.add(Calendar.DAY_OF_YEAR, numValue);
		}else if(dateShortcut.endsWith("y")){
			cal.add(Calendar.YEAR, numValue);
		}		
		logger.info(cal.getTime());
		return covertToMMDDYYYY(cal.getTime());		
	}

}
