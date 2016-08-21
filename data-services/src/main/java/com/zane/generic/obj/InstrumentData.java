package com.zane.generic.obj;
/**
 * 
 * @author Rafid Zane
 *
 */
public class InstrumentData {
	
	private String instrument;
	private String security;
	private String series;
	
	private String data;
	
	public InstrumentData(String name, String security, String series) {
		this.instrument = name;
		this.security = security;
		this.series = series;
	}
	/**
	 * @return the instrument
	 */
	public String getInstrument() {
		return instrument;
	}
	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	/**
	 * @return the security
	 */
	public String getSecurity() {
		return security;
	}
	/**
	 * @param security the security to set
	 */
	public void setSecurity(String security) {
		this.security = security;
	}
	/**
	 * @return the series
	 */
	public String getSeries() {
		return series;
	}
	/**
	 * @param series the series to set
	 */
	public void setSeries(String series) {
		this.series = series;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	 
	
	public String toString() {
		return security+"_"+ series;
	}
	
	

}
