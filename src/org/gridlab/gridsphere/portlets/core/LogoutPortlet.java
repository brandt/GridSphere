/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.PortletProperties;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.LoginService;
import org.gridlab.gridsphere.services.security.AuthenticationException;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Iterator;

public class LogoutPortlet extends AbstractPortlet {

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        System.err.println("init() in LogoutPortlet");
    }

    public void actionPerformed(ActionEvent evt) throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        PortletSession session = req.getPortletSession();
        session.invalidate();
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<b>logout</b>");
    }

}
