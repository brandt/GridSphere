/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletWindow;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.PortletErrorMessage;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;

public class PortletFrame extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFrame.class);

    private String portletClass;
    private String windowState = SportletWindow.NORMAL.toString();
    private String portletMode = null;

    public PortletFrame() {
    }

    public void setConcretePortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getConcretePortletClass() {
        return portletClass;
    }

    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    public String getWindowState() {
        return windowState;
    }

    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    public String getPortletMode() {
        return portletMode;
    }

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderFirst(ctx, req, res);
        log.debug("in doRenderFirst()");

        // Set the portlet ID
        req.setAttribute(GridSphereProperties.PORTLETID, portletClass);

        PortletRegistryManager registryManager = PortletRegistryManager.getInstance();

        if (border == null) border = new PortletBorder();
        border.setPortletClass(portletClass);

        UserPortletManager userPortletManager = UserPortletManager.getInstance();

        // Set the portlet window
        PortletWindow window = SportletWindow.getInstance(windowState);
        req.setAttribute(GridSphereProperties.PORTLETWINDOW, window);

        // Set the portlet mode
        //String prevMode = req.getParameter(GridSphereProperties.PORTLETMODE);
        //if (prevMode == null) prevMode = Portlet.Mode.VIEW.toString();
        //req.setAttribute(GridSphereProperties.PREVIOUSMODE, prevMode);

        if (portletMode == null) {
            portletMode = Portlet.Mode.VIEW.toString();
        }
        Portlet.Mode mode = Portlet.Mode.getInstance(portletMode);
        req.setAttribute(GridSphereProperties.PORTLETMODE, mode);

        // Get portlet information for this portletClass


        ///// begin portlet frame
        PrintWriter out = res.getWriter();
        out.println("<table width=\"" + width + "%\"  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\"><tr><td>");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#999999\">");
        out.println("<tr><td width=\"100%\">");

        border.doRenderFirst(ctx, req, res);
        border.doRenderLast(ctx, req, res);

        out.println("</td></tr>");
        out.println("<tr><td valign=\"top\" align=\"left\"><table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" bgcolor=");
        out.println("\"" + bgColor + "\"<tr><td width=\"25%\" valign=\"center\">");

        PortletErrorMessage error = (PortletErrorMessage)req.getAttribute(GridSphereProperties.PORTLETERROR);
        if ((error != null) && (error.getPortletID() == portletClass)) {
            out.println("<b>Error!</b>");
            out.println(error.getMessage());
        } else {
            userPortletManager.service(portletClass, req, res);
        }

        req.removeAttribute(GridSphereProperties.PORTLETMODE);

    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        PrintWriter out = res.getWriter();
        out.println("</tr></table></td></tr></table></td></tr></table>");
    }

}
