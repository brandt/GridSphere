package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;

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

    public List getSessions(User user) {
        return (List)userSessions.get(user.getID());
    }

    public void addSession(User user, PortletSession session) {
        log.debug("Setting session for user " + user.getID());
        List sessions = null;
        if (userSessions.containsKey(user.getID())) {
            sessions = (List)userSessions.get(user.getID());
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
            String userId = (String)it.next();
            List portletSessions = (List)userSessions.get(userId);
            Iterator pit = portletSessions.iterator();
            while(pit.hasNext()) {
                PortletSession s = (PortletSession)pit.next();
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

    /*
    public List getSessions() {
        List l = new ArrayList();
        Iterator it = userSessions.values().iterator();
        while (it.hasNext()) {
            l.add(it.next());
        }
        return l;
    }
    */

    public void login(PortletRequest request) {
        // so far do nothing
    }

    public void logout(PortletSession session) {
        log.debug("in logout");
        Set s = userSessions.keySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            String uid = (String)it.next();
            List sessions = (List)userSessions.get(uid);
            log.debug("checking if user " + uid + " has session" + session.getId());
            if (sessions.contains(session)) {
                log.debug("Logging out user: " +  uid + " session: " + session.getId());
                sessions.remove(session);
            }
        }
        dumpSessions();
    }

    public void removeSessions(User user) {
        log.debug("Removing session for user " + user.getID());
        //List userSessions = (List)userSessions.get(user.getID());
        List s = getSessions(user);
        if (s != null) {
            Iterator it = s.iterator();
            while (it.hasNext()) {
                PortletSession session = (PortletSession)it.next();
                if (session != null) {
                    try {
                        log.debug("invalidating session " + session.getId());
                        session.invalidate();
                    } catch (IllegalStateException e) {
                        log.debug("session " + session.getId() + " for user " + user.getID() + " has already been invalidated!");
                    }
                }
            }
            userSessions.remove(user.getID());
        }

        /*
        if (user != null) {
            if (user.getID() != null) {
                log.debug("removing " + user.getID() + " from user session manager");
                userSessions.remove(user.getID());
            }
        }
        */
    }

    public void dumpSessions() {
        log.info("User Session Manager information:");
        log.info("# current sessions: " + userSessions.size());
        Set keySet = userSessions.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            String u = (String)it.next();
            List l = (List)userSessions.get(u);
            Iterator lit = l.iterator();
            while (lit.hasNext()) {
                PortletSession s = (PortletSession)lit.next();
            log.info("User: " + u + " has session id: " + s.getId());
            }

        }

        sessionManager.dumpSessions();
    }
}
