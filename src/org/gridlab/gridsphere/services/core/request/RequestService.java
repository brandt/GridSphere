/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.request;

import org.gridlab.gridsphere.portlet.service.PortletService;

public interface RequestService extends PortletService {

    public GenericRequest getRequest(String requestId);

    public GenericRequest createRequest();

    public void deleteRequest(GenericRequest request);

    public void saveRequest(GenericRequest request);
}