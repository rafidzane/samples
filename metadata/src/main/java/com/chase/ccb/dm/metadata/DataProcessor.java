package com.chase.ccb.dm.metadata;

import java.util.ArrayList;
import java.util.Collection;

public class DataProcessor extends Settings{
	
	private Collection<DataDefinition> sources = new ArrayList<DataDefinition>();
	private Collection<DataDefinition> destinations = new ArrayList<DataDefinition>();
	
	private String processorName;

	public Collection<DataDefinition> getSources() {
		return sources;
	}

	public void setSources(Collection<DataDefinition> sources) {
		this.sources = sources;
	}

	public Collection<DataDefinition> getDestinations() {
		return destinations;
	}

	public void setDestinations(Collection<DataDefinition> destinations) {
		this.destinations = destinations;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
 	
	
	

}
