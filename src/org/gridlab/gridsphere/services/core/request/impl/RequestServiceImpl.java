/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.request.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.request.GenericRequest;
import org.gridlab.gridsphere.services.core.request.RequestService;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.*;

public class RequestServiceImpl extends HibernateDaoSupport implements RequestService {

    private static final long REQUEST_SWEEP_FREQUENCY =  1000 * 60; // 1 minute intervals

    //private PersistenceManagerRdbms pm = null;
    private static PortletLog log = SportletLog.getInstance(RequestServiceImpl.class);
    private Timer timer = null;

    private class RequestSweeperTask extends TimerTask {

        public void run() {
            clearExpiredEntries();
        }
    }

    public RequestServiceImpl() {

    }

    public void init() {
        timer = new Timer(true);
        timer.schedule(new RequestSweeperTask(),  Calendar.getInstance().getTime(), REQUEST_SWEEP_FREQUENCY );    
    }

    public void destroy() {
        timer.cancel();
        timer = null;
    }

    public GenericRequest getRequest(String requestId, String label) {
        String query = "select gsreq from "
                + GenericRequest.class.getName()
                + " gsreq where gsreq.oid='" + requestId + "' and gsreq.label='"+ label + "'";
        List requests = this.getHibernateTemplate().find(query);
        if ((requests != null) && (!requests.isEmpty())) {
            return (GenericRequest)requests.get(0);
        }
        return null;
    }

    public GenericRequest createRequest(String label) {
        GenericRequest request = new GenericRequest();
        request.setLabel(label);
        saveRequest(request);
        return request;
    }

    public void saveRequest(GenericRequest request) {
        this.getHibernateTemplate().saveOrUpdate(request);
    }

    protected void clearExpiredEntries() {
        //log.debug("Updating generic requests");
        Date date = Calendar.getInstance().getTime();
        List reqs = getAllRequests();
        Iterator it = reqs.iterator();
        while (it.hasNext()) {
            GenericRequest req = (GenericRequest)it.next();
            if (req.getLifetime() == null) {
                log.debug("deleting request with no lifetime specified " + req.getOid());
                deleteRequest(req);
            } else if (date.compareTo(req.getLifetime()) >= 0) {
                System.err.println("my date=" +  date);
                System.err.println("req lifetime=" +  req.getLifetime());
                log.debug("deleting request " + req.getOid());
                deleteRequest(req);
            }
        }
    }

    public void deleteRequest(GenericRequest request) {
        this.getHibernateTemplate().delete(request);
    }

    public List getAllRequests() {
        String oql = "select gsreq from "
                + GenericRequest.class.getName()
                + " gsreq ";
        return this.getHibernateTemplate().find(oql);
    }
}