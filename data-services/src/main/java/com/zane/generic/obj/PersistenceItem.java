package com.zane.generic.obj;

import java.io.Serializable;
import java.util.Date;

import com.zane.generic.dao.SequenceGenerator;

public class PersistenceItem implements Serializable{

	private Long gpsId;
	private String applicationId;
	private String userId;
	private String key;
	private String value;
	private String nameSpace;
	private Date creationDate;
	private Date lastModified;
	private Date lastAccessed;
	
	public PersistenceItem(){		
	}
	
	public PersistenceItem(String userId, String appId) {
		this.gpsId = SequenceGenerator.generateSequenceId();		
		this.userId = userId;
		this.applicationId = appId;
		Date d = new Date();
		this.lastAccessed=d;
		this.lastModified=d;
		this.creationDate=d;
	}
	public Long getGpsId() {
		return gpsId;
	}
	public void setGpsId(Long gpsId) {
		this.gpsId = gpsId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNameSpace() {
		nameSpace = nameSpace==null?"":nameSpace;
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		nameSpace = nameSpace==null?"":nameSpace;
		this.nameSpace = nameSpace;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	public Date getLastAccessed() {
		return lastAccessed;
	}
	
	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	
	
}
