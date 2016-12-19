package com.chase.ccb.dm.metadata;

import java.util.ArrayList;
import java.util.Collection;

public class DataDefinition extends Settings{
	
	private DataDefinition parentDefinition;
	public DataDefinition getParentDefinition() {
		return parentDefinition;
	}
	public void setParentDefinition(DataDefinition parentDefinition) {
		this.parentDefinition = parentDefinition;
	}
	public DataZone getDataZone() {
		return dataZone;
	}
	public void setDataZone(DataZone dataZone) {
		this.dataZone = dataZone;
	}
	
	public Collection<DataField> getFields() {
		return fields;
	}
	
	public void addField(DataField field){
		fields.add(field);
	}
	public void setFields(Collection<DataField> fields) {
		this.fields = fields;
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
	private DataZone dataZone; 
	private Collection<DataField> fields = new ArrayList<DataField>();
	private String name;
	private String code;

}
