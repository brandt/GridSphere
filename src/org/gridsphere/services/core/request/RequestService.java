/*
 * @version: $Id: RequestService.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.request;

import org.gridsphere.portlet.service.PortletService;

public interface RequestService extends PortletService {

    public GenericRequest getRequest(String requestId, String label);

    public GenericRequest createRequest(String label);

    public void deleteRequest(GenericRequest request);

    public void saveRequest(GenericRequest request);
}