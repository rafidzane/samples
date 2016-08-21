package com.zane.generic.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zane.generic.obj.PersistenceItem;
import com.zane.generic.obj.RadarPortfolio;


public class GenericStoreDAO extends HibernateDaoSupport {

	public PersistenceItem findGPSById(String userId, String gpsId) {
		try {
			List dataList = getHibernateTemplate().find("from PersistenceStore s where s.userId=? and s.gpsId = ?",
					new Object[]{userId,gpsId});
			if(dataList != null && dataList.size()> 0){
				return (PersistenceItem) dataList.get(0);
			}
		} catch (DataAccessException e) {
			throw e;
		}
		return null;
	}


	public PersistenceItem saveUpdate(PersistenceItem store){		
		try {
			getHibernateTemplate().saveOrUpdate(store);
		} catch (DataAccessException e) {
			throw e;
		}
		return store;
	}
	
	public Collection<RadarPortfolio> saveUpdatePortfolios(Collection<RadarPortfolio> portfolios){		
		try {
			getHibernateTemplate().saveOrUpdateAll(portfolios);
		} catch (DataAccessException e) {
			throw e;
		}
		return portfolios;
	}


	public PersistenceItem delete(PersistenceItem store){
		getHibernateTemplate().delete(store);
		return store;
	}


	public PersistenceItem findGPSByKey(String userId, String appId, String key, String namespace) {
		namespace = namespace == null?"":namespace;
		List dataList = getHibernateTemplate().find("from PersistenceItem s " +
				" where s.userId=? and s.key = ? and s.applicationId = ? and s.nameSpace=?",
				new Object[]{userId,key,appId,namespace});
		if(dataList != null && dataList.size()> 0){
			return (PersistenceItem) dataList.get(0);
		}
		return null;
	}
	
	public Collection<PersistenceItem> findGPSCollectionByKey(String userId, String appId, String nameSpace) {
		List dataList = getHibernateTemplate().find("from PersistenceItem s " +
				" where s.userId=? and s.applicationId= ? and s.nameSpace like '%"+nameSpace+"%' ",
				new Object[]{userId,appId});
		return dataList;
	}


	public Collection<RadarPortfolio> findPortolioCollectionByKey(String userId,
			String key) {
		List dataList = getHibernateTemplate().find("from RadarPortfolio s " +
				" where s.userId=? and s.portfolioName = ?",
				new Object[]{userId,key});
		return dataList;
	}


	public Collection<RadarPortfolio> deletePortFolioObjects(Collection<RadarPortfolio> portfolios) {
		getHibernateTemplate().deleteAll(portfolios);
		return portfolios;
		
	}



}
