/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.GuestUser;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.core.security.AuthenticationException;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Iterator;

public class LoginPortlet extends AbstractPortlet {

    public static final String LOGIN_ERROR_FLAG = "LOGIN_ERROR";
    public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        if (user instanceof GuestUser) {
            log.info("YO WE IN GUEST USER");
            getPortletConfig().getContext().include("/jsp/login.jsp", request, response);
        } else {
            log.info("YO WE IN VIEW USER!");
            getPortletConfig().getContext().include("/jsp/viewuser.jsp", request, response);
        }
    }

    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("in edit mode");
    }

    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        PrintWriter out = response.getWriter();
        if (user instanceof GuestUser) {
            out.println("Login");
        } else {
            out.println("Welcome, " + user.getFullName());
        }
    }

}
