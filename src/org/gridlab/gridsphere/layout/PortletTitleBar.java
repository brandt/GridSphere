/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.event.impl.WindowEventImpl;
import org.gridlab.gridsphere.layout.event.impl.PortletTitleBarEventImpl;
import org.gridlab.gridsphere.layout.event.PortletTitleBarEvent;
import org.gridlab.gridsphere.layout.event.PortletTitleBarListener;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;
import org.gridlab.gridsphere.portletcontainer.descriptor.ApplicationPortletDescriptor;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * A PortletTitleBar represents the visual display of the portlet title bar
 * within a portlet frame and is contained by {@link PortletFrame}.
 * The title bar contains portlet mode and window state as well as a title.
 */
public class PortletTitleBar extends BasePortletComponent {

    private String title = "Portlet Unavailable";
    private String portletClass = null;
    private PortletWindow.State windowState = PortletWindow.State.NORMAL;

    private String[] portletModes = {Portlet.Mode.VIEW.toString()};
    private Portlet.Mode portletMode = Portlet.Mode.VIEW;
    private Portlet.Mode previousMode = null;

    private List listeners = new ArrayList();

    private PortletSettings settings;
    private List modeList = null;
    private List allowsWindowStates = new ArrayList();
    private String ErrorMessage = null;

    /**
     * Link is an abstract representation of a hyperlink with an href, image and
     * alt tags.
     */
    abstract class Link {
        protected String href = "";
        protected String imageSrc = "";
        protected String altTag = "";

        /**
         * Returns the image source attribute in the link
         *
         * @return the image source attribute in the link
         */
        public String getImageSrc() {
            return imageSrc;
        }

        /**
         * Sets the href attribute in the link
         *
         * @param href the href attribute in the link
         */
        public void setHref(String href) {
            this.href = href;
        }

        /**
         * Returns the href attribute in the link
         *
         * @return the href attribute in the link
         */
        public String getHref() {
            return href;
        }

        /**
         * Returns the alt tag attribute in the link
         *
         * @return the alt tag attribute in the link
         */
        public String getAltTag() {
            return altTag;
        }

        /**
         * Returns a string containing the image src, href and alt tag attributes
         * Used primarily for debugging purposes
         */
        public String toString() {
            StringBuffer sb = new StringBuffer("\n");
            sb.append("image src: " + imageSrc + "\n");
            sb.append("href: " + href + "\n");
            sb.append("alt tag: " + altTag + "\n");
            return sb.toString();
        }
    }

    /**
     * PortletModeLink is a concrete instance of a Link used for creating
     * portlet mode hyperlinks
     */
    class PortletModeLink extends Link {

        public static final String configImage = "images/window_configure.gif";
        public static final String editImage = "images/window_edit.gif";
        public static final String helpImage = "images/window_help.gif";

        public static final String configAlt = "Configure";
        public static final String editAlt = "Edit";
        public static final String helpAlt = "Help";

