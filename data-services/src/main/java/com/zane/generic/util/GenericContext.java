package com.zane.generic.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zane.generic.cache.GenericCache;
import com.zane.generic.handler.HandlerFactory;
import com.zane.generic.service.GenericAutoSuggestService;

 
public class GenericContext {
	
	protected static ApplicationContext appContext;	
	
	public void loadSpringXML(String name){		
		try {
			appContext = new ClassPathXmlApplicationContext(name);	
		} catch (Exception e) {
			System.out.println("Cannot Find Spring.xml from ClassPath ... Trying File System ....");
			appContext = new CustomClassPathContext(name);	
			
		}	
		String[] names = appContext.getBeanDefinitionNames();
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
		}
	} 
	
	public static Object getBean(String beanName){
		return appContext.getBean(beanName);
	}
	 
	public String[] getAllBeanNames(){
		return appContext.getBeanDefinitionNames();
	}

	 

	public static HandlerFactory getHandlerFactory() {
		return (HandlerFactory) appContext.getBean("dataEngineHandlerFactory");
	}
	
	 
	public static GenericCache getHistoricalDataCache(){
		return (GenericCache) appContext.getBean("historicalDataCache");
	}

	public static GenericAutoSuggestService getAutoSuggestService() {
		return (GenericAutoSuggestService) appContext.getBean("autoSuggestService");  
	}
	

}
