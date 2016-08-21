package com.zane.generic.xml;

import java.util.Collection;

import com.thoughtworks.xstream.XStream;
import com.zane.generic.obj.PersistenceItem;

public class ResponseBuilder {
	
	private static XStream xStream = null;
	
	 
	static{
		xStream = new XStream(new CDATAPrinterDriver());		
		xStream.setMode(XStream.NO_REFERENCES);  			
	}
	
	 
	public static synchronized String generateResponseXML(Object obj){		
		StringBuilder builder = new StringBuilder();
		if(obj != null){
			if(obj instanceof Collection){
				Collection<PersistenceItem> stores = (Collection<PersistenceItem>)obj;
				for (PersistenceItem persistenceStore : stores) {
					builder.append(xStream.toXML(persistenceStore)+"\n");
				}
			}else if (obj instanceof String){
				builder.append(obj.toString()+"\n");	
			}
				
		}
		return builder.toString();						
	}

}
