/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.impl.PortletTitleBarEventImpl;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletURI;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class PortletTitleBar extends BasePortletComponent {

    private String title = "";
    private String titleColor = "#FFFFFF";
    private String font = "Arial, Helvetica, sans-serif";
    private String lineColor = "#336699";
    private String thickness = "1";
    private String portletClass = null;

    private PortletWindow.State portletWindowState = PortletWindow.State.NORMAL;

    private String[] portletModes = { Portlet.Mode.VIEW.toString() };
    private Portlet.Mode portletMode = Portlet.Mode.VIEW;
    private Portlet.Mode previousMode = null;

    protected String name = PortletTitleBar.class.getName();
    private List listeners = new ArrayList();

    private PortletSettings settings;
    private List modeList;
    private List allowsWindowStates = new ArrayList();
    private String ErrorMessage = null;

    public PortletTitleBar() {}

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getPortletClass() {
        return portletClass;
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

    public void setPortletWindowState(PortletWindow portletWindow) {
        this.portletWindowState = portletWindowState;
    }

    public PortletWindow.State getPortletWindowState() {
        return portletWindowState;
    }

    public void setPortletMode(Portlet.Mode portletMode) {
        this.portletMode = portletMode;
    }

    public Portlet.Mode getPortletMode() {
        return portletMode;
    }

    public String getClassName() {
        return PortletTitleBar.class.getName();
    }

    public void addTitleBarListener(PortletTitleBarListener listener) {
        listeners.add(listener);
    }

    public List init(List list) {
        list = super.init(list);
        PortletRegistryManager registryManager = PortletRegistryManager.getInstance();
        String appID = registryManager.getApplicationPortletID(portletClass);
        ApplicationPortlet appPortlet = registryManager.getApplicationPortlet(appID);

        SupportsModes supportedModes = appPortlet.getPortletApplicationDescriptor().getSupportsModes();
        modeList = supportedModes.getMarkupList();
        ConcretePortlet concPortlet = appPortlet.getConcretePortlet(portletClass);
        settings = concPortlet.getSportletSettings();

        // Get window state settings
        AllowsWindowStates allowedWindowStates = appPortlet.getPortletApplicationDescriptor().getAllowsWindowStates();
        allowsWindowStates = allowedWindowStates.getWindowStatesAsStrings();
        return list;
    }

    public void destroy() {

    }

    public void makeWindowLinks(GridSphereEvent event) {

        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();

        SportletURI sportletURI;

        // subtract current window state
        int i;
        List windowStates = new ArrayList(allowsWindowStates);
        String state = null;
        String minStr = PortletWindow.State.MINIMIZED.toString();
        String maxStr = PortletWindow.State.MAXIMIZED.toString();
        String resStr = PortletWindow.State.RESIZING.toString();
        System.err.println("states==" + windowStates.size());
        for (i = 0; i < windowStates.size(); i++) {
            state = (String)windowStates.get(i);
            if (state.equalsIgnoreCase(portletWindowState.toString())) {
                windowStates.remove(i);
            }

            // add a resize state to list if contains minimized or maximized
            if ((!windowStates.contains(resStr)) && ((state.equalsIgnoreCase(minStr)) || (state.equalsIgnoreCase(minStr)))) {
                windowStates.add(resStr);
            }
        }

        // create a URI for each of the window states
        String stateString;
        PortletStateLink stateLink;
        List stateLinks = new Vector();
        Iterator it = windowStates.iterator();
        while (it.hasNext()) {
            String winState = (String)it.next();
            sportletURI = event.createNewAction(GridSphereEvent.Action.LAYOUT_ACTION, COMPONENT_ID, portletClass);
            try {
                stateLink = new PortletStateLink(winState);
                // Create portlet link Href
                //modeAction = new DefaultPortletAction(LayoutProperties.CHANGESTATE);
                //sportletURI.addAction(modeAction);
                sportletURI.addParameter(GridSphereProperties.PORTLETWINDOW, winState);
                stateLink.setStateHref(sportletURI.toString());
                stateLinks.add(stateLink);
            } catch (Exception e) {
                ErrorMessage += "Unable to create window state link: " + winState + "\n";
            }
        }
        req.setAttribute(LayoutProperties.WINDOWSTATELINKS, stateLinks);
    }

    public void makeModeLinks(GridSphereEvent event) {

        int i;

        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();

        // get client preferred markup
        Client client = req.getClient();
        String prefMarkup = client.getMarkupName();

        // Make sure Client supports supported modes
        Markup m = null;
        Iterator it = modeList.iterator();
        while (it.hasNext()) {
            m = (Markup)it.next();
            if (m.getName().equals(client.getMarkupName())) {
                portletModes = m.getPortletModesAsStrings();
            }
        }

        // Only if user is logged in they get non-view modes
        User user = req.getUser();
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

        // create a URI for each of the portlet modes
        PortletURI sportletURI;
        String modeString;
        PortletModeLink modeLink;
        List portletLinks = new Vector();
        DefaultPortletAction modeAction;
        for (i = 0; i < portletModes.length; i++) {
            sportletURI = new SportletURI(res);
            //sportletURI.addParameter(GridSphereProperties.PORTLETID, (String)req.getAttribute(GridSphereProperties.PORTLETID));
            try {
                modeLink = new PortletModeLink(portletModes[i]);
                // Create portlet link Href

                //modeAction = new DefaultPortletAction(PortletAction.CHANGEMODE);
                //sportletURI.addAction(modeAction);
                sportletURI.addParameter(GridSphereProperties.PORTLETMODE, portletModes[i]);
                modeLink.setModeHref(sportletURI.toString());
                portletLinks.add(modeLink);
            } catch (Exception e) {
                ErrorMessage += "Unable to create portlet mode link: " + portletModes[i] + "\n";
            }
        }
        req.setAttribute(LayoutProperties.PORTLETMODELINKS, portletLinks);
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletTitleBarEvent evt = new PortletTitleBarEventImpl(event, COMPONENT_ID);
        if (evt != null) fireTitleBarAction(evt);
    }

    protected void fireTitleBarAction(PortletTitleBarEvent event) throws PortletLayoutException {
        Iterator it = listeners.iterator();
        PortletTitleBarListener l;
        while (it.hasNext()) {
            l = (PortletTitleBarListener)it.next();
            l.handleTitleBarEvent(event);
        }
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {

        // title bar: configure, edit, help, title, min, max
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
        PortletContext ctx = event.getPortletContext();

        // get the appropriate title for this client
        Client client = req.getClient();
        title = settings.getTitle(req.getLocale(), client);

        if (portletClass != null) {
            makeModeLinks(event);
        }

        req.setMode(portletMode);
        req.setPreviousMode(previousMode);

        try {
            req.setAttribute(LayoutProperties.TITLE, title);
            req.setAttribute(LayoutProperties.THICKNESS, thickness);
            req.setAttribute(LayoutProperties.LINECOLOR, lineColor);
            req.setAttribute(LayoutProperties.FONT, font);
            req.setAttribute(LayoutProperties.TITLECOLOR, titleColor);
            ctx.include("/WEB-INF/conf/layout/portlet-border-first.jsp", req, res);
        } catch (ServletException e) {
            ErrorMessage += "Unable to include JSP\n";
        }


        // Invoke doTitle of portlet whose action was perfomed
        if ((getComponentID() == event.getPortletComponentID()) && (portletClass != null)) {
            UserPortletManager userManager = event.getUserPortletManager();
            req.setPortletSettings(settings);
            try {
                userManager.doTitle(portletClass, req, res);
                title = "";
            } catch (PortletException e) {
                ErrorMessage += "Unable to invoke doTitle on active portlet\n";
                throw new PortletLayoutException("Unable to invoke doTitle on active portlet " + portletClass + "  " + COMPONENT_ID, e);
            }
        }

        if (portletClass != null) {
            makeWindowLinks(event);
        }

        try {
            req.setAttribute(LayoutProperties.TITLE, title);
            req.setAttribute(LayoutProperties.THICKNESS, thickness);
            req.setAttribute(LayoutProperties.LINECOLOR, lineColor);
            req.setAttribute(LayoutProperties.FONT, font);
            req.setAttribute(LayoutProperties.TITLECOLOR, titleColor);
            ctx.include("/WEB-INF/conf/layout/portlet-border-last.jsp", req, res);
        } catch (ServletException e) {
            ErrorMessage += "Unable to include JSP\n";
        }
    }

    public boolean hasError() {
        if (ErrorMessage == null) return false;
        return true;
    }

    public String getErrorReport() {
        return ErrorMessage;
    }

}
