/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.login;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBeanFactory;
import org.gridlab.gridsphere.portlets.core.GPDKPortlet;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.services.core.user.LoginService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class LoginPortlet extends GPDKPortlet {

    public static final String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
    public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }


    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        request.setAttribute("user", user);
        super.doView(request, response);
    }

    public void doConfigure(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        LoginService loginService = (LoginService)getPortletConfig().getContext().getService(LoginService.class, request.getUser());

        List supportedModules = loginService.getSupportedAuthModules();
        List activeModules = loginService.getActiveAuthModules();

        request.setAttribute("activeModules", activeModules);
        request.setAttribute("supportedModules", supportedModules);

        super.doConfigure(request, response);
    }

    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        PrintWriter out = response.getWriter();

        if (user instanceof GuestUser) {

            out.println(getPortletSettings().getTitle(request.getLocale(), null));
        } else {
            getPortletConfig().getContext().include("/jsp/login/login_title.jsp", request, response);
        }
        /*
         ResourceBundle resBundle = ResourceBundle.getBundle("Portlet", locale);
         String welcome = resBundle.getString("LOGIN_SUCCESS");
         out.println(welcome + ", " + user.getFullName());
         */
    }
}
