/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBeanFactory;
import org.gridlab.gridsphere.event.ActionEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginPortlet extends AbstractPortlet {

    public static final String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
    public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

    public void actionPerformed(ActionEvent evt) {
        PortletRequest req = evt.getPortletRequest();


        TextBean errorMsg = (TextBean)TagBeanFactory.getTagBean(req, "errorMsg");
        String errorKey = (String)req.getAttribute(LoginPortlet.LOGIN_ERROR_FLAG);
        if ((errorKey != null) && (errorMsg != null)) {
            errorMsg.setKey(errorKey);
            TagBeanFactory.storeTagBean(req, errorMsg);
        }

    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        if (user instanceof GuestUser) {
            getPortletConfig().getContext().include("/jsp/login/login.jsp", request, response);
        } else {
            getPortletConfig().getContext().include("/jsp/login/viewuser.jsp", request, response);
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
