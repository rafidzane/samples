package com.zane.generic.service.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class GenericDataSourceProvider {
	
	private Map<String, DataSource> datasourceMap = new HashMap<String,DataSource>();
	
	public Map<String, DataSource> getDatasourceMap() {
		return datasourceMap;
	}

	public void setDatasourceMap(Map<String, DataSource> datasourceMap) {
		this.datasourceMap = datasourceMap;
	}
	
	
	
	/*
	public Connection getDSConnnectionByUser(String dsName, String user,
			String password) throws SQLException 
	{		
		DataSource ds = null;
		if(this.tkContext){
			
			ds = connSvr.getDataSource(dsName);				
		}else{
			ds = (DataSource) dsContext.getBean(dsName);
		}
		if(ds==null) throw new SQLException("DataSource has not been defined [" + dsName + "].");
		Connection conn = null;
		if(user!=null && this.tkContext){
			String pass = passes.getProperty(dsName+"_" + user);
			conn = ds.getConnection(user, pass);
		}else{
			conn = ds.getConnection();
		}
		return conn;
	}
	*/
	
	
	

}
