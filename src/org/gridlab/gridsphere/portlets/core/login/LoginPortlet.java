/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.login;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.services.core.security.auth.LoginAuthModule;
import org.gridlab.gridsphere.services.core.user.LoginService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginPortlet extends ActionPortlet {

    public static final String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
    public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        DEFAULT_VIEW_PAGE = "doViewUser";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewUser(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: doViewUser");
        PortletRequest request = event.getPortletRequest();
        User user = request.getUser();
        request.setAttribute("user", user);
        setNextState(request, "login/login.jsp");
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

    public void gs_login(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: gs_login");
        PortletRequest req = event.getPortletRequest();

        String errorKey = (String)req.getAttribute(LoginPortlet.LOGIN_ERROR_FLAG);

        if (errorKey != null) {
            FrameBean frame = event.getFrameBean("errorFrame");
            frame.setKey(LoginPortlet.LOGIN_ERROR_FLAG);
            frame.setStyle("error");
        }
        setNextState(req, "doViewUser");
    }

}
