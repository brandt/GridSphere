/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets;

import org.gridlab.gridsphere.portlet.*;

import javax.servlet.UnavailableException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;

public class HalloWelt extends AbstractPortlet {

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        System.err.println("init() in HalloWelt");
    }

    public void execute(PortletRequest request) throws PortletException {
        // do nothing
    }

    public void service(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        getPortletConfig().getContext().include("/jsp/hello2.jsp", request, response);
    }

}