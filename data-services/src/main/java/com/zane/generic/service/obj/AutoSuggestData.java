package com.zane.generic.service.obj;

/**
 * @author rafidz
 *
 */
public class AutoSuggestData {
	
	private String key;
	private String description;
	private String marketCap;
	
	 
	@Override
	public String toString() {		
		return "Key:"+key+", Description:"+description;
	}
 

	public String getKey() {
		return key;
	}
 

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}
 
	public void setDescription(String description) {
		this.description = description;
	}


	public String getMarketCap() {
		return marketCap;
	}


	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}
	
	
 
	

}
