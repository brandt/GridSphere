package org.gridsphere.services.core.filter;

import org.gridsphere.portlet.service.PortletService;

import java.util.List;

/**
 * The <code>PortalFilterService</code> provides the portal with the available portal filters.
 */
public interface PortalFilterService extends PortletService {

    public List<PortalFilter> getPortalFilters();

}
