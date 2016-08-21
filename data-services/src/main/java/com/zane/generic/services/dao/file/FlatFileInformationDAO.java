package com.zane.generic.services.dao.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zane.generic.service.dao.AutoSuggestDAO;
import com.zane.generic.service.obj.AutoSuggestData;
import com.zane.generic.service.obj.AutoSuggestMetaData;
import com.zane.generic.service.obj.FileAutoSuggestMetaData;
import com.zane.generic.util.DataAccessException;

public class FlatFileInformationDAO implements AutoSuggestDAO {

	private static Log logger = LogFactory.getLog(FlatFileInformationDAO.class);
	
	public Collection<AutoSuggestData> getAutoSuggestData(AutoSuggestMetaData metaData) throws DataAccessException {
		logger.info("Starting to Retrieving All Information for ("+metaData.getName()+")");
		Collection<AutoSuggestData> dataSet = new ArrayList<AutoSuggestData>();
		try {
			BufferedReader reader = new BufferedReader(
									new FileReader(new File(((FileAutoSuggestMetaData)metaData).getFileLocation())));
			String line = null;
			AutoSuggestData data = null;
			while ( (line= reader.readLine()) != null) {
				String[] values = StringUtils.split(line, metaData.getDelimiter());
				if(values != null){
					data = new AutoSuggestData();
					data.setKey(values[0]);
					if(values.length >1 ){
						data.setDescription(values[values.length-1]);	
					}else{
						data.setDescription("");
					}						
					dataSet.add(data);	
				}
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		} 
		return dataSet;
	}
}
