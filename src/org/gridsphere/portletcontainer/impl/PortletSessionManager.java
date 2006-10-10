package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portletcontainer.PortletSessionListener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletSessionManager.java 4985 2006-08-04 09:54:28Z novotny $
 */
public class PortletSessionManager implements HttpSessionListener {

    private static PortletSessionManager instance = new PortletSessionManager();
    private Log log = LogFactory.getLog(PortletSessionManager.class);

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

                        log.debug("logging a session listener out: " + sessionListener.getClass());
                        sessionListener.logout(httpSession);
                    
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

    public void dumpSessions() {
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
