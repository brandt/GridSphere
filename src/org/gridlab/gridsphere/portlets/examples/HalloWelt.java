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

public class HalloWelt extends AbstractPortlet {

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        System.err.println("init() in HalloWelt");
    }

    public void actionPerformed(ActionEvent evt) {
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<br>Hello, World Zwei</br>");
        out.println("<table><tr><td>1</td></tr><tr><td>2</td></tr></table>");
    }

    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<br>now in edit mode</br>");
    }
}
