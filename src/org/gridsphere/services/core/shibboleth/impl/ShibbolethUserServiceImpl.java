package org.gridsphere.services.core.shibboleth.impl;

import java.util.ArrayList;
import java.util.List;

import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.persistence.QueryFilter;
import org.gridsphere.services.core.shibboleth.ShibbolethUser;
import org.gridsphere.services.core.shibboleth.ShibbolethUserService;

public class ShibbolethUserServiceImpl implements ShibbolethUserService, PortletServiceProvider {

	private PersistenceManagerRdbms pm = null;
	private String jdoShibUser = ShibbolethUser.class.getName();
		
	public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
		PersistenceManagerService pms = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
		pm = pms.createGridSphereRdbms();
	}
	
	public void destroy() {
	}

	public void saveShibUser(ShibbolethUser shibUser) {
		pm.saveOrUpdate(shibUser);
	}
	
	public void deleteShibUser(ShibbolethUser shibUser) {
		if( shibUser != null)
			pm.delete(shibUser);
	}

	public ShibbolethUser getShibUser(String id) {
		return selectShibbolethUser("where uzer.ID='" + id + "'");
	}

	public List<ShibbolethUser> getShibUser() {
		return selectUsers("", null);
	}
	
	public ShibbolethUser getShibUserByUserName(String loginname) {
		return selectShibbolethUser("where uzer.ShibUsername='" + loginname + "'");
	}

	public int getNumShibUsers() {
		String oql = "select count(*) from " + this.jdoShibUser;
		return pm.count(oql);
	}
	
	private List<ShibbolethUser> selectUsers(String criteria, QueryFilter queryFilter) {
		String oql = "select uzer from " + this.jdoShibUser + " uzer " + criteria;
		List<ShibbolethUser> userList = pm.restoreList(oql, queryFilter);
		if (userList == null)
			userList = new ArrayList<ShibbolethUser>();
		return userList;
	}
	
	private ShibbolethUser selectShibbolethUser(String criteria) {
		String oql = "select uzer from " + jdoShibUser + " uzer " + criteria;
		return (ShibbolethUser) pm.restore(oql);
	}
}
