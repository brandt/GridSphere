/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletTitleBar.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.event.WindowEvent;
import org.gridsphere.event.impl.WindowEventImpl;
import org.gridsphere.layout.event.PortletTitleBarEvent;
import org.gridsphere.layout.event.PortletTitleBarListener;
import org.gridsphere.layout.event.impl.PortletTitleBarEventImpl;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portlet.*;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.portletcontainer.*;
import org.gridsphere.portletcontainer.impl.PortletInvoker;
import org.gridsphere.services.core.registry.PortletRegistryService;
import org.gridsphere.services.core.security.role.PortletRole;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.security.Principal;
import java.util.*;

/**
 * A <code>PortletTitleBar</code> represents the visual display of the portlet title bar
 * within a portlet frame and is contained by {@link PortletFrame}.
 * The title bar contains portlet mode and window state as well as a title.
 */
public class PortletTitleBar extends BasePortletComponent implements Serializable, Cloneable {

    private static PortletLog log = SportletLog.getInstance(PortletTitleBar.class);

    private String title = "unknown title";
    private String portletClass = null;

    private transient PortletRegistryService portletRegistryService = null;
    private transient PortletInvoker portletInvoker = null;

    private transient PortletWindow.State windowState = PortletWindow.State.NORMAL;

    private transient Mode portletMode = Mode.VIEW;
    private transient Mode previousMode = Mode.VIEW;
    private transient List allowedWindowStates = new ArrayList();
    private transient String errorMessage = "";
    private transient boolean hasError = false;
    private transient boolean isActive = false;

    private transient List modeLinks = null;
    private transient List windowLinks = null;

    private transient Render titleView = null;

    /**
     * Link is an abstract representation of a hyperlink with an href, image and
     * alt tags.
     */
    abstract class Link {
        protected String href = "";
        protected String imageSrc = "";
        protected String altTag = "";
        protected String symbol = "";
        protected String cursor = "";

        /**
         * Returns the image source attribute in the link
         *
         * @return the image source attribute in the link
         */
        public String getImageSrc() {
            return imageSrc;
        }

        public String getSymbol() { //WAP 2.0 Extention
            return symbol;
        }

        /**
         * Returns the CSS cursor style to use
         *
         * @return the cursor
         */
        public String getCursor() {
            return cursor;
        }

