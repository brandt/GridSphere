/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletURI;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

public class PortletBorder implements LayoutActionListener {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletBorder.class);

    private PortletRegistryManager registryManager = PortletRegistryManager.getInstance();
    private UserPortletManager userManager = UserPortletManager.getInstance();

    private String title = "";
    private String titleColor = "#FFFFFF";
    private String font = "Arial, Helvetica, sans-serif";
    private String lineColor = "#336699";
    private String thickness = "1";
    private String portletClass = null;

    private Portlet.Mode portletMode;
    private PortletWindow portletWindow;

    private String[] windowStates;

    public PortletBorder() {}

    public PortletBorder(String title, String titleColor, String font, String lineColor, String thickness) {
        if (title != null) this.title = title;
        if (titleColor != null) this.titleColor = titleColor;
        if (font != null) this.font = font;
        if (lineColor != null) this.lineColor = lineColor;
        if (thickness != null) this.thickness= thickness;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public String getTitleFont() {
        return font;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleColor(String color) {
        this.titleColor = titleColor;
    }

    public void setTitleFont(String font) {
        this.font = font;
    }

    public void setLineColor(String color) {
        this.lineColor = lineColor;
    }

    public String getLineColor() {
        return lineColor;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getPortletClass() {
        return portletClass;
    }

    public void setPortletWindow(PortletWindow portletWindow) {
        this.portletWindow = portletWindow;
    }

    public PortletWindow getPortletWindow() {
        return portletWindow;
    }

    public void setPortletMode(Portlet.Mode portletMode) {
        this.portletMode = portletMode;
    }

    public Portlet.Mode getPortletMode() {
        return portletMode;
    }

    /*
    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRenderFirst()");
        PrintWriter out = res.getWriter();
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" bgcolor=\"" +  lineColor + "\"");
        out.println("<tr><td><img src=\"images/help.gif\" align=left></td><td height=\"20\" align=left valign=middle>");
    }
    */

    public void makeLinks(HttpServletRequest req, HttpServletResponse res) {

        int i;

        // get client preferred markup
        Client client = (Client)req.getSession().getAttribute(GridSphereProperties.CLIENT);
        String prefMarkup = client.getMarkupName();
        log.info("Client: " + client.toString());


        String appID = registryManager.getApplicationPortletID(portletClass);
        ApplicationPortlet appPortlet = registryManager.getApplicationPortlet(appID);

        SupportsModes supportedModes = appPortlet.getPortletApplicationDescriptor().getSupportsModes();
        List modeList = supportedModes.getMarkupList();
        Iterator it = modeList.iterator();
        Markup m = null;
        String[] portletModes = new String[1];
        portletModes[0] = "view";

        // Make sure Client supports supported modes
        while (it.hasNext()) {
            m = (Markup)it.next();
            if (m.getName().equals(client.getMarkupName())) {
                portletModes = m.getPortletModesAsStrings();
            }
        }

        // Only if user is logged in do they get non-view modes
        User user = (User)req.getSession().getAttribute(GridSphereProperties.USER);
        if (user instanceof GuestUser) {
            portletModes = new String[1];
            portletModes[0] = "view";
        }

        // subtract current portlet mode
        String s;
        for (i = 0; i < portletModes.length; i++) {
            if (portletModes[i].equalsIgnoreCase(portletMode.toString())) {
                portletModes[i] = "";
            }
        }

        ConcretePortlet concPortlet = appPortlet.getConcretePortlet(portletClass);
        PortletSettings settings = concPortlet.getSportletSettings();
        title = settings.getTitle(req.getLocale(), client);


        // Get window state settings
        AllowsWindowStates allowedWindowStates = appPortlet.getPortletApplicationDescriptor().getAllowsWindowStates();
        List windowStates = allowedWindowStates.getWindowStatesAsStrings();


        // subtract current window state
        String state = null;
        String minStr = PortletWindow.State.MINIMIZED.toString();
        String maxStr = PortletWindow.State.MAXIMIZED.toString();
        String resStr = PortletWindow.State.RESIZING.toString();
        for (i = 0; i < windowStates.size(); i++) {
            state = (String)windowStates.get(i);
            if (state.equalsIgnoreCase(portletWindow.toString())) {
                windowStates.remove(i);
            }

            // add a resize state to list if contains minimized or maximized
            if ((!windowStates.contains(resStr)) && ((state.equalsIgnoreCase(minStr)) || (state.equalsIgnoreCase(minStr)))) {
                windowStates.add(resStr);
            }
        }

        // create a URI for each of the portlet modes
        PortletURI sportletURI;
        String modeString;
        PortletModeLink modeLink;
        List portletLinks = new Vector();
        DefaultPortletAction modeAction;
        for (i = 0; i < portletModes.length; i++) {
            sportletURI = new SportletURI(res);
            sportletURI.addParameter(GridSphereProperties.PORTLETID, (String)req.getAttribute(GridSphereProperties.PORTLETID));
            try {
                modeLink = new PortletModeLink(portletModes[i]);
                // Create portlet link Href

                //modeAction = new DefaultPortletAction(PortletAction.CHANGEMODE);
                //sportletURI.addAction(modeAction);

                sportletURI.addParameter(GridSphereProperties.PORTLETMODE, portletModes[i]);
                modeLink.setModeHref(sportletURI.toString());
                portletLinks.add(modeLink);
                log.info("mode: " + modeLink.toString());
            } catch (Exception e) {
                log.error("Unable to create portlet link: " + e.getMessage());
            }
        }
        req.setAttribute(LayoutProperties.PORTLETMODELINKS, portletLinks);

        // create a URI for each of the window states
        String stateString;
        PortletStateLink stateLink;
        List stateLinks = new Vector();
        it = windowStates.iterator();
        while (it.hasNext()) {
            String winState = (String)it.next();
            sportletURI = new SportletURI(res);
            sportletURI.addParameter(GridSphereProperties.PORTLETID, (String)req.getAttribute(GridSphereProperties.PORTLETID));
            try {
            stateLink = new PortletStateLink(winState);
            // Create portlet link Href
            //modeAction = new DefaultPortletAction(LayoutProperties.CHANGESTATE);
            //sportletURI.addAction(modeAction);
            sportletURI.addParameter(GridSphereProperties.PORTLETWINDOW, winState);
            stateLink.setStateHref(sportletURI.toString());
            stateLinks.add(stateLink);
            log.info("state: " + stateLink.toString());
            } catch (Exception e) {
                log.error("Unable to create window state link: " + e.getMessage());
            }
        }
        req.setAttribute(LayoutProperties.WINDOWSTATELINKS, stateLinks);

    }

    public void doLayoutAction(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {

    }

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {

        // title bar: configure, edit, help, title, min, max

        if (portletClass != null) {
            makeLinks(req, res);
        }

        try {
            req.setAttribute(LayoutProperties.TITLE, title);
            req.setAttribute(LayoutProperties.THICKNESS, thickness);
            req.setAttribute(LayoutProperties.LINECOLOR, lineColor);
            req.setAttribute(LayoutProperties.FONT, font);
            req.setAttribute(LayoutProperties.TITLECOLOR, titleColor);
            req.setAttribute(LayoutProperties.WINDOWSTATES, windowStates);
            RequestDispatcher rd = ctx.getRequestDispatcher("/WEB-INF/conf/layout/portlet-border-first.jsp");
            rd.include(req, res);
        } catch (ServletException e) {
            log.error("Unable to include JSP", e);
            throw new PortletLayoutException("Unable to include JSP", e);
        }

    }

    /*
    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRenderLast()");
        PrintWriter out = res.getWriter();
        out.println("<font color=\"#FFFFFF\" face=\"" + font + "\">&nbsp;" + title);
        out.println("</font></td><td><img src=\"images/window.gif\" align=right></td></tr></table>");
    }
    */
   public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {


        // Invoke doTitle of portlet whose action was perfomed
        String id = (String)req.getParameter(GridSphereProperties.PORTLETID);
        if ((id != null) && (id.equals(portletClass))) {
            userManager.doTitle(portletClass, req, res);
            title = "";
        }

         try {
            req.setAttribute(LayoutProperties.TITLE, title);
            req.setAttribute(LayoutProperties.THICKNESS, thickness);
            req.setAttribute(LayoutProperties.LINECOLOR, lineColor);
            req.setAttribute(LayoutProperties.FONT, font);
            req.setAttribute(LayoutProperties.TITLECOLOR, titleColor);

            req.setAttribute(LayoutProperties.WINDOWSTATES, windowStates);
            RequestDispatcher rd = ctx.getRequestDispatcher("/WEB-INF/conf/layout/portlet-border-last.jsp");
            rd.include(req, res);
        } catch (ServletException e) {
            log.error("Unable to include component JSP", e);
            throw new PortletLayoutException("Unable to include JSP", e);
        }
    }


}
