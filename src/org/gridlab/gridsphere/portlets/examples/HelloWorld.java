/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.examples;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorld extends AbstractPortlet {

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
    }

    public void actionPerformed(ActionEvent evt) {
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        PrintWriter out = response.getWriter();
        String value = portletSettings.getApplicationSettings().getAttribute("foobar");
        out.println(value);

        getPortletConfig().getContext().include("/jsp/hello.jsp", request, response);
    }

}
