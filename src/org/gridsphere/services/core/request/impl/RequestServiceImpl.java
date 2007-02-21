/*
 * @version: $Id: RequestServiceImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.request.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.request.Request;
import org.gridsphere.services.core.request.RequestService;

import java.util.*;

public class RequestServiceImpl implements RequestService, PortletServiceProvider {

    private static final long REQUEST_SWEEP_FREQUENCY = 5000 * 60; // 5 minute intervals

    private PersistenceManagerRdbms pm = null;
    private Log log = LogFactory.getLog(RequestServiceImpl.class);
    private Timer timer = null;

    private class RequestSweeperTask extends TimerTask {
        public void run() {
            clearExpiredEntries();
        }
    }

    public void init(PortletServiceConfig config) {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();
        timer = new Timer(true);
        timer.schedule(new RequestSweeperTask(), Calendar.getInstance().getTime(), REQUEST_SWEEP_FREQUENCY);
    }

    public void destroy() {
        timer.cancel();
        timer = null;
    }

    public Request getRequest(String requestId, String label) {
        Request request = null;
        String query = "select gsreq from "
                + GenericRequest.class.getName()
                + " gsreq where gsreq.oid='" + requestId + "' and gsreq.label='" + label + "'";
        try {
            request = (Request) this.pm.restore(query);
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve request: " + requestId, e);
        }
        return request;
    }

    public Request createRequest(String label) {
        Request request = new GenericRequest();
        request.setLabel(label);
        saveRequest(request);
        return request;
    }

    public void saveRequest(Request request) {
        try {
            pm.saveOrUpdate(request);
        } catch (PersistenceManagerException e) {
            log.error("Unable to create or update password for user", e);
        }
    }

    protected void clearExpiredEntries() {
        //log.debug("Updating generic requests");
        Date date = Calendar.getInstance().getTime();
        List<Request> reqs = getAllRequests();
        for (Request req : reqs) {
            if (req.getLifetime() == null) {
                log.debug("deleting request with no lifetime specified " + req.getOid());
                deleteRequest(req);
            } else if (date.compareTo(req.getLifetime()) >= 0) {
                log.debug("deleting request " + req.getOid());
                deleteRequest(req);
            }
        }
    }

    public void deleteRequest(Request request) {
        try {
            pm.delete(request);
        } catch (PersistenceManagerException e) {
            log.error("Unable to delete request: " + request.getOid(), e);
        }

    }

    public List<Request> getAllRequests() {
        String oql = "select gsreq from "
                + GenericRequest.class.getName()
                + " gsreq ";
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            log.error("Error retrieving requests", e);
            return new ArrayList();
        }

    }
}