/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portlet.impl.SportletData;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.services.container.registry.PortletUserRegistryService;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;
import org.gridlab.gridsphere.portletcontainer.descriptor.Owner;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Manages user portlet instances characterized by a user's list of concrete portlet ID's and the persistent UserData
 * of each one
 */
public class PortletUserRegistryServiceImpl implements PortletUserRegistryService, PortletServiceProvider {

    private static PortletServiceFactory factory = SportletServiceFactory.getInstance();
    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletUserRegistryServiceImpl.class);

    private static PortletRegistryService registryService = null;
    private static AccessControlService aclService = null;


    //private Map userPortlets = new Hashtable();

    // A Vector of UserPortlets objects
    private List userPortlets = new Vector();


    public class UserPortlets {

        // the user ID
        private String userID = "";

        // the list of UserPortletInfos
        private List portlets = new Vector();

        public class UserPortletInfo {

            private String portletID;
            private SportletData sportletData;

            public SportletData getSportletData() {
                return sportletData;
            }

            public void setPortletData(SportletData sportletData) {
                this.sportletData = sportletData;
            }

            public String getPortletID() {
                return portletID;
            }

            public void setPortletID(String portletID) {
                this.portletID = portletID;
            }
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public void setUserPortletInfo(List portlets) {
            this.portlets = portlets;
        }

        public List getUserPortletInfo() {
            return portlets;
        }

        public void addPortlet(UserPortletInfo userPortletInfo) {
            portlets.add(userPortletInfo);
        }

        public void removePortlet(String portletID) {

        }

        public void removePortlet(UserPortletInfo userPortletInfo) {
            if (portlets.contains(userPortletInfo))
                portlets.remove(userPortletInfo);
        }
    }

    /**
     * The init method is responsible for parsing portlet.xml and creating RegisteredPortlet objects based on the
     * entries. The RegisteredPortlets are managed by the PortletRegistryService.
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("in init()");
        // Need a portlet registry service
        try {
            aclService = (AccessControlService) factory.createPortletService(AccessControlService.class, config.getServletConfig(), true);
            registryService = (PortletRegistryService) factory.createPortletService(PortletRegistryService.class, config.getServletConfig(), true);
        } catch (PortletServiceException e) {
            throw new PortletServiceUnavailableException("Unable to get instance of PortletRegistryService");
        }
    }

    public void destroy() {
        log.info("in destroy()");
    }

    /**
     * Login retrieves the user's PortletData for their list of portlets
     */
    public void login(PortletRequest request) {
        log.info("in login()");

        User user = request.getUser();
        List usersPortlets = new Vector();
        // based on user.getID() get their UserPortlet and then getPortlets
        Iterator it = usersPortlets.iterator();
        while (it.hasNext()) {
            String portletID = (String) it.next();
            AbstractPortlet portlet = registryService.getActivePortlet(portletID);
            portlet.login(request);
        }
    }

    /**
     * Logout serializes user's PortletData of their list of portlets
     */
    public void logout(PortletRequest request) {
        log.info("in logout()");

        // based on user.getID() get their UserPortlet and then getPortlets
        List usersPortlets = new Vector();
        Iterator it = usersPortlets.iterator();
        while (it.hasNext()) {
            String portletID = (String) it.next();
            AbstractPortlet portlet = registryService.getActivePortlet(portletID);
            portlet.logout(request.getPortletSession());
        }
    }

    /**
     * Returns the collection of registered portlets for a user, null if none exists
     *
     * @return the registered portlets
     */
    public List getPortlets(PortletRequest request) {
        String uid = request.getUser().getID();
        return null;
    }

    public void addPortlet(PortletRequest request, String concretePortletID) {
        String uid = request.getUser().getID();

    }

    public void removePortlet(PortletRequest request, String concretePortletID) {
        String uid = request.getUser().getID();
    }

    public PortletSettings getPortletSettings(PortletRequest request, String concretePortletID) {
        User user = request.getUser();
        SportletSettings sportletSettings = null;
        RegisteredPortlet regPortlet = registryService.getRegisteredPortlet(concretePortletID);

        Owner owner = regPortlet.getPortletOwner();

        Portlet.Mode mode = request.getMode();

        if (mode == Portlet.Mode.CONFIGURE) {
            PortletRole ownerRole = owner.getRole();
            PortletGroup ownerGroup = owner.getGroup();
            try {
            /*
            if (aclService.hasSuperRole(user)) {
                return regPortlet.getPortletSettings(true);
            }
            */
            if (aclService.hasRoleInGroup(user, ownerGroup, ownerRole))
                return regPortlet.getPortletSettings(true);
            } catch (PortletServiceException e) {
                log.error("Unable to get access control service", e);
            }
        }
        return regPortlet.getPortletSettings(false);
    }

    public PortletData getPortletData(PortletRequest request, String concretePortletID) {
        User user = request.getUser();

        // get SportletData from userPortlets
        SportletData data = (SportletData)userPortlets.get(0);
        Portlet.Mode mode = request.getMode();

        if (mode == Portlet.Mode.EDIT) {
            data.enableConfigurePermission(true);
        }
        return data;
    }


}
