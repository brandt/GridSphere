package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletSession;
import org.gridlab.gridsphere.portlet.PortletSessionListener;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletSession;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletSessionManager.java 4985 2006-08-04 09:54:28Z novotny $
 */
public class PortletSessionManager implements HttpSessionListener {

    private static PortletSessionManager instance = new PortletSessionManager();
    private static PortletLog log = SportletLog.getInstance(PortletSessionManager.class);

    private Hashtable sessions = new Hashtable();

    private PortletSessionManager() {

    }

    public static PortletSessionManager getInstance() {
        return instance;
    }

    public int getNumSessions() {
        return sessions.size();        
    }

    public Set getSessions() {
        return sessions.keySet();
    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    public void sessionCreated(HttpSessionEvent event) {
        log.debug("sessionCreated('" + event.getSession().getId() + "')");
        sessions.put(event.getSession(), new ArrayList());
        //dumpSessions();
    }


    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        log.debug("sessionDestroyed('" + event.getSession().getId() + "')");
        HttpSession httpSession = event.getSession();
        if (httpSession != null) {
            List sessionListeners = (List) sessions.get(httpSession);
            if (sessionListeners != null) {
                Iterator it = sessionListeners.iterator();
                while (it.hasNext()) {
                    PortletSessionListener sessionListener = (PortletSessionListener) it.next();
                    PortletSession session = new SportletSession(httpSession);
                    try {
                        log.debug("logging a session listener out: " + sessionListener.getClass());
                        sessionListener.logout(session);
                    } catch (PortletException e) {
                        log.error("Unable to invoke logout on session listener ", e);
                    }
                }
                log.debug("Removing session: " + httpSession.getId());
                sessions.remove(httpSession);
            }
        } else {
            log.info("Not sure why sessionDestroyed listener provides null session id!");
        }
        //dumpSessions();
    }

    public void addSessionListener(String sessionId, PortletSessionListener sessionListener) {
        log.debug("adding session listener for : " + sessionId + " " + sessionListener.getClass());
        Iterator it = sessions.keySet().iterator();
        while (it.hasNext()) {
            HttpSession session = (HttpSession)it.next();
            if (session.getId().equals(sessionId)) {
                System.err.println("adding session listener for : " + sessionId + " " + sessionListener.getClass());
                List sessionListeners = (List) sessions.get(session);
                if (sessionListeners == null) sessionListeners = new ArrayList();
                sessionListeners.add(sessionListener);
                sessions.put(session, sessionListeners);
                break;
            }
        }
        //dumpSessions();
    }

    public synchronized void dumpSessions() {
        log.debug("PortletSessionManager Session information:");
        log.debug("# current sessions: " + sessions.size());
        Set keySet = sessions.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            HttpSession session = (HttpSession)it.next();
            log.debug("session #id: " + session.getId());
        }
    }

}
