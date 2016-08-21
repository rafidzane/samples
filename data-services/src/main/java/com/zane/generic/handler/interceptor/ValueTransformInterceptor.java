package com.zane.generic.handler.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;
/**
 * Important Class which checks searchAttribute values and
 * decodes them into RADAR usable values
 * 
 * This includes converting date formats to date (-1m,-1d,-1y)
 * Converting Mapping values into dataValues (i.e United States -->  US )
 * 
 * Check to see if any search attribute is a UserPortfolio attribute
 * To set a boolean true
 * @author Rafid Wahab (rwahab)
 * @version 1.0, Aug 28, 2008
 */
public class ValueTransformInterceptor implements SearchInterceptor {

	private static final Logger logger = Logger.getLogger(ValueTransformInterceptor.class);	

	private String universeKey;
	private String universeOperator;
	
	public Object processIntercept(Object input){

		Map<String, Object> requestMap = (Map<String, Object>)input; 
		/*
		MetaDataManager mgr = ScannerContext.getMetaDataManager();	
		Set<Map.Entry<String,Object>> entrySet	= requestMap.entrySet();
		Map<String, Object> mapOverwrite = new HashMap<String, Object>();
		try {
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey(); 
				if(key.startsWith(SearchKeys.SEARCH_ATTR_ID)){
					String keyValue = (String)entry.getValue();
					String index = key.substring(SearchKeys.SEARCH_ATTR_ID.length(),key.length());
					String value = (String)requestMap.get(SearchKeys.VALUE+index);
					String operId = (String)requestMap.get(SearchKeys.OPER_ID+index);
					
					String transformedValue = null;
					
					if(keyValue.equalsIgnoreCase(SearchKeys.UNIVERSE)){
						keyValue = universeKey;
						operId = universeOperator;
						mapOverwrite.put(key, universeKey);
						mapOverwrite.put(SearchKeys.OPER_ID+index, universeOperator);					
					}
					
					SearchAttribute searchAttribute = mgr.getSearchAttribute(new Long(keyValue));				
					Operator oper = mgr.getOperator(operId);
					
					Collection<String> values = new ArrayList<String>();
					if(value != null){						
						String[] valueTokens = new String[]{value};//StringUtils.splitPreserveAllTokens(value,",");				
						for (int i = 0; i < valueTokens.length; i++) {
							String tempValue = valueTokens[i];
							transformedValue = null;
							if(searchAttribute.containsInstructionKey(InstructionKeys.ENUMERATION) 
									&& !"in".equalsIgnoreCase(oper.getValue())){
								String mappingId = searchAttribute.getInstructionValue(InstructionKeys.MAPID);
								if(mappingId != null){						
									transformedValue = mgr.getMappingDataValue(mappingId,tempValue);	
								}					
							}  
              
							if(searchAttribute.getTableName().equals("UNV_PORTFOLIO")){
								mapOverwrite.put(DataServiceKeys.HAS_USER_PORTFOLIO, "true");
							}
							
							if(searchAttribute.getDataType().equals(DataType.DATE) ||
									searchAttribute.getDataType().equals(DataType.DATETIME) ){

								if(tempValue != null){
									tempValue = tempValue.toLowerCase();
									if(tempValue.endsWith("m") || tempValue.endsWith("d") || tempValue.endsWith("y")){
										transformedValue = DateUtil.convertToMMDDYYYYDate(tempValue);							
									}	
								}					
							}
							if(transformedValue == null){
								transformedValue = tempValue;
							}
							values.add(transformedValue);
						}

						StringBuffer transformBuffer = new StringBuffer();
						boolean isFirst = true;
						for (String tValue : values) {
							if(isFirst){
								transformBuffer.append(tValue);
								isFirst = false;
							}else{
								transformBuffer.append(",").append(tValue);
							}
						}
						if(transformBuffer != null){
							mapOverwrite.put(SearchKeys.VALUE+index, transformBuffer.toString());	
						}
					}
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		requestMap.putAll(mapOverwrite);
		*/
		return requestMap;
	}

	public String getUniverseKey() {
		return universeKey;
	}

	public void setUniverseKey(String universeKey) {
		this.universeKey = universeKey;
	}

	public String getUniverseOperator() {
		return universeOperator;
	}

	public void setUniverseOperator(String universeOperator) {
		this.universeOperator = universeOperator;
	}




}
