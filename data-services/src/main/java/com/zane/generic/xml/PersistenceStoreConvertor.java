package com.zane.generic.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.zane.generic.obj.PersistenceItem;
import com.zane.generic.util.DateUtil;

public class PersistenceStoreConvertor implements Converter{

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		PersistenceItem s = (PersistenceItem)value;		
		writer.addAttribute("id", s.getGpsId().toString());

		writer.startNode("CreationDate");
		writer.setValue(DateUtil.covertToMMDDYYYYHHMMSS(s.getCreationDate()));
		writer.endNode();

		writer.startNode("LastModified");
		writer.setValue(DateUtil.covertToMMDDYYYYHHMMSS(s.getLastModified()));
		writer.endNode();

		writer.startNode("LastAccessed");
		writer.setValue(DateUtil.covertToMMDDYYYYHHMMSS(s.getLastAccessed()));
		writer.endNode();

		writer.startNode("ApplicationId");
		writer.setValue(s.getApplicationId());
		writer.endNode();
		
		
		writer.startNode("NameSpace");
		writer.setValue(s.getNameSpace());
		writer.endNode();
		
		
		writer.startNode("Key");
		writer.setValue(s.getKey());
		writer.endNode();

		writer.startNode("Value");
		writer.setValue("<![CDATA["+s.getValue()+"]]>");
		writer.endNode();


	}	 

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {

		return null;
	}

	public boolean canConvert(Class c) {
		return c.equals(PersistenceItem.class);
	}
	
}
