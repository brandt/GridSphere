/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;

/**
 * All interfaces in this Service Provider Interface (SPI) package are needed by all implementations of services.
 * The PortletServiceProvider interface has to be implemented by all portlet service implementations,
 * so that it can be instantiated and destroyed by the appropriate service factory.
 */
public interface PortletServiceProvider extends PortletService {

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException;

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy();

}
