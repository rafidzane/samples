package com.zane.generic.handler.output;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.zane.generic.service.obj.AutoSuggestData;
import com.zane.generic.util.DataServiceKeys;

 
public class AutoSuggestRefreshJSONOutputter implements DataEngineOutput {

	 

	public Object generateOutput(Map<String, Object>  input) {
		StringBuilder builder = new StringBuilder();
		
		Collection<AutoSuggestData> rs = (Collection<AutoSuggestData>)input.get(DataServiceKeys.DATA_RESULT);	
		Date lastUpdated = null; //(Date)input.get(DataServiceKeys.LAST_UPDATED);
		Boolean refreshed = null; //(Boolean)input.get(DataServiceKeys.REFRESHED);
		
		//builder.append("{\"ResultSet\":[{\"Value\":\"Symbol AutoSuggest Values has "+rs.size()+" values available. Last Updated:"+DateUtil.covertToMMDDYYYYHHMMSS(lastUpdated)+"\",\n");				
		builder.append("\"Refreshed\":\""+refreshed+"\"");	
		builder.append("}]}");
		return builder.toString();	
	}
	
}
