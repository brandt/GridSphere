/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;

import javax.servlet.UnavailableException;
import java.io.IOException;

public class LogoutPortlet extends AbstractPortlet {

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        System.err.println("init() in LogoutPortlet");
    }

    public void actionPerformed(ActionEvent evt) throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        PortletSession session = req.getPortletSession();
        session.invalidate();
        req.getPortletSession(true);
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        getPortletConfig().getContext().include("/jsp/logout.jsp", request, response);
    }

}
