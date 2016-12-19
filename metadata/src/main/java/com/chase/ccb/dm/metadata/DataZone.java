package com.chase.ccb.dm.metadata;

public class DataZone extends Settings{
	
	private String name;
	private ZoneConnector connector;
	private ZoneAuthentication authentication;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ZoneConnector getConnector() {
		return connector;
	}
	public void setConnector(ZoneConnector connector) {
		this.connector = connector;
	}
	public ZoneAuthentication getAuthentication() {
		return authentication;
	}
	public void setAuthentication(ZoneAuthentication authentication) {
		this.authentication = authentication;
	}
	
	 

}
