/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletWindow;
import org.gridlab.gridsphere.portlet.impl.SportletMode;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.PortletErrorMessage;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.container.registry.PortletDataManager;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class PortletFrame extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFrame.class);

    private String portletClass;

    // renderPortlet is true in doView and false on minimized
    private boolean renderPortlet = true;

    private Portlet.Mode portletMode = Portlet.Mode.VIEW;
    private Portlet.Mode previousMode = null;

    private PortletWindow portletWindow = null;

    private List listeners = new ArrayList();

    public PortletFrame() {
    }

    public void addActionListener(LayoutActionListener actionListener) {
        listeners.add(actionListener);
    }

    public void removeActionListener(LayoutActionListener actionListener) {
        listeners.remove(actionListener);
    }

    public void setConcretePortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getConcretePortletClass() {
        return portletClass;
    }

    public void setWindowState(String windowState) {
        try {
            portletWindow = new SportletWindow(windowState);
        } catch (Exception e) {
        }
    }

    public String getWindowState() {
        return portletWindow.toString();
    }

    public void setPortletMode(String pMode) {
        try {
        this.portletMode = SportletMode.getInstance(pMode);
        } catch (Exception e) {
        }
    }

    public String getPortletMode() {
        return portletMode.toString();
    }

    /*
    public void init() {
        if (border == null) border = new PortletBorder();
        border.setPortletClass(portletClass);
        border.setPortletWindow(portletWindow);
        border.setPortletMode(portletMode);

        border.addLayoutListener(this);

    }
    */

    public void doLayoutAction(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {

        // process events

        String id = (String)req.getParameter(GridSphereProperties.PORTLETID);
        if ((id != null) && (id.equals(portletClass))) {

            // change portlet mode if necessary
            String newmode = req.getParameter(GridSphereProperties.PORTLETMODE);
            if (newmode != null) {
                // Perform access control on portlet modes
                previousMode = portletMode;
                try {
                portletMode = Portlet.Mode.getInstance(newmode);
                } catch (Exception e) {}
            }

            // change window state if necessary
            String newwindow = req.getParameter(GridSphereProperties.PORTLETWINDOW);
            String minStr = PortletWindow.State.MINIMIZED.toString();
            String maxStr = PortletWindow.State.MAXIMIZED.toString();
            String resStr = PortletWindow.State.RESIZING.toString();
            String winstateStr;
            if (newwindow != null) {
                try {
                portletWindow = new SportletWindow(newwindow);
                } catch (Exception e) {}

                // if it's minimized then don't render portlet's service method
                winstateStr = portletWindow.getWindowState().toString();
                if (winstateStr.equalsIgnoreCase(minStr)) {
                    renderPortlet = false;
                } else if (winstateStr.equalsIgnoreCase(resStr)) {
                    renderPortlet = true;
                } else if (winstateStr.equalsIgnoreCase(maxStr)) {
                    renderPortlet = true;
                }
            }
        }

    }


    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderFirst(ctx, req, res);

        // Set the portlet ID
        req.setAttribute(GridSphereProperties.PORTLETID, portletClass);

        PortletRegistryManager registryManager = PortletRegistryManager.getInstance();


        String id = (String)req.getParameter(GridSphereProperties.PORTLETID);
        if ((id != null) && (id.equals(portletClass))) {

        }


        if (border == null) border = new PortletBorder();
        border.setPortletClass(portletClass);
        border.setPortletWindow(portletWindow);
        border.setPortletMode(portletMode);

        UserPortletManager userPortletManager = UserPortletManager.getInstance();

        PortletDataManager dataManager = PortletDataManager.getInstance();

        // Set the portlet window
        //PortletWindow window = SportletWindow.getInstance(windowState);
        //req.setAttribute(GridSphereProperties.PORTLETWINDOW, window);

        // Set the portlet mode
        //String prevMode = req.getParameter(GridSphereProperties.PORTLETMODE);
        //if (prevMode == null) prevMode = Portlet.Mode.VIEW.toString();
        //req.setAttribute(GridSphereProperties.PREVIOUSMODE, prevMode);


        // Where do we get portlet mode?
        // Assume that a GuestUser is view
        // Only if user is logged in do they get non-view modes



        // get Mode from UserPortletManager
        //Portlet.Mode portletMode = req.//dataManager.getPortletMode(portletClass, req, res);
        //if (portletMode == null) {
         //   Portlet.Mode portletMode = Portlet.Mode.getInstance(savedPortletMode);
        //}

        // get data from UserPortletManager
        //PortletData portletData = userPortletManager.getPortletData(portletClass, req, res);
        //req.setAttribute(GridSphereProperties.PORTLETDATA, portletData);



        req.setAttribute(GridSphereProperties.PORTLETMODE, portletMode);
        previousMode = portletMode;
        req.setAttribute(GridSphereProperties.PREVIOUSMODE, previousMode);

        req.setAttribute(GridSphereProperties.PORTLETWINDOW, portletWindow);

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
            if (renderPortlet)
                userPortletManager.service(portletClass, req, res);
        }

    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        PrintWriter out = res.getWriter();
        out.println("</tr></table></td></tr></table></td></tr></table>");
    }

}
