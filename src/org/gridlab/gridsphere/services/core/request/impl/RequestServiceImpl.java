/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.request.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.request.GenericRequest;
import org.gridlab.gridsphere.services.core.request.RequestService;

public class RequestServiceImpl implements RequestService, PortletServiceProvider {

    private PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();
    private static PortletLog log = SportletLog.getInstance(RequestServiceImpl.class);

    public void init(PortletServiceConfig config) {

    }

    public void destroy() {
    }

    public GenericRequest getRequest(String requestId) {
        GenericRequest request = null;
        String query = "select gsreq from "
                + GenericRequest.class.getName()
                + " gsreq where gsreq.oid='" + requestId + "'";
        try {
            request = (GenericRequest) this.pm.restore(query);
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve request: " + requestId, e);
        }
        return request;
    }

    public GenericRequest createRequest() {
        GenericRequest request = new GenericRequest();
        saveRequest(request);
        return request;
    }

    public void saveRequest(GenericRequest request) {
        try {
            if (request.getOid() != null) {
                pm.update(request);
            } else {
                pm.create(request);
            }
        } catch (PersistenceManagerException e) {
            log.error("Unable to create or update password for user", e);
        }
    }

    public void deleteRequest(GenericRequest request) {
        try {
            pm.delete(request);
        } catch (PersistenceManagerException e) {
            log.error("Unable to delete request: " + request.getOid(), e);
        }

    }
}