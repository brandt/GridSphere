package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletSessionListener;
import org.gridlab.gridsphere.portlet.PortletSession;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletSession;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletSessionManager implements HttpSessionListener {

    private static PortletSessionManager instance  = new PortletSessionManager();
    private PortletLog log = SportletLog.getInstance(PortletSessionManager.class);

    private Map sessions = new HashMap();

    private PortletSessionManager() {

    }

    public static PortletSessionManager getInstance() {
        return instance;
    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    public void sessionCreated(HttpSessionEvent event) {
        log.debug("sessionCreated('" + event.getSession().getId() + "')");
        synchronized (sessions) {
            sessions.put(event.getSession().getId(), new ArrayList());
        }
        dumpSessions();
    }


    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        //loginService.sessionDestroyed(event.getSession());
        log.debug("sessionDestroyed('" + event.getSession().getId() + "')");
        //HttpSession session = event.getSession();
        //User user = (User) session.getAttribute(GridSphereProperties.USER);
        //System.err.println("user : " + user.getUserID() + " expired!");
        //PortletLayoutEngine engine = PortletLayoutEngine.getDefault();
        //engine.removeUser(user);
        //engine.logoutPortlets(event);
        synchronized (sessions) {
            List sessionListeners = (List)sessions.get(event.getSession().getId());
            Iterator it = sessionListeners.iterator();
            while (it.hasNext()) {
                PortletSessionListener sessionListener = (PortletSessionListener)it.next();
                PortletSession session = new SportletSession(event.getSession());
                try {
                    log.debug("logging a session listener out:");
                    sessionListener.logout(session);
                } catch (PortletException e) {
                    log.error("Unable to invoke logout on session listener ", e);
                }
            }
            sessions.remove(event.getSession().getId());
        }
        dumpSessions();
    }

    public void addSessionListener(String sessionId, PortletSessionListener sessionListener) {
        List sessionListeners = (List)sessions.get(sessionId);
        if (sessionListeners != null) {
            sessionListeners.add(sessionListener);
        }
        sessions.put(sessionId, sessionListeners);
    }

    public void removeSessionListener(String sessionId, PortletSessionListener sessionListener) {
        List sessionListeners = (List)sessions.get(sessionId);
        if (sessionListeners != null) {
            sessionListeners.remove(sessionListener);
        }
        sessions.put(sessionId, sessionListeners);
    }

    public void dumpSessions() {
        log.info("Session information:");
        log.info("# current sessions: " + sessions.size());
        Set keySet = sessions.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            log.info("current sessions #id: " + (String)it.next());
        }
    }
}
