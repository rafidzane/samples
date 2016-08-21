package com.zane.generic.handler.output;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zane.generic.service.obj.AutoSuggestResultSet;
import com.zane.generic.util.DataServiceKeys;

 
public class AutoSuggestJSONOutputter implements DataEngineOutput {

	
	private static final String DELIMITER = "|";

	public Object generateOutput(Map<String, Object>  input) {
		StringBuilder builder = new StringBuilder();
		
		AutoSuggestResultSet rs = (AutoSuggestResultSet)input.get(DataServiceKeys.DATA_RESULT);
		
		builder.append("{\"ResultSet\":{\"Result\":[");
		if(rs != null){
			Collection<String> matches = rs.getMatches();
			String[] legend = rs.getLegend();
			int counter = 0;				
			for (String strValue : matches) {
				String[] values = StringUtils.split(strValue,DELIMITER);
				builder.append("{");
				for (int i = 0; i < legend.length; i++) {
					if(i != legend.length-1){
						builder.append("\""+legend[i]+"\": \""+values[i]+"\", ");	
					}else{
						builder.append("\""+legend[i]+"\": \""+values[i]+"\"");
					}					
				}					
				if(++counter != matches.size()){
					builder.append("},\n");	
				}else{
					builder.append("}\n");
				}			
			}	
		}				
		builder.append("]}}");
		return builder.toString();	
	}
	
	/*
	private void buildHTMLMenu(Menu menu, StringBuilder builder, int level) {
		Collection<Menu> children = menu.getChildren();
		builder.append("<a href='javascript:viewMenu("+level+","+menu.getMenuId()+");' >"+menu.getDisplayName()+"</a><br><div id=\""+menu.getMenuId()+"\"></div>");
		for (int i = 0; i < level; i++) {
			builder.append("&nbsp;&nbsp;");	
		}
		if(children != null){
			for (Menu child : children) {			
				buildHTMLMenu(child, builder,++level);					
			}	
		}				
	}
	*/

	 
	
	

}
