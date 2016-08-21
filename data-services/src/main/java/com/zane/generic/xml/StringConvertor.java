package com.zane.generic.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class StringConvertor implements Converter{

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		String s = (String)value;		 

		writer.startNode("String");
		writer.setValue("<![CDATA["+s.toString()+"]]>");
		writer.endNode();

	}	 

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {

		return null;
	}

	public boolean canConvert(Class c) {
		return c.equals(String.class);
	}
	
}
