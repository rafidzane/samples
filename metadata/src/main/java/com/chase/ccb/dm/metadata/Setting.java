package com.chase.ccb.dm.metadata;

public class Setting {
	
	private String name;
	public Setting(String name2, String value2) {
			this.name = name2;
			this.value = value2;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String value;


}
