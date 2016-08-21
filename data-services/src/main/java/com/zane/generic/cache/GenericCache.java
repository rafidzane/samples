package com.zane.generic.cache;

import java.util.Properties;
import java.util.Set;


public interface GenericCache {
	
	public Object getFromCache(String key) throws GenericCacheException;	
	public void   putInCache(String key, Object value) throws GenericCacheException;		
	public void   putInCache(String key, Object value, String group) throws GenericCacheException;
	public void   flush(String key);
	public void   flushCache();
	public boolean flushGroup(String group);
	public String getCacheName();
	public int getCacheSize();
	public Set<String> getCacheGroups();
	public Properties getCacheProperties();
	
}

