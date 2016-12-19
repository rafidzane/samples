package com.chase.ccb.dm.metadata;

import java.io.File;
import java.io.Writer;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class DataFlowStream {
	
	private static XStream xstream = null;
	
	static {
		xstream = new XStream(new JsonHierarchicalStreamDriver() {
		    public HierarchicalStreamWriter createWriter(Writer writer) {
		        return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
		    }
		});
	     xstream.setMode(XStream.NO_REFERENCES);
	     
	}
	
	public static void main(String[] args) throws Exception{
		DataFlow flow = new DataFlow();
		flow.setName("VLS DataMovement");
		flow.setCode("VLS");
		flow.setEffectiveStartDate(new Date());
		
		DataProcessor p = new DataProcessor();
		p.setProcessorName("VLS Move A to B");
		
		DataDefinition sourceDefinition = new DataDefinition();
		sourceDefinition.setCode("MSP156.LOAN");
		sourceDefinition.setName("MSP Loan");
		for (int i = 0; i < 3; i++) {
			
			DataField f1 = new DataField();
			f1.setDataType("STRING");
			f1.setName("F"+i);
			f1.setPosition(String.valueOf(i));
			
			PiInformation pi = new PiInformation();
			pi.setPiCode("SSN");
			pi.setPiDefinition("Social Security Number");
			f1.setPi(pi);
			f1.setPosition(String.valueOf(i));
			
			sourceDefinition.addField(f1);
			
		}
		
		DataZone zone = new DataZone();
		zone.setName("ICDW Oracle Dev");
		
		ZoneAuthentication auth = new ZoneAuthentication();
		auth.setScheme("EPV");
		zone.setAuthentication(auth);

		ZoneConnector connector = new ZoneConnector();
		connector.setName("Oracle Connector");
		connector.setVendor("Oracle 11g");
		zone.setConnector(connector);
		
		
		sourceDefinition.setDataZone(zone);
		p.getSources().add(sourceDefinition);
		
		DataDefinition desDef = new DataDefinition();
		desDef.setCode("MSP156.LOAN");
		desDef.setName("MSP Loan");
		for (int i = 0; i < 3; i++) {
			
			DataField f1 = new DataField();
			f1.setDataType("STRING");
			f1.setName("F"+i);
			f1.setPosition(String.valueOf(i));
			
			PiInformation pi = new PiInformation();
			pi.setPiCode("SSN");
			pi.setPiDefinition("Social Security Number");
			f1.setPi(pi);
			f1.setPosition(String.valueOf(i));
			
			desDef.addField(f1);
			
		}
		
		
		zone = new DataZone();
		zone.setName("Archive Zone");
		
		auth = new ZoneAuthentication();
		auth.setScheme("None");
		zone.setAuthentication(auth);

		connector = new ZoneConnector();
		connector.setName("HortonWorks");
		connector.setVendor("Hortonworks HDP");
		zone.setConnector(connector);
		
		p.getDestinations().add(desDef);
		flow.getProcesses().add(p);
		FileUtils.writeStringToFile(new File("sample.json"), xstream.toXML(flow));
		
		
		
	}

}
