/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.request;

import org.gridlab.gridsphere.portlet.service.PortletService;

public interface RequestService extends PortletService {

    public GenericRequest getRequest(String requestId, String label);

    public GenericRequest createRequest(String label);

    public void deleteRequest(GenericRequest request);

    public void saveRequest(GenericRequest request);
}