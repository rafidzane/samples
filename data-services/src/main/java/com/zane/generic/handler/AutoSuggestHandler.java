package com.zane.generic.handler;

import java.util.Map;

import com.zane.generic.obj.GenericError;
import com.zane.generic.service.GenericAutoSuggestService;
import com.zane.generic.service.ServiceKeys;
import com.zane.generic.service.obj.AutoSuggestResultSet;
import com.zane.generic.util.DataServiceKeys;
import com.zane.generic.util.GenericContext;
 
public class AutoSuggestHandler extends AbstractDataEngineHandler{
	
	private int defaultRowNumber;
	
	public int getDefaultRowNumber() {
		return defaultRowNumber;
	}


	public void setDefaultRowNumber(int defaultRowNumber) {
		this.defaultRowNumber = defaultRowNumber;
	}


	public AutoSuggestHandler(){}
	 
	  
	public Object processRequest(){
		Map searchParameters = getData();
		
		String searchKeyDataSet = (String) searchParameters.get(ServiceKeys.SEARCH_DATASET);
		String searchValue = (String) searchParameters.get(ServiceKeys.SEARCH_VALUE);
		String numberOfRow = (String) searchParameters.get(ServiceKeys.SEARCH_RESULT_SIZE);
		
		GenericAutoSuggestService service = 
			         GenericContext.getAutoSuggestService();		
		AutoSuggestResultSet rs = null;
		Object output = null;
		try {
			int rows = defaultRowNumber;			
			try {
				 rows = Integer.parseInt(numberOfRow);
			} catch (Exception e) {
				rows = defaultRowNumber;
			}
			rs = service.findValuesForSearchKey(searchKeyDataSet,searchValue ,rows);
			getData().put(DataServiceKeys.DATA_RESULT, rs);	
			output = getOutputter().generateOutput(getData());
			
		} catch (Exception e) {
			GenericError error = new GenericError(500,e.getMessage(),e);		 
			output = error;
		}
		setOutput(output);
		return output;		 
	}

	 

  

	
	 
	  

}
