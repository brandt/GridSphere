package org.gridsphere.services.core.filter.impl;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.filter.BasePortalFilter;
import org.gridsphere.services.core.filter.PortalFilter;
import org.gridsphere.services.core.filter.PortalFilterConfig;
import org.gridsphere.services.core.request.Request;
import org.gridsphere.services.core.request.RequestService;
import org.gridsphere.services.core.user.User;
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
public class RememberMeCookieFilter extends BasePortalFilter implements PortalFilter {

    private UserManagerService userManagerService;
    private RequestService requestService;

    private static final String COOKIE_REQUEST = "cookie-request";
    private int COOKIE_EXPIRATION_TIME = 60 * 60 * 24 * 7;  // 1 week (in secs)

    public void init(PortalFilterConfig config) {
        userManagerService = (UserManagerService) PortletServiceFactory.createPortletService(UserManagerService.class, true);
        requestService = (RequestService) PortletServiceFactory.createPortletService(RequestService.class, true);
        try {
            COOKIE_EXPIRATION_TIME = Integer.parseInt(config.getInitParameter("COOKIE_EXPIRATION_TIME"));
        } catch (NumberFormatException e) {
            System.err.println(config.getInitParameter("COOKIE_EXPIRATION_TIME"));
            e.printStackTrace();
        }
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
        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
        if (user != null) return;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        //System.err.println("cookie length=" + cookies.length);
        Cookie c = null;
        for (int i = 0; i < cookies.length; i++) {
            c = cookies[i];
            //System.err.println("found a cookie:");
            //System.err.println("name=" + c.getName());
            //System.err.println("value=" + c.getValue());
            if (c.getName().equals("gridsphere")) {
                String reqId = c.getValue();
                //System.err.println("reqid = " + reqId);
                Request genreq = requestService.getRequest(reqId, COOKIE_REQUEST);
                if (genreq != null) {
                    String remoteAddr = genreq.getAttribute("ipaddress");
                    if ((remoteAddr != null) && (!remoteAddr.equals(((HttpServletRequest) req).getRemoteAddr()))) {
                        //System.err.println("ip address of host and cookie did not match!!");
                        return;
                    }
                    String uid = genreq.getUserID();
                    User newuser = userManagerService.getUser(uid);
                    if (newuser != null) {
                        //System.err.println("in checkUserHasCookie-- seting user settings!!");
                        req.setAttribute(SportletProperties.PORTLET_USER, newuser);
                        req.getSession(true).setAttribute(SportletProperties.PORTLET_USER, newuser.getID());
                    }

                }
            }
        }
    }

    protected void setUserCookie(HttpServletRequest req, HttpServletResponse res) {
        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
        Request request = requestService.createRequest(COOKIE_REQUEST);
        Cookie cookie = new Cookie("gridsphere", request.getOid());
        request.setUserID(user.getID());
        long time = Calendar.getInstance().getTime().getTime() + COOKIE_EXPIRATION_TIME * 1000;
        request.setLifetime(new Date(time));
        String remoteAddr = ((HttpServletRequest) req).getRemoteAddr();
        if (remoteAddr != null) request.setAttribute("ipaddress", remoteAddr);
        requestService.saveRequest(request);

        // COOKIE_EXPIRATION_TIME is specified in secs
        cookie.setMaxAge(COOKIE_EXPIRATION_TIME);
        cookie.setPath("/"); //TODO: should be path of the context, but it is configurable during deployment
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
                    Request request = requestService.getRequest(reqid, COOKIE_REQUEST);
                    if (request != null) requestService.deleteRequest(request);
                    c.setMaxAge(0);
                    res.addCookie(c);
                }
            }
        }
    }
}
