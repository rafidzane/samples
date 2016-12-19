package com.chase.ccb.dm.metadata;

public class DataField extends Settings{
	
	private String name;
	private String dataType;
	private String position;
	
	private PiInformation pi;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public PiInformation getPi() {
		return pi;
	}

	public void setPi(PiInformation pi) {
		this.pi = pi;
	}

}
