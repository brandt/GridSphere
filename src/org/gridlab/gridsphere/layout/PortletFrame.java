/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.event.PortletFrameEvent;
import org.gridlab.gridsphere.layout.event.PortletFrameListener;
import org.gridlab.gridsphere.layout.event.PortletTitleBarEvent;
import org.gridlab.gridsphere.layout.event.PortletTitleBarListener;
import org.gridlab.gridsphere.layout.event.impl.PortletFrameEventImpl;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.impl.SportletDataManager;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <code>PortletFrame</code> provides the visual representation of a portlet. A portlet frame
 * contains a portlet title bar unless visible is set to false.
 */
public class PortletFrame extends BasePortletComponent implements PortletTitleBarListener {

    // renderPortlet is true in doView and false on minimized
    private boolean renderPortlet = true;

    private String portletClass = null;
    private PortletTitleBar titleBar = null;
    private List listeners = new ArrayList();
    private PortletErrorMessage errorFrame = new PortletErrorMessage();
    private boolean transparent = false;
    private String innerPadding = "";
    private String outerPadding = "";

    private PortletDataManager dataManager = SportletDataManager.getInstance();

    // Playing with the idea of a portlet error frame to abstract error display
    class PortletErrorMessage {

        private String id = "";
        private Exception e = null;
        private String msg = "";

        public PortletErrorMessage() {

        }

        public boolean hasMessage() {
            return (msg.equals("") ? false: true);
        }

        public String getPortletID() {
            return id;
        }

        public void setPortletID(String portletID) {
            this.id = portletID;
        }

        public String getMessage() {
            return msg;
        }

        public void setMessage(String msg) {
            this.msg += msg;
        }

        public void setMessage(String msg, Exception e) {
            this.msg += msg;
            setException(e);
        }

        public Exception getException() {
            return e;
        }

        public void setException(Exception e) {
            this.msg += e.getMessage();
        }

    }

    /**
     * Constructs an instance of PortletFrame
     */
    public PortletFrame() {
    }

    /**
     * Sets the portlet class contained by this portlet frame
     *
     * @param portletClass the fully qualified portlet classname
     */
    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    /**
     * Returns the portlet class contained by this portlet frame
     *
     * @return the fully qualified portlet classname
     */
    public String getPortletClass() {
        return portletClass;
    }

    /**
     * Sets the inner padding of the portlet frame
     *
     * @param innerPadding the inner padding
     */
    public void setInnerPadding(String innerPadding) {
        this.innerPadding = innerPadding;
    }

    /**
     * Returns the inner padding of the portlet frame
     *
     * @return the inner padding
     */
    public String getInnerPadding() {
        return innerPadding;
    }

    /**
     * Sets the outer padding of the portlet frame
     *
     * @param outerPadding the outer padding
     */
    public void setOuterPadding(String outerPadding) {
        this.outerPadding = outerPadding;
    }

    /**
     * Returns the outer padding of the portlet frame
     *
     * @return the outer padding
     */
    public String getOuterPadding() {
        return outerPadding;
    }

    /**
     * If set to <code>true</code> the portlet is rendered transparently without a
     * defining border and title bar. This is used for example for the LogoutPortlet
     *
     * @param transparent if set to <code>true</code>, portlet frame is displayed transparently, <code>false</code> otherwise
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    /**
     * If set to <code>true</code> the portlet is rendered transparently without a
     * defining border and title bar. This is used for example for the LogoutPortlet
     *
     * @return <code>true</code> if the portlet frame is displayed transparently, <code>false</code> otherwise
     */
    public boolean getTransparent() {
        return this.transparent;
    }

    /**
     * Initializes the portlet frame component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a <code>List</code> of component identifiers
     * @return a <code>List</code> of updated component identifiers
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
        // if the portlet frame is transparent then it doesn't get a title bar
        if (transparent == false) titleBar = new PortletTitleBar();
        if (titleBar != null) {
            titleBar.setPortletClass(portletClass);
            list = titleBar.init(list);
            titleBar.addTitleBarListener(this);
        }
        return list;
    }

    /**
     * Adds a portlet frame listener to be notified of portlet frame events
     *
     * @param listener a portlet frame listener
     * @see PortletFrameEvent
     */
    public void addFrameListener(PortletFrameListener listener) {
        listeners.add(listener);
    }

    /**
     * Fires a frame event notification
     *
     * @param event a portlet frame event
     * @throws PortletLayoutException if a layout error occurs
     */
    protected void fireFrameEvent(PortletFrameEvent event) throws PortletLayoutException {
        Iterator it = listeners.iterator();
        PortletFrameListener l;
        while (it.hasNext()) {
            l = (PortletFrameListener) it.next();
            l.handleFrameEvent(event);
        }
    }

