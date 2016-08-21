package com.zane.generic.service.obj;

import java.util.Date;

import com.zane.generic.service.dao.AutoSuggestDAO;

public class AutoSuggestMetaData {
	
	public static final String DB_TYPE = "DB";
	public static final String FILE_TYPE = "FILE";
	
	
	private String name;
	private String description;   
	private String delimiter;
	private AutoSuggestDAO dao;
	
	private Date lastUpdated;
	private int updateInterval; 
	private boolean replaceSpecialCharacters;
	
	public AutoSuggestDAO getDao() {
		return dao;
	}
	public void setDao(AutoSuggestDAO dao) {
		this.dao = dao;
	}
	public boolean isReplaceSpecialCharacters() {
		return replaceSpecialCharacters;
	}
	public void setReplaceSpecialCharacters(boolean replaceSpecialCharacters) {
		this.replaceSpecialCharacters = replaceSpecialCharacters;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
  
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public int getUpdateInterval() {
		return updateInterval;
	}
	public void setUpdateInterval(int updateInterval) {
		this.updateInterval = updateInterval;
	}
	 
	

}
