package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;

import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class UserSessionManager implements PortletSessionListener {

    private static PortletSessionManager sessionManager = PortletSessionManager.getInstance();
    private static UserSessionManager instance = new UserSessionManager();
    private PortletLog log = SportletLog.getInstance(UserSessionManager.class);
    private Map userSessions = new Hashtable();

    private UserSessionManager() {
    }

    public static UserSessionManager getInstance() {
        return instance;
    }

    public void destroy() {
        log.debug("Destroying all user sessions");
        Set keys = userSessions.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String uid = (String) it.next();
            List sessions = (List) userSessions.get(uid);
            Iterator sit = sessions.iterator();
            while (sit.hasNext()) {
                PortletSession s = (PortletSession) sit.next();
                try {
                    s.invalidate();
                } catch (IllegalStateException e) {
                    log.debug("Session already invalidated");
                }
            }
            userSessions.remove(uid);
        }
    }

    public List getSessions(User user) {
        return (List) userSessions.get(user.getID());
    }

    public List getSessions(String userid) {
        return (List) userSessions.get(userid);
    }

    public void addSession(User user, PortletSession session) {
        log.debug("Setting session for user " + user.getID());
        List sessions;
        if (userSessions.containsKey(user.getID())) {
            sessions = (List) userSessions.get(user.getID());
        } else {
            sessions = new ArrayList();
        }
        sessions.add(session);
        userSessions.put(user.getID(), sessions);
        sessionManager.addSessionListener(session.getId(), this);
    }

    public String getUserIdFromSession(PortletSession session) {
        Iterator it = userSessions.keySet().iterator();
        while (it.hasNext()) {
            String userId = (String) it.next();
            List portletSessions = (List) userSessions.get(userId);
            Iterator pit = portletSessions.iterator();
            while (pit.hasNext()) {
                PortletSession s = (PortletSession) pit.next();
                if (s.getId().equals(session.getId())) {
                    return userId;
                }
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

    public void login(PortletRequest request) {
        // so far do nothing
    }

    public void logout(PortletSession session) {
        log.debug("in logout");
        Set s = userSessions.keySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            String uid = (String) it.next();
            List sessions = (List) userSessions.get(uid);
            log.debug("checking if user " + uid + " has session" + session.getId());
            if (sessions.contains(session)) {
                log.debug("Logging out user: " + uid + " session: " + session.getId());
                session.removeAttribute(SportletProperties.PORTLET_USER);
                sessions.remove(session);
            }
        }
        //dumpSessions();
    }

    public void removeSessions(User user) {
        log.debug("Removing session for user " + user.getID());
        //List userSessions = (List)userSessions.get(user.getID());
        List s = getSessions(user);
        if (s != null) {
            userSessions.remove(user.getID());
        }
    }

    public void removeSessions(String userid) {
        log.debug("Removing session for user " + userid);
        //List userSessions = (List)userSessions.get(user.getID());
        List s = getSessions(userid);
        if (s != null) {
            userSessions.remove(userid);
        }
    }

    public synchronized void dumpSessions() {
        log.info("User Session Manager information:");
        log.info("# current sessions: " + userSessions.size());
        Set keySet = userSessions.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String u = (String) it.next();
            List l = (List) userSessions.get(u);
            Iterator lit = l.iterator();
            while (lit.hasNext()) {
                PortletSession s = (PortletSession) lit.next();
                log.info("User: " + u + " has session id: " + s.getId());
            }

        }
        sessionManager.dumpSessions();
    }
}
