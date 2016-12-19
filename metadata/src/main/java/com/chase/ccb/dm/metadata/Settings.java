package com.chase.ccb.dm.metadata;

import java.util.Collection;

public class Settings{
	
	private Collection<Setting> settings;
	
	public void  addSetting(String name, String value){
		settings.add(new Setting(name, value));
		
	}
	public Collection<Setting> getSettings() {
		return settings;
	}
	public void setSettings(Collection<Setting> settings) {
		this.settings = settings;
	}
	
	
	

}
