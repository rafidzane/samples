package com.zane.generic.cache;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class GenericOSCache implements GenericCache{
	
	private static final Logger logger = Logger.getLogger(GenericOSCache.class);
	
	private Properties cacheProperties;
	private String cacheName;	
	private GeneralCacheAdministrator cache;	
	private Set<String> cacheGroups = new TreeSet<String>();
	
	public GenericOSCache(String name, Properties props, boolean flushOnStart){		
		setupCache(name, props, flushOnStart);
	}
	
	public GenericOSCache(String name, Properties props){		
		setupCache(name, props, false);
	}
	private void setupCache(String name, Properties props, boolean flushOnStart) {
		this.cacheName = name;
		this.cacheProperties = props;
		String path = (String)props.getProperty(CacheKeys.CACHE_PATH);
		path = path+"/"+name;
		if(flushOnStart){
			try {
				logger.info("Flushing Directory:"+path);
				FileUtils.deleteDirectory(new File(path));
			} catch (IOException e) {
				logger.error(e);
			}	
		}		
		props.setProperty(CacheKeys.CACHE_PATH, path);
		cache = new GeneralCacheAdministrator(props);
		cache.putInCache("TestFile","Test...");
		logger.info("Initialized OSCache at: "+path);
	}
	
	
	
	public String getCacheName() {
		return cacheName;
	} 
 
	public Properties getCacheProperties() {
		return cacheProperties;
	}
 
	public void flushCache() {
		cache.flushAll();
	}

	public void flush(String key) {	
		cache.flushEntry(key);		
	}
	
	public int getCacheSize() {	
		return cache.getCache().getSize();
	}

	public synchronized Object getFromCache(String key)throws GenericCacheException {	
		Object value = null;
		try {						
			value = cache.getFromCache(key);
			logger.debug("Getting From CacheName: "+key);
		} catch (NeedsRefreshException e) {			
			logger.debug("Cache Object Not Found for:"+key);
			cache.cancelUpdate(key);
		} catch(Exception e){
			throw new GenericCacheException(e.getMessage(),e);
		}
		return value;
	}

	public  synchronized void putInCache(String key, Object value)throws GenericCacheException {
		logger.debug("Putting In CacheName: "+key);
		cache.putInCache(key,value);		
	}
	
	public  synchronized void putInCache(String key, Object value, String group)throws GenericCacheException {
		logger.debug("Putting In CacheName/Group: "+key+","+group);
		cache.putInCache(key,value, new String[]{group});
		cacheGroups.add(group);
	}

	public Set<String> getCacheGroups() {
		return cacheGroups;
	}

	public void setCacheGroups(Set<String> cacheGroups) {
		this.cacheGroups = cacheGroups;
	}

	public boolean flushGroup(String group) {
		cache.flushGroup(group);
		return true;
	}
 

}
