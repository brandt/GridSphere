/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletSecurityServiceFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRoles;
import org.gridlab.gridsphere.services.AccessControlService;
import org.gridlab.gridsphere.services.impl.AccessControlServiceImpl;

import javax.servlet.ServletConfig;
import java.util.*;
import java.lang.reflect.Constructor;

/**
 * The SportletSecurityServiceFactory provides a singleton factory to create secure portlet services and is
 * implemented using the proxy pattern to represent a SportletServicefactory used internally.
 */
public class SportletSecurityServiceFactory implements PortletSecurityServiceFactory {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(SportletSecurityServiceFactory.class);

    private static SportletSecurityServiceFactory instance;
    private static SportletServiceFactory proxy;
    private static AccessControlService accessControlService;

    /**
     * Private constructor. Use getInstance() instead.
     */
    private SportletSecurityServiceFactory() {
        proxy = SportletServiceFactory.getInstance();

        // Need to have the super access control service, so we create it directly
        accessControlService = (AccessControlService)proxy.createPortletService(AccessControlService, null, null, true);
    }

    /**
     * doSync is used to ensure only one thread has created an instance
     */
    private synchronized static void doSync() {}

    public static SportletSecurityServiceFactory getInstance() {
        if (instance == null) {
            SportletSecurityServiceFactory.doSync();
            instance = new SportletSecurityServiceFactory();
        }
        return instance;
    }

    /**
     * createPortletServiceFactory instantiates the given class and initializes it
     *
     * @param service the class of the service
     * @param serviceProperties the service properties
     * @param servletConfig the servlet configuration
     * @param boolean reuse a previous initialized service if true, otherwise create a new service instance if false
     * @return the instantiated portlet service
     * @throws PortletServiceUnavailableException if the portlet service is unavailable
     * @throws PortletServiceNotFoundException if the PortletService is not found
     */
    public PortletService createSecurePortletService(Class service,
                                               Properties serviceProperties,
                                               ServletConfig servletConfig,
                                               boolean useCachedService,
                                               User user, String groupName)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {
        // Create the appropriate service based on the user's role
        // Order matters-- if the user has both Super and Admin role, they get Super privileges
        Class clazz = getRoleClass(service, user, groupName);
        return proxy.createPortletService(clazz, serviceProperties, servletConfig, useCachedService);
    }

    protected Class getRoleClass(Class service, User user, String groupName) throws ClassNotFoundException {
        Class clazz = null;
        Iterator groups =
        String serviceName = service.getName();
        // parse for last period
        int lastchar = serviceName.lastIndexOf(".");
        if (accessControlService.hasRoleInGroup(user, groupName, PortletRoles.SUPER) {
            clazz = Class.forName("Super" + service.getName());
        } else if (accessControlService.hasRoleInGroup(user, groupName, PortletRoles.ADMIN) {
            clazz = Class.forName("Admin" + service.getName());
        } else if (accessControlService.hasRoleInGroup(user, groupName, PortletRoles.USER) {
            clazz = Class.forName("User" + service.getName());
        } else {

        }
        return clazz;
    }

}
