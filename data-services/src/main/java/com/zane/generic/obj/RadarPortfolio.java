package com.zane.generic.obj;

import java.io.Serializable;
import java.util.Date;

import com.zane.generic.dao.SequenceGenerator;

public class RadarPortfolio implements Serializable{

	private Long portfolioId;
	private String portfolioName;
	private String userId;
	private String ticker;
	private boolean visible;
	private boolean shared;
	private Date creationDate;
	private Date lastModified;
	private Date lastAccessed;
	
	public RadarPortfolio(){		
	}
	
	public RadarPortfolio(String userId, String portfolioName) {
		this.portfolioId = SequenceGenerator.generateSequenceId();
		this.portfolioName = portfolioName;
		this.userId = userId;		
		Date d = new Date();
		this.lastAccessed=d;
		this.lastModified=d;
		this.creationDate=d;
	}

	public Long getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
 
	
	
}
