package com.zane.generic.handler;

import java.util.Collection;
import java.util.Map;

import com.zane.generic.obj.GenericError;
import com.zane.generic.service.GenericAutoSuggestService;
import com.zane.generic.service.ServiceKeys;
import com.zane.generic.service.obj.AutoSuggestData;
import com.zane.generic.service.obj.AutoSuggestMetaData;
import com.zane.generic.util.DataServiceKeys;
import com.zane.generic.util.GenericContext;

public class AutoSuggestRefreshHandler extends AbstractDataEngineHandler{

	private int defaultRowNumber;

	public int getDefaultRowNumber() {
		return defaultRowNumber;
	}


	public void setDefaultRowNumber(int defaultRowNumber) {
		this.defaultRowNumber = defaultRowNumber;
	}


	public AutoSuggestRefreshHandler(){}


	public Object processRequest(){
		Map searchParameters = getData();

		String refresh = (String) searchParameters.get(ServiceKeys.REFRESH);		

		GenericAutoSuggestService service = GenericContext.getAutoSuggestService();
		String dataSetKey = "";
		AutoSuggestMetaData metaData = service.getAutoSuggest(dataSetKey);
		boolean isRefreshed = false;
		Collection<AutoSuggestData> infos = null;
		if("true".equalsIgnoreCase(refresh)){
			try {	
				service.refreshAutoSuggestDataSetValues(metaData);
				infos = service.retrieveAutoSuggestData(metaData);
				infos = service.refreshAutoSuggestDataSetValues(metaData);		
				isRefreshed = true;
			} catch (Exception e) {
				GenericError error = new GenericError(500,"An error occurred refreshing Symbol Information");			
				error.setStackTrace(e);		
				setOutput(error);
				isRefreshed = false;
			}
		}	 
		getData().put(DataServiceKeys.DATA_RESULT,infos);	
		//getData().put(DataServiceKeys.LAST_UPDATED,metaData.getLastUpdated());
		//getData().put(ReportingKeys.REFRESHED,isRefreshed);
		Object output = getOutputter().generateOutput(getData());
		setOutput(output);
		return output;		 
	}









}