    /**
     * Notifies this listener that a portlet title barw event has occured
     *
     * @param event the portolet title bar event
     * @throws PortletLayoutException if a portlet layout exception occurs during processing
     */
    public void handleTitleBarEvent(PortletTitleBarEvent event) throws PortletLayoutException {
        if (event.getAction() == PortletTitleBarEvent.Action.WINDOW_MODIFY) {
            PortletWindow.State state = event.getState();
            PortletFrameEvent evt = null;
            if (state == PortletWindow.State.MINIMIZED) {
                renderPortlet = false;
                evt = new PortletFrameEventImpl(PortletFrameEvent.Action.FRAME_MINIMIZED, COMPONENT_ID);
            } else if (state == PortletWindow.State.RESIZING) {
                renderPortlet = true;
                evt = new PortletFrameEventImpl(PortletFrameEvent.Action.FRAME_RESTORED, COMPONENT_ID);
            } else if (state == PortletWindow.State.MAXIMIZED) {
                renderPortlet = true;
                evt = new PortletFrameEventImpl(PortletFrameEvent.Action.FRAME_MAXIMIZED, COMPONENT_ID);
            }
            fireFrameEvent(evt);
        }
    }

    /**
     * Performs an action on this portlet frame component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.actionPerformed(event);
        System.err.println("in action Performed in PortletFrame");
        // process events
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        req.setAttribute(GridSphereProperties.PORTLETID, portletClass);

        String newmode = req.getParameter(GridSphereProperties.PORTLETMODE);
        if (newmode != null) {
            req.setMode(Portlet.Mode.toMode(newmode));
        } else {
            if (titleBar != null) {
                String mode = titleBar.getPortletMode();
                req.setMode(Portlet.Mode.toMode(mode));
            } else {
                req.setMode(Portlet.Mode.VIEW);
            }
        }

        // Set the portlet data
        User user = req.getUser();
        PortletData data = null;
        if (!(user instanceof GuestUser)) {
            try {
                data = dataManager.getPortletData(req.getUser(), portletClass);
                req.setAttribute(GridSphereProperties.PORTLETDATA, data);
            } catch (PersistenceManagerException e) {
                errorFrame.setMessage("Unable to retrieve user's portlet data", e);
            }
        }


        // now perform actionPerformed on Portlet if it has an action
        DefaultPortletAction action = event.getAction();
        if (action.getName() != "") {
            try {
                PortletInvoker.actionPerformed(portletClass, action, req, res);
            } catch (PortletException e) {
                errorFrame.setMessage("Unable to perform action", e);
            }
        }
        // in case portlet mode got reset
        if (titleBar != null) titleBar.setPortletMode(req.getMode().toString());
    }

    /**
     * Renders the portlet frame component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        req.setAttribute(GridSphereProperties.PORTLETID, portletClass);

        // Set the portlet data
        User user = req.getUser();
        PortletData data = null;
        if (!(user instanceof GuestUser)) {
            try {
                data = dataManager.getPortletData(req.getUser(), portletClass);
                req.setAttribute(GridSphereProperties.PORTLETDATA, data);
            } catch (PersistenceManagerException e) {
                errorFrame.setMessage("Unable to retrieve user's portlet data", e);
            }
        }

        ///// begin portlet frame
        PrintWriter out = res.getWriter();

        out.println("<!-- PORTLET STARTS HERE -->");
        //out.println("<div class=\"window-main\">");
        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");        // this is the main table around one portlet

        // Render title bar
        if (titleBar != null) {
            titleBar.doRender(event);
            if (titleBar.hasRenderError()) {
                errorFrame.setMessage(titleBar.getErrorMessage());
            }
        }
        if (renderPortlet) {
            if (!transparent) {
                out.println("<tr><td class=\"window-content\">");      // now the portlet content begins
            } else {
                out.println("<tr><td>");
            }

            try {
                PortletInvoker.service(portletClass, req, res);
            } catch (PortletException e) {
                errorFrame.setMessage("Unable to invoke service method", e);
            }

            if (errorFrame.hasMessage()) {
                out.println(errorFrame.getMessage());
            }

            out.println("</td></tr>");
        } else {
            out.println("<tr><td class=\"window-content-minimize\">");      // now the portlet content begins
            out.println("</td></tr>");
        }


        out.println("</table>");
        out.println("<!--- PORTLET ENDS HERE -->");
    }

}