        /**
         * Sets the CSS cursor style to use
         *
         * @param cursor the cursor
         */
        public void setCursor(String cursor) {
            this.cursor = cursor;
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
    public class PortletModeLink extends Link {

        public static final String configImage = "images/window_configure.gif";
        public static final String configSymbol = "c";//WAP 2.0 Extention
        public static final String editImage = "images/window_edit.gif";
        public static final String editSymbol = "/";//WAP 2.0 Extention
        public static final String helpImage = "images/window_help.gif";
        public static final String helpSymbol = "?";//WAP 2.0 Extention
        public static final String viewImage = "images/window_view.gif";
        public static final String viewSymbol = "V";//WAP 2.0Extention

        /**
         * Constructs an instance of PortletModeLink with the supplied portlet mode
         *
         * @param mode the portlet mode
         */
        public PortletModeLink(Mode mode, Locale locale) throws IllegalArgumentException {
            if (mode == null) return;

            altTag = mode.getText(locale);

            // Set the image src
            if (mode.equals(Mode.CONFIGURE)) {
                imageSrc = configImage;
                symbol=configSymbol;//WAP 2.0
            } else if (mode.equals(Mode.EDIT)) {
                imageSrc = editImage;
                symbol=editSymbol;//WAP 2.0
            } else if (mode.equals(Mode.HELP)) {
                imageSrc = helpImage;
                symbol=helpSymbol;//WAP 2.0
                cursor = "help";
            } else if (mode.equals(Mode.VIEW)) {
                imageSrc = viewImage;
                symbol=viewSymbol;//WAP 2.0
            } else {
                throw new IllegalArgumentException("No matching Portlet.Mode found for received portlet mode: " + mode);
            }
        }
    }

    /**
     * PortletStateLink is a concrete instance of a Link used for creating
     * portlet window state hyperlinks
     */
    public class PortletStateLink extends Link {

        public static final String closeImage = "images/window_close.gif";
        public static final String minimizeImage = "images/window_minimize.gif";
        public static final String maximizeImage = "images/window_maximize.gif";
        public static final String resizeImage = "images/window_resize.gif";
        public static final String floatImage = "images/window_float.gif";

        public static final String closeSymbol = "X"; //WAP 2.0
        public static final String minimizeSymbol = "_"; //WAP 2.0
        public static final String maximizeSymbol = "="; //WAP 2.0
        public static final String resizeSymbol = "-"; //WAP 2.0
        public static final String floatSymbol = "^"; //WAP 2.0

        /**
         * Constructs an instance of PortletStateLink with the supplied window state
         *
         * @param state the window state
         */
        public PortletStateLink(PortletWindow.State state, Locale locale) throws IllegalArgumentException {
            if (state == null) return;
            // Set the image src
            if (state.equals(PortletWindow.State.MINIMIZED)) {
                imageSrc = minimizeImage;
                symbol = minimizeSymbol;
            } else if (state.equals(PortletWindow.State.MAXIMIZED)) {
                imageSrc = maximizeImage;
                symbol = maximizeSymbol;
            } else if (state.equals(PortletWindow.State.RESIZING)) {
                imageSrc = resizeImage;
                symbol = resizeSymbol;
            } else if (state.equals(PortletWindow.State.CLOSED)) {
                imageSrc = closeImage;
                symbol = closeSymbol;
            } else if (state.equals(PortletWindow.State.FLOATING)) {
                imageSrc = floatImage;
                symbol = floatSymbol;
            } else {
                throw new IllegalArgumentException("No matching PortletWindow.State found for received window mode: " + state);
            }
            altTag = state.getText(locale);

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
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
     */
    public void setWindowState(PortletWindow.State state) {
        if (state != null) this.windowState = state;
    }

    /**
     * Returns the window state of this title bar
     *
     * @return the portlet window state expressed as a string
     */
    public PortletWindow.State getWindowState() {
        return windowState;
    }

    /**
     * Sets the window state of this title bar
     *
     * @param state the portlet window state expressed as a string
     */
    public void setWindowStateAsString(String state) {
        if (state != null) {
            try {
                this.windowState = PortletWindow.State.toState(state);
            } catch (IllegalArgumentException e) {
                // do nothing
            }
        }
    }

    /**
     * Returns the window state of this title bar
     *
     * @return the portlet window state expressed as a string
     */
    public String getWindowStateAsString() {
        return windowState.toString();
    }

    /**
     * Sets the portlet mode of this title bar
     *
     * @param mode the portlet mode expressed as a string
     */
    public void setPortletMode(Mode mode) {
        if (mode != null) this.portletMode = mode;
    }

    /**
     * Returns the portlet mode of this title bar
     *
     * @return the portlet mode expressed as a string
     */
    public Mode getPortletMode() {
        return portletMode;
    }

    /**
     * Sets the portlet mode of this title bar
     *
     * @param mode the portlet mode expressed as a string
     */
    public void setPreviousMode(Mode mode) {
        if (mode != null) this.previousMode = mode;
    }

    /**
     * Returns the portlet mode of this title bar
     *
     * @return the portlet mode expressed as a string
     */
    public Mode getPreviousMode() {
        return previousMode;
    }

    /**
     * Sets the portlet mode of this title bar
     *
     * @param mode the portlet mode expressed as a string
     */
    public void setPortletModeAsString(String mode) {
        if (mode == null) return;
        try {
            this.portletMode = Mode.toMode(mode);
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }

    /**
     * Returns the portlet mode of this title bar
     *
     * @return the portlet mode expressed as a string
     */
    public String getPortletModeAsString() {
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
     * Indicates an error ocurred suring the processing of this title bar
     *
     * @return <code>true</code> if an error occured during rendering,
     *         <code>false</code> otherwise
     */
    public boolean hasRenderError() {
        return hasError;
    }

    /**
     * Returns any errors associated with the functioning of this title bar
     *
     * @return any title bar errors that occured
     */
    public String getErrorMessage() {
        return errorMessage;
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
    public List init(PortletRequest req, List list) {
        list = super.init(req, list);
        titleView = (Render)getRenderClass(req, "TitleBar");
        portletInvoker = new PortletInvoker();
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setPortletClass(portletClass);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        doConfig();
        return list;
    }

    /**
     * Sets configuration information about the supported portlet modes,
     * allowed window states and title bar and web app name obtained from {@link PortletSettings}.
     * Information is queried from the {@link PortletRegistryService}
     */
    protected void doConfig() {
        try {
            portletRegistryService = (PortletRegistryService)PortletServiceFactory.createPortletService(PortletRegistryService.class, true);
        } catch (PortletServiceException e) {
            log.error("Unable to init services! ", e);
        }

        String appID = portletRegistryService.getApplicationPortletID(portletClass);
        ApplicationPortlet appPortlet = portletRegistryService.getApplicationPortlet(appID);
        if (appPortlet != null) {
            ApplicationPortletConfig appConfig = appPortlet.getApplicationPortletConfig();
            if (appConfig != null) {
                // get supported modes from application portlet config
                //supportedModes = sort(appConfig.getSupportedModes());

                // get window states from application portlet config

                allowedWindowStates = new ArrayList(appConfig.getAllowedWindowStates());
                allowedWindowStates = sort(allowedWindowStates);

                if (canModify) {
                    if (!allowedWindowStates.contains(PortletWindow.State.CLOSED)) {
                        allowedWindowStates.add(PortletWindow.State.CLOSED);
                    }
                }
            }
        }
    }

    /**
     * Simple sorting algoritm that sorts in increasing order a <code>List</code>
     * containing objects that implement <code>Comparator</code>
     *
     * @param list a <code>List</code> to be sorted
     * @return the sorted list
     */
    private List sort(List list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Comparator c = (Comparator) list.get(j);
                Comparator d = (Comparator) list.get(j + 1);
                if (c.compare(c, d) == 1) {
                    Object tmp = list.get(j);
                    list.set(j, d);
                    list.set(j + 1, tmp);
                }
            }
        }
        return list;
    }

    /**
     * Creates the portlet window state hyperlinks displayed in the title bar
     *
     * @param event the gridsphere event
     * @return a list of window state hyperlinks
     */
    public List createWindowLinks(GridSphereEvent event) {
        PortletURI portletURI;
        PortletResponse res = event.getPortletResponse();
        PortletWindow.State tmp;

        if (allowedWindowStates.isEmpty()) return null;

        //String[] windowStates = new String[allowedWindowStates.size()];
        List windowStates = new ArrayList();
        for (int i = 0; i < allowedWindowStates.size(); i++) {

            tmp = (PortletWindow.State) allowedWindowStates.get(i);
            windowStates.add(tmp);
            // remove current state from list
            if (tmp.equals(windowState) && (!windowState.equals(PortletWindow.State.CLOSED))) {
                windowStates.remove(i);
            }
        }

        // get rid of resized if window state is normal
        if (windowState.equals(PortletWindow.State.NORMAL) || windowState.equals(PortletWindow.State.CLOSED)) {
            windowStates.remove(PortletWindow.State.RESIZING);
        }

        // get rid of floating if window state is minimized
        if (windowState.equals(PortletWindow.State.MINIMIZED)) windowStates.remove(PortletWindow.State.FLOATING);

        // Localize the window state names
        PortletRequest req = event.getPortletRequest();

        Locale locale = req.getLocale();

        // create a URI for each of the window states
        PortletStateLink stateLink;
        List stateLinks = new Vector();
        for (int i = 0; i < windowStates.size(); i++) {
            tmp = (PortletWindow.State) windowStates.get(i);
            portletURI = res.createURI();
            portletURI.addParameter(this.getComponentIDVar(req), this.componentIDStr);

            try {
                stateLink = new PortletStateLink(tmp, locale);
                portletURI.addParameter(SportletProperties.PORTLET_WINDOW, tmp.toString());
                stateLink.setHref(portletURI.toString());
                if (tmp.equals(PortletWindow.State.FLOATING)) {
                    stateLink.setHref(portletURI.toString() + "\" onclick=\"return GridSphere_popup(this, 'notes')\"");
                }
                stateLinks.add(stateLink);
            } catch (IllegalArgumentException e) {
                // do nothing
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
        PortletResponse res = event.getPortletResponse();
        PortletRequest req = event.getPortletRequest();
        // make modes from supported modes
        Client client = req.getClient();
        List supportedModes = new ArrayList();
        // set portlet modes

        String appID = portletRegistryService.getApplicationPortletID(portletClass);

        ApplicationPortlet appPortlet = portletRegistryService.getApplicationPortlet(appID);
        if (appPortlet != null) {
            ApplicationPortletConfig appConfig = appPortlet.getApplicationPortletConfig();
            if (appConfig != null) {
                // get supported modes from application portlet config
                //supportedModes = sort(appConfig.getSupportedModes());
                supportedModes = appConfig.getSupportedModes(client.getMimeType());
                if (supportedModes.isEmpty()) return null;
            }
        }

        // Unless user is admin they should not see configure mode
        List userRoles = (List)req.getRoles();
        boolean hasConfigurePermission = userRoles.contains(PortletRole.ADMIN.getName());
        List smodes = new ArrayList();
        Mode mode;
        for (i = 0; i < supportedModes.size(); i++) {
            mode = (Mode) supportedModes.get(i);
            if (mode.equals(Mode.CONFIGURE)) {
                if (hasConfigurePermission) {
                    smodes.add(mode);
                }
            } else {
                smodes.add(mode);
            }

            // remove current mode from list
            smodes.remove(portletMode);
        }

        // Localize the portlet mode names
        Locale locale = req.getLocale();

        List portletLinks = new ArrayList();
        for (i = 0; i < smodes.size(); i++) {
            // create a URI for each of the portlet modes
            PortletURI portletURI;
            PortletModeLink modeLink;
            mode = (Mode) smodes.get(i);
            portletURI = res.createURI();
            portletURI.addParameter(this.getComponentIDVar(req), this.componentIDStr);
            //portletURI.addParameter(SportletProperties.PORTLETID, portletClass);
            try {
                modeLink = new PortletModeLink(mode, locale);
                portletURI.addParameter(SportletProperties.PORTLET_MODE, mode.toString());
                modeLink.setHref(portletURI.toString());
                portletLinks.add(modeLink);
            } catch (IllegalArgumentException e) {
                //log.debug("Unable to get mode for : " + mode.toString());
            }
        }

        return portletLinks;
    }

    /**
     * Performs an action on this portlet title bar component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event) {
        super.actionPerformed(event);
        isActive = true;

        PortletRequest req = event.getPortletRequest();

        req.setAttribute(SportletProperties.PORTLETID, portletClass);

        // pop last event off stack
        event.getLastRenderEvent();

        PortletTitleBarEvent titleBarEvent = new PortletTitleBarEventImpl(this, event, COMPONENT_ID);

        //User user = req.getUser();
        //if (!(user instanceof GuestUser)) {
        Principal principal = req.getUserPrincipal();
        if (principal != null) {
            if (titleBarEvent.hasAction()) {

                if (titleBarEvent.hasWindowStateAction()) {

                    PortletResponse res = event.getPortletResponse();

                    // don't set window state if it is floating
                    if (!titleBarEvent.getState().equals(PortletWindow.State.FLOATING)) windowState = titleBarEvent.getState();

                    WindowEvent winEvent = null;

                    // if receive a window state that is not supported do nothing
                    if (!allowedWindowStates.contains(windowState)) return;

                    if (windowState == PortletWindow.State.MAXIMIZED) {
                        winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_MAXIMIZED);
                    } else if (windowState == PortletWindow.State.MINIMIZED) {
                        winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_MINIMIZED);
                    } else if (windowState == PortletWindow.State.RESIZING) {
                        winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_RESTORED);
                    } else if (windowState == PortletWindow.State.CLOSED) {
                        winEvent = new WindowEventImpl(req, WindowEvent.WINDOW_CLOSED);
                    }
                    if (winEvent != null) {
                        try {
                            portletInvoker.windowEvent((String)req.getAttribute(SportletProperties.PORTLETID), winEvent, req, res);
                        } catch (Exception e) {
                            hasError = true;
                            errorMessage += "Failed to invoke window event method of portlet: " + portletClass;
                        }
                    }
                }
                if (titleBarEvent.hasPortletModeAction()) {
                    /*
                    if (titleBarEvent.getMode().equals(Portlet.Mode.CONFIGURE)) {
                        @TODO fix me
                        boolean hasrole = aclService.hasRequiredRole(req, portletClass, true);
                        if (!hasrole) return;

                    }*/
                    previousMode = portletMode;
                    portletMode = titleBarEvent.getMode();
                    //System.err.println("mode = " + portletMode);
                    //System.err.println("prev mode = " + previousMode);
                }
            }
        }

        req.setAttribute(SportletProperties.PORTLET_WINDOW, windowState);
        req.setMode(portletMode);
        req.setAttribute(SportletProperties.PREVIOUS_MODE, previousMode);

        Iterator it = listeners.iterator();
        PortletComponent comp;
        while (it.hasNext()) {
            comp = (PortletComponent) it.next();
            event.addNewRenderEvent(titleBarEvent);
            comp.actionPerformed(event);
        }
    }

    /**
     * Fires a title bar event notification
     *
     * @param event a portlet title bar event
     */
    protected void fireTitleBarEvent(PortletTitleBarEvent event) {
        Iterator it = listeners.iterator();
        PortletTitleBarListener l;
        while (it.hasNext()) {
            l = (PortletTitleBarListener) it.next();
            l.handleTitleBarEvent(event);
        }
    }

    public List getModeLinks() {
        return modeLinks;
    }

    public List getWindowLinks() {
        return windowLinks;
    }

    public void doRender(GridSphereEvent event) {

        hasError = false;

        // title bar: configure, edit, help, title, min, max
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        // get the appropriate title for this client

        Locale locale = req.getLocale();

        Principal principal = req.getUserPrincipal();
        if (principal != null) {
            if (portletClass != null) {
                modeLinks = createModeLinks(event);
                windowLinks = createWindowLinks(event);
            }
        }

        //System.err.println("in title bar render portletclass=" + portletClass + ": setting prev mode= " + previousMode + " cur mode= " + portletMode);

        req.setMode(portletMode);
        req.setAttribute(SportletProperties.PREVIOUS_MODE, previousMode);
        req.setAttribute(SportletProperties.PORTLET_WINDOW, windowState);

        StringBuffer preTitle = titleView.doStart(event, this);
        req.setAttribute(SportletProperties.RENDER_OUTPUT + COMPONENT_ID + ".pre", preTitle.toString());

        StringBuffer postTitle = titleView.doEnd(event, this);
        req.setAttribute(SportletProperties.RENDER_OUTPUT + COMPONENT_ID + ".post", postTitle.toString());

        StringWriter storedWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(storedWriter);
        PortletResponse wrappedResponse = new StoredPortletResponseImpl(res, writer);

        try {
            //System.err.println("invoking  doTitle:" + title);
            portletInvoker.doTitle((String)req.getAttribute(SportletProperties.PORTLETID), req, wrappedResponse);
            //out.println(" (" + portletMode.toString() + ") ");
            title = storedWriter.toString();
        } catch (Exception e) {
            ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
            title = bundle.getString("PORTLET_UNAVAILABLE");
            hasError = true;
            errorMessage = portletClass + " " + title + "!\n"; //"PortletException:" + e.getMessage();
            log.error(portletClass + " is currently unavailable:", e);
        }
    }

    public String getPreBufferedTitle(PortletRequest req) {
        String preTitle = (String)req.getAttribute(SportletProperties.RENDER_OUTPUT + COMPONENT_ID + ".pre");
        req.removeAttribute(SportletProperties.RENDER_OUTPUT + COMPONENT_ID + ".pre");
        return preTitle;
    }

    public String getPostBufferedTitle(PortletRequest req) {
        String postTitle = (String)req.getAttribute(SportletProperties.RENDER_OUTPUT + COMPONENT_ID + ".post");
        req.removeAttribute(SportletProperties.RENDER_OUTPUT + COMPONENT_ID + ".post");
        return postTitle;
    }


    public Object clone() throws CloneNotSupportedException {
        PortletTitleBar t = (PortletTitleBar) super.clone();
        t.title = this.title;
        t.portletClass = this.portletClass;
        t.portletMode = Mode.toMode(this.portletMode.toString());
        t.windowState = PortletWindow.State.toState(this.windowState.toString());
        t.previousMode = this.previousMode;
        t.errorMessage = this.errorMessage;
        t.hasError = this.hasError;
        t.allowedWindowStates = new ArrayList(this.allowedWindowStates.size());
        for (int i = 0; i < this.allowedWindowStates.size(); i++) {
            PortletWindow.State state = (PortletWindow.State) allowedWindowStates.get(i);
            t.allowedWindowStates.add(state.clone());
        }
        return t;

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());

        return sb.toString();
    }

}

