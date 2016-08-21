package com.zane.generic.service.obj;

import java.util.Collection;
import java.util.LinkedList;



public class AutoSuggestResultSet {
	
	
	public AutoSuggestResultSet(String delimiter){
		matches = new LinkedList<String>(); 
		//TreeSet<String>(new SecurityComparator(delimiter));	
	}
	
	
	public Collection<String> matches= null;
	
	
	
	public int getMatchesSize() {
		return matches.size();
	}

	public String[] legend = new String[] {"ticker", "name"};
	
	public String[] getLegend() {
		return legend;
	}

	public void setLegend(String[] legend) {
		this.legend = legend;
	}

	public void addMatch(String match){
		if(!matches.contains(match)){
			matches.add(match);	
		}		
	}
	
	public Collection<String> getMatches() {
		return matches;
	}

	 
	
	

}
