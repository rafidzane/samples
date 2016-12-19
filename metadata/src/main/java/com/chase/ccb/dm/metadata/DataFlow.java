package com.chase.ccb.dm.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class DataFlow extends AbstractVersionInformation{
	
	public DataFlow(){
		setVersionHashCode();		
	}
	private Collection<DataProcessor> processes = new ArrayList<DataProcessor>();
	
	private String name;
	private String code;
	
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	 
	
	
	public Collection<DataProcessor> getProcesses() {
		return processes;
	}
	public void setProcesses(Collection<DataProcessor> processes) {
		this.processes = processes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	@Override
	protected void setVersionHashCode() {
		this.versionHashCode = String.valueOf("DF_"+hashCode());
	} 
	
	
	 
	

}
