/*
 * @version: $Id$
 */
package org.gridsphere.services.core.request;

import org.gridsphere.portlet.service.PortletService;

public interface RequestService extends PortletService {

    public Request getRequest(String requestId, String label);

    public Request createRequest(String label);

    public void deleteRequest(Request request);

    public void saveRequest(Request request);
}