package com.zane.generic.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zane.generic.service.dao.AutoSuggestDAO;
import com.zane.generic.service.obj.AutoSuggestData;
import com.zane.generic.service.obj.AutoSuggestMetaData;
import com.zane.generic.service.obj.AutoSuggestResultSet;
import com.zane.generic.util.DataAccessException;
import com.zane.generic.util.GenericContext;

public class GenericAutoSuggestService {



	private static final Log logger = LogFactory.getLog(GenericAutoSuggestService.class);


	private Map<String, AutoSuggestMetaData> autoSuggests = 
		new HashMap<String, AutoSuggestMetaData>();

	private Map<String, Collection<AutoSuggestData>> autoSuggestDataMap =	new HashMap<String, Collection<AutoSuggestData>>();


	public AutoSuggestResultSet findValuesForSearchKey(String dataSetKey, String searchKey, int numberOfRow) throws DataAccessException {
		AutoSuggestMetaData autoSuggestMetaData = autoSuggests.get(dataSetKey);
		AutoSuggestResultSet set = null;

		if(autoSuggestMetaData != null){
			String delimiter = autoSuggestMetaData.getDelimiter();
			set = new AutoSuggestResultSet(delimiter);			
			Collection<AutoSuggestData> asDataSet =  
				retrieveAutoSuggestData(autoSuggestMetaData);
			searchKey = searchKey.toLowerCase();	
			for (AutoSuggestData m : asDataSet) {			
				if (set.getMatchesSize() >= numberOfRow)break;
				boolean isFound = false;
				if(m.getKey().toLowerCase().startsWith(searchKey) ){				
					isFound = true;
				}	
				if(isFound){
					set.addMatch(m.getKey()+delimiter+m.getDescription());
				} 
			}		
			for (AutoSuggestData m : asDataSet) {
				if (set.getMatchesSize() >= numberOfRow)break;
				if(m.getDescription().toLowerCase().indexOf(searchKey) > -1){
					boolean isFound = false;
					if(m.getKey().toLowerCase().startsWith(searchKey) ){
						isFound = true;		
					} 
					if(isFound)set.addMatch(m.getKey()+delimiter+m.getDescription());
				}			
			}
			for (AutoSuggestData m : asDataSet) {			
				if (set.getMatchesSize() >= numberOfRow)break;
				boolean isFound = false;
				if(m.getKey().toLowerCase().indexOf(searchKey) > -1){				
					isFound = true;
				}			
				if(isFound)set.addMatch(m.getKey()+delimiter+m.getDescription()); 
			}

			for (AutoSuggestData m : asDataSet) {
				if (set.getMatchesSize() >= numberOfRow)break;
				if(m.getDescription().toLowerCase().indexOf(searchKey) > -1){
					boolean isFound = false;
					if(!set.getMatches().contains(m.getKey()+delimiter+m.getDescription())){
						isFound = true;	
					}
					if(isFound)set.addMatch(m.getKey()+delimiter+m.getDescription());
				}			
			}	
		}		
		return set;
	}


	public static void main(String[] args) throws Exception{
		GenericContext c = new GenericContext();
		c.loadSpringXML("spring.xml");

		GenericAutoSuggestService service = c.getAutoSuggestService();
		AutoSuggestResultSet as = service.findValuesForSearchKey("SYMBOL","TM", 10);
		Collection<String> mi = as.getMatches();
		for (String str : mi) {
			logger.info(str);
		}	

		logger.info("-----------------");
		as = service.findValuesForSearchKey("SYMBOL","TM", 10);
		mi = as.getMatches();
		for (String str : mi) {
			logger.info(str);
		}	
	}

	public Collection<AutoSuggestData> refreshAutoSuggestDataSetValues(AutoSuggestMetaData autoSuggestMetaData) throws DataAccessException {	
		Collection<AutoSuggestData> autoSuggestDataset = retrieveAutoSuggestData(autoSuggestMetaData);	
		if(autoSuggestDataset != null){				
			logger.info("Information for DataSet("+autoSuggestMetaData.getName()+","+autoSuggestMetaData.getName()+") has been refreshed ... :"+autoSuggestDataset.size());				
		}else{
			logger.warn("Information for DataSet("+autoSuggestMetaData.getName()+","+autoSuggestMetaData.getName()+") has been NOT been refreshed ...");
		}			
		return autoSuggestDataset;
	}


	public Collection<AutoSuggestData> retrieveAutoSuggestData(AutoSuggestMetaData autoSuggestMetaData) throws DataAccessException {
		Collection<AutoSuggestData> autoSuggestData = 
			autoSuggestDataMap.get(autoSuggestMetaData.getName());
		Date lastUpdated = autoSuggestMetaData.getLastUpdated();

		boolean needsUpdate = true;
		if(lastUpdated != null){
			Calendar last =Calendar.getInstance();
			last.setTime(lastUpdated);
			Calendar current =Calendar.getInstance();
			current.setTime(new Date());
			current.add(Calendar.DAY_OF_YEAR, -autoSuggestMetaData.getUpdateInterval());
			logger.info("Last Updated List for DataSet("+autoSuggestMetaData.getName()+"): "+lastUpdated.toString());			
			if(current.after(last)){
				logger.info("DataSet("+autoSuggestMetaData.getName()+") Needs Update ... older than ("+autoSuggestMetaData.getUpdateInterval()+") days");
				needsUpdate = true;
			}else{
				needsUpdate = false;
			}
		}	

		if (autoSuggestData == null || needsUpdate ) {
			// do mapping of how to get data....
			logger.info("Getting Information For DataSet("+autoSuggestMetaData.getName()+") ....");			
			AutoSuggestDAO dao = autoSuggestMetaData.getDao();			
			autoSuggestData = dao.getAutoSuggestData(autoSuggestMetaData);		 
			if(autoSuggestMetaData.isReplaceSpecialCharacters()){
				for (AutoSuggestData m : autoSuggestData) {
					filterSpecialChars(m);
				}	
			}	 					
			autoSuggestDataMap.put(autoSuggestMetaData.getName(),autoSuggestData);
			autoSuggestMetaData.setLastUpdated(new Date());
		} else {
			logger.info("Getting Information For DataSet("+autoSuggestMetaData.getName()+") From Cache....");
		}
		return autoSuggestData;
	}


	private void filterSpecialChars(AutoSuggestData m) {
		m.setKey(m.getKey().replace("%", ""));				
		m.setDescription(m.getDescription().replace("\"", ""));

		m.setKey(m.getKey().replace(".", ""));				
		//m.setDescription(m.getDescription().replace(".", ""));
		if(m.getDescription().endsWith("(INC")){
			m.setDescription(m.getDescription().substring(0, m.getDescription().lastIndexOf("(INC")));
		}		
		if(m.getDescription().endsWith(", INC")){
			m.setDescription(m.getDescription().substring(0, m.getDescription().lastIndexOf(", INC")));
		}				
		if(m.getDescription().endsWith(" INC")){
			m.setDescription(m.getDescription().substring(0, m.getDescription().lastIndexOf(" INC")));
		}
	}


	public AutoSuggestMetaData getAutoSuggest(String dataSetKey) {
		return autoSuggests.get(dataSetKey);
	}


	public Map<String, AutoSuggestMetaData> getAutoSuggests() {
		return autoSuggests;
	}


	public void setAutoSuggests(Map<String, AutoSuggestMetaData> autoSuggests) {
		this.autoSuggests = autoSuggests;
	}



}