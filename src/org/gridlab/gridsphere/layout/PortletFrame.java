/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.PortletErrorMessage;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryServiceException;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryServiceImpl;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;

import org.gridlab.gridsphere.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;

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
        PortletRegistryManager registryManager = PortletRegistryManager.getInstance();

        if (border == null) border = new PortletBorder();

        UserPortletManager userPortletManager = UserPortletManager.getInstance();

        // Set the portlet ID
        //req.setAttribute(GridSphereProperties.PORTLETID, portletClass);

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

        // Create URI tags that can be used
        /*
        PortletURI minimizedModeURI = res.createURI(PortletWindow.State.MINIMIZED);
        PortletURI maximizedModeURI = res.createURI(PortletWindow.State.MAXIMIZED);
        PortletURI closedModeURI = res.createURI(PortletWindow.State.CLOSED);
        PortletURI restoreModeURI = res.createURI(PortletWindow.State.RESIZING);

        PortletURI modeURI = res.createURI();
        DefaultPortletAction dpa = new DefaultPortletAction(PortletAction.MODE);
        modeURI.addAction(dpa);

        modeURI.addParameter(GridSphereProperties.PORTLETMODE, Portlet.Mode.EDIT.toString());
        String edit = modeURI.toString();
        req.setAttribute(LayoutProperties.EDITURI, edit);

        modeURI.addParameter(GridSphereProperties.PORTLETMODE, Portlet.Mode.HELP.toString());
        String help = modeURI.toString();
        req.setAttribute(LayoutProperties.HELPURI, help);

        modeURI.addParameter(GridSphereProperties.PORTLETMODE, Portlet.Mode.CONFIGURE.toString());
        String configure = modeURI.toString();
        req.setAttribute(LayoutProperties.CONFIGUREURI, configure);
        */

        ///// begin portlet frame
        PrintWriter out = res.getWriter();
        out.println("<table width=\"" + width + "%\"  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\"><tr><td>");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#999999\">");
        out.println("<tr><td width=\"100%\">");

        border.doRenderFirst(ctx, req, res);

        String id = (String)req.getParameter(GridSphereProperties.PORTLETID);
        if ((id != null) && (id.equals(portletClass))) {
            userPortletManager.doTitle(portletClass, req, res);
        } else {
            String appID = registryManager.getApplicationPortletID(portletClass);
            ApplicationPortlet appPortlet = registryManager.getApplicationPortlet(appID);
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(portletClass);
            PortletSettings settings = concPortlet.getSportletSettings();
            Client client = (Client)req.getAttribute(GridSphereProperties.CLIENT);
            String title = settings.getTitle(req.getLocale(), client);
            border.setTitle(title);
        }

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
