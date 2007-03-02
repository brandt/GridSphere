package org.gridsphere.services.core.filter.impl;

import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.filter.PortalFilterService;
import org.gridsphere.services.core.filter.PortalFilter;
import org.gridsphere.services.core.filter.impl.descriptor.PortalFilterDescriptor;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.PortletServiceException;

import java.util.List;

/**
 * The <code>PortalFilterService</code> provides the portal with the available portal filters.
 */
public class PortalFilterServiceImpl implements PortalFilterService, PortletServiceProvider {

    private List<PortalFilter> portalFilters = null;

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws org.gridsphere.portlet.service.PortletServiceUnavailableException
     *          if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        String filterDescriptorPath = config.getServletContext().getRealPath("/WEB-INF/filters.xml");
        try {
            PortalFilterDescriptor filterDescriptor = new PortalFilterDescriptor(filterDescriptorPath);
            portalFilters = filterDescriptor.getPortalFilters();
        } catch (PersistenceManagerException e) {
            //log.error("error unmarshalling " + servicesPath + " using " + servicesMappingPath + " : " + e.getMessage());
            throw new PortletServiceException("error unmarshalling " + filterDescriptorPath, e);
        }
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {
    }

    public List<PortalFilter> getPortalFilters() {
        return portalFilters;
    }

}