        /**
         * Constructs an instance of PortletModeLink with the supplied portlet mode
         *
         * @param mode the portlet mode
         */
        public PortletModeLink(String mode) throws IllegalArgumentException {

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
                throw new IllegalArgumentException("No matching Portlet.Mode found for received portlet mode: " + mode);
            }
        }
    }

    /**
     * PortletStateLink is a concrete instance of a Link used for creating
     * portlet window state hyperlinks
     */
    class PortletStateLink extends Link {

        public static final String minimizeImage = "images/window_minimize.gif";
        public static final String maximizeImage = "images/window_maximize.gif";
        public static final String resizeImage = "images/window_resize.gif";

        public static final String minimizeAlt = "Minimize";
        public static final String maximizeAlt = "Maximize";
        public static final String resizeAlt = "Resize";

        /**
         * Constructs an instance of PortletStateLink with the supplied window state
         *
         * @param state the window state
         */
        public PortletStateLink(String state) throws IllegalArgumentException {
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
                throw new IllegalArgumentException("No matching PortletWindow.State found for received window mode: " + state);
            }
        }
    }

    /**
     * Constructs an instance of PortletTitleBar
     */
    public PortletTitleBar() {
    }

    /**
     * Sets the portlet class used to render the title bar
     *
     * @param portletClass the concrete portlet class
     */
    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    /**
     * Returns the portlet class used in rendering the title bar
     *
     * @return the concrete portlet class
     */
    public String getPortletClass() {
        return portletClass;
    }

    /**
     * Returns the title of the portlet title bar
     *
     * @return the portlet title bar
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the portlet title bar
     *
     * @param title the portlet title bar
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the window state of this title bar
     *
     * @param state the portlet window state expressed as a string
     * @see PortletWindow.State
     */
    public void setWindowState(String state) {
        try {
            this.windowState = PortletWindow.State.toState(state);
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * Returns the window state of this title bar
     *
     * @return the portlet window state expressed as a string
     * @see PortletWindow.State
     */
    public String getWindowState() {
        return windowState.toString();
    }

    /**
     * Sets the portlet mode of this title bar
     *
     * @param state the portlet mode expressed as a string
     * @see Portlet.Mode
     */
    public void setPortletMode(String mode) {
        try {
            this.portletMode = Portlet.Mode.toMode(mode);
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * Returns the portlet mode of this title bar
     *
     * @return the portlet mode expressed as a string
     * @see Portlet.Mode
     */
    public String getPortletMode() {
        return portletMode.toString();
    }

    /**
     * Adds a title bar listener to be notified of title bar events
     *
     * @param listener a title bar listener
     * @see PortletTitleBarEvent
     */
    public void addTitleBarListener(PortletTitleBarListener listener) {
        listeners.add(listener);
    }

    /**
     * Initializes the portlet title bar. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(List list) {
        list = super.init(list);
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setPortletClass(portletClass);
        compId.setComponentID(list.size());
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        doConfig();
        return list;
    }

    /**
     * Sets configuration information about the supported portlet modes,
     * allowed window states and title bar obtained from {@link PortletSetings}.
     * Information is queried from the {@link PortletRegistry}
     */
    protected void doConfig() {
        PortletRegistry registryManager = PortletRegistry.getInstance();
        String appID = registryManager.getApplicationPortletID(portletClass);
        ApplicationPortlet appPortlet = registryManager.getApplicationPortlet(appID);
        if (appPortlet != null) {
            ApplicationPortletDescriptor appDD = appPortlet.getApplicationPortletDescriptor();
            SupportsModes supportedModes = appDD.getSupportsModes();
            modeList = supportedModes.getMarkupList();
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(portletClass);
            settings = concPortlet.getPortletSettings();
            // Get window state settings
            AllowsWindowStates allowedWindowStates = appDD.getAllowsWindowStates();
            allowsWindowStates = allowedWindowStates.getWindowStatesAsStrings();
        }
    }

    /**
     * Creates the portlet window state hyperlinks displayed in the title bar
     *
     * @param event the gridsphere event
     * @return a list of window state hyperlinks
     */
    protected List createWindowLinks(GridSphereEvent event) {

        PortletURI portletURI;
        PortletResponse res = event.getPortletResponse();

        // subtract current window state
        List windowStates = new ArrayList(allowsWindowStates);

        if (windowState == PortletWindow.State.NORMAL) {
            windowStates.remove(PortletWindow.State.RESIZING.toString().toLowerCase());
        }
        // remove current state from list
        windowStates.remove(windowState.toString().toLowerCase());

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
                portletURI.addParameter(GridSphereProperties.PORTLETWINDOW, winState);
                stateLink.setHref(portletURI.toString());
                stateLinks.add(stateLink);
            } catch (IllegalArgumentException e) {
                ErrorMessage += "Unable to create window state link: " + winState + "\n";
            }
        }
        return stateLinks;
    }

    /**
     * Creates the portlet mode hyperlinks displayed in the title bar
     *
     * @param event the gridsphere event
     * @return a list of portlet mode hyperlinks
     */
    public List createModeLinks(GridSphereEvent event) {

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
                portletURI.addParameter(GridSphereProperties.PORTLETMODE, portletModes[i]);
                modeLink.setHref(portletURI.toString());
                portletLinks.add(modeLink);
            } catch (IllegalArgumentException e) {
                // do nothing
            }
        }
        return portletLinks;
    }

    /**
     * Performs an action on this portlet title bar component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletTitleBarEvent evt = new PortletTitleBarEventImpl(event, COMPONENT_ID);
        PortletRequest req = event.getPortletRequest();
        if (evt.getAction() == PortletTitleBarEvent.Action.WINDOW_MODIFY) {
            PortletResponse res = event.getPortletResponse();
            windowState = evt.getState();
            WindowEvent winEvent = null;

            if (windowState == PortletWindow.State.MAXIMIZED) {
                winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_MAXIMIZED);
            } else if (windowState == PortletWindow.State.MINIMIZED) {
                winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_MINIMIZED);
            } else if (windowState == PortletWindow.State.RESIZING) {
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
        if (evt != null) fireTitleBarEvent(evt);
    }

    /**
     * Fires a title bar event notification
     *
     * @param event a portlet title bar event
     * @throws PortletLayoutException if a layout error occurs
     */
    protected void fireTitleBarEvent(PortletTitleBarEvent event) throws PortletLayoutException {
        Iterator it = listeners.iterator();
        PortletTitleBarListener l;
        while (it.hasNext()) {
            l = (PortletTitleBarListener) it.next();
            l.handleTitleBarEvent(event);
        }
    }

    /**
     * Renders the portlet title bar component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {

        // title bar: configure, edit, help, title, min, max
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        // get the appropriate title for this client
        Client client = req.getClient();

        if (settings == null) {
            doConfig();
        }
        if (settings != null) {
            title = settings.getTitle(req.getLocale(), client);
        }

        List modeLinks = null, windowLinks = null;
        User user = req.getUser();
        if (user instanceof GuestUser) {
        } else {
            if (portletClass != null) {
                if (modeList != null) {
                    modeLinks = createModeLinks(event);
                }
                windowLinks = createWindowLinks(event);
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

}
