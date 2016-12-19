package com.chase.ccb.dm.metadata;

import java.util.Date;

public abstract class AbstractVersionInformation extends Settings{
	
	
	
	protected String versionHashCode;
	
	public  String getVersionHashCode(){
		return versionHashCode;
	}
	
	protected abstract void setVersionHashCode();

}
