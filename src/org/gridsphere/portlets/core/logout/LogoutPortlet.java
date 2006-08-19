/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: LogoutPortlet.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlets.core.logout;

import org.gridsphere.portlet.*;
import org.gridsphere.portlet.impl.AbstractPortlet;

import java.io.IOException;
import java.util.Locale;


public class LogoutPortlet extends AbstractPortlet {

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        Client client = request.getClient();
        String title;
        Locale locale = request.getLocale();
        title = getPortletSettings().getTitle(locale, client);
        request.setAttribute("GRIDSPHERE_LOGOUT_LABEL", title);
        request.setAttribute("username", request.getUser().getFullName());
        getPortletConfig().getContext().include("/jsp/logout/logout.jsp", request, response);
    }

}
