/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlet.service.spi;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;

/**
 * The <code>PortletServiceProvider</code> interface must be implemented by
 * all portlet service implementations, so that it can be instantiated and
 * destroyed by the appropriate service factory.
 */
public interface PortletServiceProvider extends PortletService {

    /**
     * Initializes the portlet service.
     * <p/>
     * The init method is invoked by the portlet container immediately after a
     * portlet service has been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException
     *          if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException;

    /**
     * Destroys the portlet service.
     * <p/>
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy();

}
