package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class UserSessionManager implements PortletSessionListener {

    private static PortletSessionManager sessionManager = PortletSessionManager.getInstance();
    private static UserSessionManager instance  = new UserSessionManager();
    private PortletLog log = SportletLog.getInstance(UserSessionManager.class);

    private Map userSessions = new Hashtable();

    private UserSessionManager() {
    }

    public static UserSessionManager getInstance() {
        return instance;
    }

    public PortletSession getSession(User user) {
        return (PortletSession)userSessions.get(user.getID());
    }

    public void setSession(User user, PortletSession session) {
        log.error("Setting session for user " + user.getID());
        userSessions.put(user.getID(), session);
        sessionManager.addSessionListener(session.getId(), this);
    }

    public String getUserIdFromSession(PortletSession session) {
        Iterator it = userSessions.keySet().iterator();
        while (it.hasNext()) {
            String u = (String)it.next();
            PortletSession s = (PortletSession)userSessions.get(u);
            if (s.getId().equals(session.getId())) {
                return u;
            }
        }
        return null;
    }

    public List getUserIds() {
        List l = new ArrayList();
        Iterator it = userSessions.keySet().iterator();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return l;
    }

    public List getSessions() {
        List l = new ArrayList();
        Iterator it = userSessions.values().iterator();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return l;
    }

    public void login(PortletRequest request) {
        // so far do nothing
    }

    public void logout(PortletSession session) {
        log.debug("before logout");
        dumpSessions();
        if (userSessions.containsValue(session)) {
            Iterator it = userSessions.keySet().iterator();
            while (it.hasNext()) {
                User u = (User)it.next();
                PortletSession s = (PortletSession)userSessions.get(u);
                if (s.getId().equals(session.getId())) {
                    userSessions.remove(u);
                    break;
                }
            }
        }
        log.debug("after logout");
        dumpSessions();
    }

    public void removeSession(User user) {
        log.error("Removing session for user " + user.getID());
        PortletSession s = getSession(user);
        if (s != null) s.invalidate();
        userSessions.remove(user);
    }

    public void dumpSessions() {
        log.info("Session information:");
        log.info("# current sessions: " + userSessions.size());
        Set keySet = userSessions.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String u = (String)it.next();
            PortletSession s = (PortletSession)userSessions.get(u);
            log.info("User: " + u + " has session id: " + s.getId());

        }
    }
}
