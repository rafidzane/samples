package com.zane.generic.handler.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

public class DateFormatInterceptor implements SearchInterceptor {

	private static final Logger logger = Logger.getLogger(DateFormatInterceptor.class);	
	 
	
	public Object processIntercept(Object input){
		Map<String, Object> requestMap = (Map<String, Object>)input; 
		
		/*
		MetaDataManager mgr = ScannerContext.getMetaDataManager();	
		Set<Map.Entry<String,Object>> entrySet	= requestMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			if(key.startsWith(SearchKeys.SEARCH_ATTR_ID)){
				String index = key.substring(SearchKeys.SEARCH_ATTR_ID.length(),key.length());
				SearchAttribute searchAttribute = mgr.getSearchAttribute(new Long(entry.getValue().toString()));
				if(searchAttribute.getDataType().equals(DataType.DATE) ||
						searchAttribute.getDataType().equals(DataType.DATETIME) ){
					String value = (String)requestMap.get(SearchKeys.VALUE+index);
					if(value != null){
						value = value.toLowerCase();
						if(value.endsWith("m") || value.endsWith("d") || value.endsWith("y")){
							String date = DateUtil.convertToMMDDYYYYDate(value);
							requestMap.put(SearchKeys.VALUE+index, date);
						}	
					}					
				}
			}
		}*/
		return requestMap;
	}
	
	  
	

}
