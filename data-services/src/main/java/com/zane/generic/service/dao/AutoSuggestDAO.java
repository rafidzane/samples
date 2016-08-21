package com.zane.generic.service.dao;

import java.util.Collection;

import com.zane.generic.service.obj.AutoSuggestData;
import com.zane.generic.service.obj.AutoSuggestMetaData;
import com.zane.generic.util.DataAccessException;

public interface AutoSuggestDAO {
	
	public Collection<AutoSuggestData> getAutoSuggestData(AutoSuggestMetaData metaData) throws DataAccessException;
	
}
