/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.AccessDeniedException;

import javax.servlet.ServletConfig;
import java.util.Properties;

/**
 * All interfaces in this Service Provider Interface (SPI) package are needed by all implementations of secure
 * services. The PortletSecurityServiceFactory interface has to be implemented by all portlet security service
 * factory implementations.
 */
public interface PortletSecurityServiceFactory {

    /**
     * createPortletServiceFactory instantiates the given class and initializes it
     *
     * @param service the class of the service
     * @param serviceProperties the service properties
     * @param servletConfig the servlet configuration
     * @param boolean reuse a previous initialized service if true, otherwise create a new service instance if false
     * @param user the user object used to access the service
     * @return the instantiated portlet service
     * @throws PortletUnavailableException if the portlet service is unavailable
     * @throws AccessDeniedException if the user doesn't have sufficient rights to create service
     */
    public PortletService createPortletSecurityService(Class service,
                                               Properties serviceProperties,
                                               ServletConfig servletConfig,
                                               boolean useCachedService,
                                               User user)
                                        throws PortletServiceUnavailableException, AccessDeniedException;



}
