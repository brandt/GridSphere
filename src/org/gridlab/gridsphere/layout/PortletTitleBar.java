/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.event.impl.WindowEventImpl;
import org.gridlab.gridsphere.layout.impl.PortletTitleBarEventImpl;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class PortletTitleBar extends BasePortletComponent {

    private String title = "";
    private String portletClass = null;
    private PortletWindow.State portletWindowState = PortletWindow.State.NORMAL;

    private String[] portletModes = {Portlet.Mode.VIEW.toString()};
    private Portlet.Mode portletMode = Portlet.Mode.VIEW;
    private Portlet.Mode previousMode = null;

    private List listeners = new ArrayList();

    private PortletSettings settings;
    private List modeList;
    private List allowsWindowStates = new ArrayList();
    private String ErrorMessage = null;

    abstract class Link {
        protected String href = "";
        protected String imageSrc = "";
        protected String altTag = "";

        public String getImageSrc() {
            return imageSrc;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getHref() {
            return href;
        }

        public String getAltTag() {
            return altTag;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer("\n");
            sb.append("image src: " + imageSrc + "\n");
            sb.append("href: " + href + "\n");
            sb.append("alt tag: " + altTag + "\n");
            return sb.toString();
        }
    }

    class PortletModeLink extends Link {

        public static final String configImage = "images/window_configure.gif";
        public static final String editImage = "images/window_edit.gif";
        public static final String helpImage = "images/window_help.gif";

        public static final String configAlt = "Configure";
        public static final String editAlt = "Edit";
        public static final String helpAlt = "Help";

        /**
         * Given a potlet mode, create the necessary URIs to represnt the mode in a portlet border
         */
        public PortletModeLink(String mode) throws Exception {

            // Set the image src
            if (mode.equalsIgnoreCase(Portlet.Mode.CONFIGURE.toString())) {
                imageSrc = configImage;
                altTag = configAlt;
            } else if (mode.equalsIgnoreCase(Portlet.Mode.EDIT.toString())) {
                imageSrc = editImage;
                altTag = editAlt;
            } else if (mode.equalsIgnoreCase(Portlet.Mode.HELP.toString())) {
                imageSrc = helpImage;
                altTag = helpAlt;
            } else {
                throw new Exception("No matching Portlet.Mode found for received portlet mode: " + mode);
            }
        }
    }

    class PortletStateLink extends Link {

        public static final String minimizeImage = "images/window_minimize.gif";
        public static final String maximizeImage = "images/window_maximize.gif";
        public static final String resizeImage = "images/window_resize.gif";

        public static final String minimizeAlt = "Minimize";
        public static final String maximizeAlt = "Maximize";
        public static final String resizeAlt = "Resize";

        /**
         * Given a portlet window state, create the necessary URIs to represent the state in a portlet border
         */
        public PortletStateLink(String state) throws Exception {

            // Set the image src
            if (state.equalsIgnoreCase(PortletWindow.State.MINIMIZED.toString())) {
                imageSrc = minimizeImage;
                altTag = minimizeAlt;
            } else if (state.equalsIgnoreCase(PortletWindow.State.MAXIMIZED.toString())) {
                imageSrc = maximizeImage;
                altTag = maximizeAlt;
            } else if (state.equalsIgnoreCase(PortletWindow.State.RESIZING.toString())) {
                imageSrc = resizeImage;
                altTag = resizeAlt;
            } else {
                throw new Exception("No matching PortletWindow.State found for received window mode: " + state);
            }
        }

    }


    public PortletTitleBar() {
    }

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getPortletClass() {
        return portletClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPortletWindowState(PortletWindow.State portletWindow) {
        this.portletWindowState = portletWindow;
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

    public void addTitleBarListener(PortletTitleBarListener listener) {
        listeners.add(listener);
    }

    public List init(List list) {
        list = super.init(list);
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setPortletClass(portletClass);
        compId.setComponentID(list.size());
        compId.setClassName(this.getClass().getName());
        list.add(compId);

        PortletRegistry registryManager = PortletRegistry.getInstance();
        String appID = registryManager.getApplicationPortletID(portletClass);
        ApplicationPortlet appPortlet = registryManager.getApplicationPortlet(appID);
        if (appPortlet != null) {
            SupportsModes supportedModes = appPortlet.getApplicationPortletDescriptor().getSupportsModes();
            modeList = supportedModes.getMarkupList();
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(portletClass);
            settings = concPortlet.getPortletSettings();
        }
        // Get window state settings
        AllowsWindowStates allowedWindowStates = appPortlet.getApplicationPortletDescriptor().getAllowsWindowStates();
        allowsWindowStates = allowedWindowStates.getWindowStatesAsStrings();
        return list;
    }

    public void destroy() {

    }

    public List makeWindowLinks(GridSphereEvent event) {

        PortletURI portletURI;
        PortletResponse res = event.getPortletResponse();

        // subtract current window state
        List windowStates = new ArrayList(allowsWindowStates);

        if (portletWindowState == PortletWindow.State.NORMAL) {
            windowStates.remove(PortletWindow.State.RESIZING.toString().toLowerCase());
        }
        // remove current state from list
        windowStates.remove(portletWindowState.toString().toLowerCase());

        // create a URI for each of the window states
        PortletStateLink stateLink;
        List stateLinks = new Vector();
        Iterator it = windowStates.iterator();
        while (it.hasNext()) {
            String winState = (String) it.next();

            portletURI = res.createURI();
            portletURI.addParameter(GridSphereProperties.COMPONENT_ID, this.componentIDStr);
            portletURI.addParameter(GridSphereProperties.PORTLETID, portletClass);
            try {
                stateLink = new PortletStateLink(winState);
                // Create portlet link Href
                portletURI.addParameter(GridSphereProperties.PORTLETWINDOW, winState);
                stateLink.setHref(portletURI.toString());
                stateLinks.add(stateLink);
            } catch (Exception e) {
                ErrorMessage += "Unable to create window state link: " + winState + "\n";
            }
        }
        return stateLinks;
    }

    public List makeModeLinks(GridSphereEvent event) {

        int i;

        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        // get client preferred markup
        Client client = req.getClient();

        // Make sure Client supports supported modes
        Markup m = null;
        Iterator it = modeList.iterator();
        while (it.hasNext()) {
            m = (Markup) it.next();
            if (m.getName().equals(client.getMarkupName())) {
                portletModes = m.getPortletModesAsStrings();
            }
        }

        // subtract current portlet mode
        for (i = 0; i < portletModes.length; i++) {
            if (portletModes[i].equalsIgnoreCase(portletMode.toString())) {
                portletModes[i] = "";
            }
        }

        // create a URI for each of the portlet modes
        PortletURI portletURI;
        PortletModeLink modeLink;
        List portletLinks = new Vector();
        for (i = 0; i < portletModes.length; i++) {
            portletURI = res.createURI();
            portletURI.addParameter(GridSphereProperties.COMPONENT_ID, this.componentIDStr);
            portletURI.addParameter(GridSphereProperties.PORTLETID, portletClass);
            try {
                modeLink = new PortletModeLink(portletModes[i]);
                // Create portlet link Href

                portletURI.addParameter(GridSphereProperties.PORTLETMODE, portletModes[i]);
                modeLink.setHref(portletURI.toString());
                portletLinks.add(modeLink);
            } catch (Exception e) {
                ErrorMessage += "Unable to create portlet mode link: " + portletModes[i] + "\n";
            }
        }
        return portletLinks;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletTitleBarEvent evt = new PortletTitleBarEventImpl(event, COMPONENT_ID);
        PortletRequest req = event.getPortletRequest();
        if (evt.getAction() == PortletTitleBarEvent.Action.WINDOW_MODIFY) {
            PortletResponse res = event.getPortletResponse();
            portletWindowState = evt.getState();
            WindowEvent winEvent = null;

            if (portletWindowState == PortletWindow.State.MAXIMIZED) {
                winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_MAXIMIZED);
            } else if (portletWindowState == PortletWindow.State.MINIMIZED) {
                winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_MINIMIZED);
            } else if (portletWindowState == PortletWindow.State.RESIZING) {
                winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_RESTORED);
            }
            if (winEvent != null) {
                try {
                    //userManager.windowEvent(portletClass, winEvent, req, res);
                    PortletInvoker.windowEvent(portletClass, winEvent, req, res);
                } catch (PortletException e) {
                    throw new PortletLayoutException("Failed to invoke window event method of portlet: " + portletClass);
                }
            }
        } else if (evt.getAction() == PortletTitleBarEvent.Action.MODE_MODIFY) {
            previousMode = portletMode;
            portletMode = evt.getMode();
            req.setMode(portletMode);
            req.setAttribute(GridSphereProperties.PREVIOUSMODE, portletMode);
        }
        if (evt != null) fireTitleBarAction(evt);
    }

    protected void fireTitleBarAction(PortletTitleBarEvent event) throws PortletLayoutException {
        Iterator it = listeners.iterator();
        PortletTitleBarListener l;
        while (it.hasNext()) {
            l = (PortletTitleBarListener) it.next();
            l.handleTitleBarEvent(event);
        }
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {

        // title bar: configure, edit, help, title, min, max
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        // get the appropriate title for this client
        Client client = req.getClient();
        title = settings.getTitle(req.getLocale(), client);

        List modeLinks = null, windowLinks = null;
        User user = req.getUser();
        if (user instanceof GuestUser) {
        } else {
            if (portletClass != null) {
                modeLinks = makeModeLinks(event);
                windowLinks = makeWindowLinks(event);
            }
        }

        req.setMode(portletMode);
        req.setAttribute(GridSphereProperties.PREVIOUSMODE, previousMode);

        PrintWriter out = res.getWriter();

        out.println("<tr><td class=\"window-title\">");
        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr>");

        // Output portlet mode icons
        if (modeLinks != null) {
            Iterator modesIt = modeLinks.iterator();
            out.println("<td class=\"window-icon-left\">");
            PortletModeLink mode;
            while (modesIt.hasNext()) {
                mode = (PortletModeLink) modesIt.next();
                out.println("<a href=\"" + mode.getHref() + "\"><img border=\"0\" src=\"themes/" + theme + "/" + mode.getImageSrc() + "\" title=\"" + mode.getAltTag() + "\"/></a>");
            }
            out.println("</td>");
        }

        // Invoke doTitle of portlet whose action was perfomed
        String actionStr = req.getParameter(GridSphereProperties.ACTION);
        out.println("<td class=\"window-title-name\">");
        if (actionStr != null) {
            try {
                PortletInvoker.doTitle(portletClass, req, res);
                out.println(" (" + portletMode.toString() + ") ");
            } catch (PortletException e) {
                ErrorMessage += "Unable to invoke doTitle on active portlet\n";
                throw new PortletLayoutException("Unable to invoke doTitle on active portlet " + portletClass + "  " + COMPONENT_ID, e);
            }

        } else {
            out.println(title);
        }

        out.println("</td>");

        // Output window state icons
        if (windowLinks != null) {
            Iterator windowsIt = windowLinks.iterator();
            PortletStateLink state;
            out.println("<td class=\"window-icon-right\">");
            while (windowsIt.hasNext()) {
                state = (PortletStateLink) windowsIt.next();
                out.println("<a href=\"" + state.getHref() + "\"><img border=\"0\" src=\"themes/" + theme + "/" + state.getImageSrc() + "\" title=\"" + state.getAltTag() + "\"/></a>");
            }
            out.println("</td>");
        }
        out.println("</tr></table>");
        out.println("</td></tr>");
    }

    public boolean hasError() {
        if (ErrorMessage == null) return false;
        return true;
    }

    public String getErrorReport() {
        return ErrorMessage;
    }

}
