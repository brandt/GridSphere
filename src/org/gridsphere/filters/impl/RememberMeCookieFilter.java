package org.gridsphere.filters.impl;

import org.gridsphere.filters.PortalFilter;
import org.gridsphere.filters.PortalFilterConfig;
import org.gridsphere.portlet.User;
import org.gridsphere.portlet.jsrimpl.SportletProperties;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.request.GenericRequest;
import org.gridsphere.services.core.request.RequestService;
import org.gridsphere.services.core.user.UserManagerService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class RememberMeCookieFilter implements PortalFilter {

    private UserManagerService userManagerService;
    private RequestService requestService;

    private static final String COOKIE_REQUEST = "cookie-request";
    private int COOKIE_EXPIRATION_TIME = 60 * 60 * 24 * 7;  // 1 week (in secs)

    public void init(PortalFilterConfig config) {
        userManagerService = (UserManagerService) PortletServiceFactory.createPortletService(UserManagerService.class, true);
        requestService = (RequestService) PortletServiceFactory.createPortletService(RequestService.class, true);
        System.err.println(config.getInitParameter("COOKIE_EXPIRATION_TIME"));
        //COOKIE_EXPIRATION_TIME = Integer.parseInt(config.getInitParameter("COOKIE_EXPIRATION_TIME"));
    }

    public void doAfterLogin(HttpServletRequest req, HttpServletResponse res) {
        String remme = req.getParameter("remlogin");
        if (remme != null) {
            setUserCookie(req, res);
        } else {
            removeUserCookie(req, res);
        }
    }


    public void doAfterLogout(HttpServletRequest req, HttpServletResponse res) {
        removeUserCookie(req, res);
    }

    public void doBeforeEveryRequest(HttpServletRequest req, HttpServletResponse res) {
        checkUserHasCookie(req);
    }

    public void doAfterEveryRequest(HttpServletRequest req, HttpServletResponse res) {

    }

    protected void checkUserHasCookie(HttpServletRequest req) {
        User user = (User)req.getAttribute(SportletProperties.PORTLET_USER);
        if (user != null) return;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        System.err.println("cookie length=" + cookies.length);
        Cookie c = null;
        for (int i = 0; i < cookies.length; i++) {
            c = cookies[i];
            System.err.println("found a cookie:");
            System.err.println("name=" + c.getName());
            System.err.println("value=" + c.getValue());
            if (c.getName().equals("gridsphere")) {
                String reqId = c.getValue();
                System.err.println("reqid = " + reqId);
                GenericRequest genreq = requestService.getRequest(reqId, COOKIE_REQUEST);
                if (genreq != null) {
                    String remoteAddr = genreq.getAttribute("ipaddress");
                    if ((remoteAddr != null) && (!remoteAddr.equals(((HttpServletRequest)req).getRemoteAddr()))) {
                        System.err.println("ip address of host and cookie did not match!!");
                        return;
                    }
                    String uid = genreq.getUserID();
                    User newuser = userManagerService.getUser(uid);
                    if (newuser != null) {
                        System.err.println("in checkUserHasCookie-- seting user settings!!");
                        req.setAttribute(SportletProperties.PORTLET_USER, user);
                        req.getSession(true).setAttribute(SportletProperties.PORTLET_USER, user.getID());
                    }

                }
            }
        }
    }

    protected void setUserCookie(HttpServletRequest req, HttpServletResponse res) {
        User user = (User)req.getAttribute(SportletProperties.PORTLET_USER);
        GenericRequest request = requestService.createRequest(COOKIE_REQUEST);
        Cookie cookie = new Cookie("gridsphere", request.getOid());
        request.setUserID(user.getID());
        long time = Calendar.getInstance().getTime().getTime() + COOKIE_EXPIRATION_TIME * 1000;
        request.setLifetime(new Date(time));
        String remoteAddr =((HttpServletRequest)req).getRemoteAddr();
        if (remoteAddr != null) request.setAttribute("ipaddress", remoteAddr);
        requestService.saveRequest(request);

        // COOKIE_EXPIRATION_TIME is specified in secs
        cookie.setMaxAge(COOKIE_EXPIRATION_TIME);
        res.addCookie(cookie);
        //System.err.println("adding a  cookie");
    }

    protected void removeUserCookie(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c.getName().equals("gridsphere")) {
                    String reqid = c.getValue();
                    //System.err.println("reqid= " + reqid);
                    GenericRequest request = requestService.getRequest(reqid, COOKIE_REQUEST);
                    if (request != null) requestService.deleteRequest(request);
                    c.setMaxAge(0);
                    res.addCookie(c);
                }
            }
        }
    }
}
