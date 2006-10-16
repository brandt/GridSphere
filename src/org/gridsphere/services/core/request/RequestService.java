/*
 * @version: $Id: RequestService.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.request;

import org.gridsphere.portlet.service.PortletService;

public interface RequestService extends PortletService {

    public Request getRequest(String requestId, String label);

    public Request createRequest(String label);

    public void deleteRequest(Request request);

    public void saveRequest(Request request);
}