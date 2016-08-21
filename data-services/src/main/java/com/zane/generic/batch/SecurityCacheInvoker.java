package com.zane.generic.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zane.generic.cache.GenericCache;
import com.zane.generic.util.GenericContext;
import com.zane.generic.util.UrlInvoker;

public class SecurityCacheInvoker {
	
	private static final Logger logger = Logger.getLogger(SecurityCacheInvoker.class);
	private Collection<String> securitiesList;
	private String securityFileDir;
	private Collection<String> urls;

	public void preCacheSecurities(){
		long start = System.currentTimeMillis();
		UrlInvoker invoker = new UrlInvoker();
		
		GenericCache cache = 
			GenericContext.getHistoricalDataCache();
		cache.flushCache();
		for (String fileName : securitiesList) {
			File dataFile = new File(securityFileDir+File.separator+fileName);
			String line = null;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(dataFile));
				while ((line = reader.readLine()) != null ) {
					String[] tokens = StringUtils.split(line, "\t");
					for (String url : urls) {
						invoker.getStringFromURL(url.replace("$SECURITY", tokens[0])); 	
					}
					logger.info("Completed:"+line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			logger.info("Completed Precache in "+(System.currentTimeMillis()-start));	
		}
		
	}

	
	/**
	 * @return the url
	 */
	public Collection getUrls() {
		return urls;
	}



	/**
	 * @param url the url to set
	 */
	public void setUrls(Collection url) {
		this.urls = url;
	}


	/**
	 * @return the securitiesList
	 */
	public Collection<String> getSecuritiesList() {
		return securitiesList;
	}


	/**
	 * @param securitiesList the securitiesList to set
	 */
	public void setSecuritiesList(Collection<String> securitiesList) {
		this.securitiesList = securitiesList;
	}


	/**
	 * @return the securityFileDir
	 */
	public String getSecurityFileDir() {
		return securityFileDir;
	}


	/**
	 * @param securityFileDir the securityFileDir to set
	 */
	public void setSecurityFileDir(String securityFileDir) {
		this.securityFileDir = securityFileDir;
	}





}
