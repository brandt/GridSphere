/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.impl.PortletFrameEventImpl;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletWindow;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.PortletErrorMessage;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.user.UserManagerService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PortletFrame extends BasePortletComponent implements PortletTitleBarListener {

    // renderPortlet is true in doView and false on minimized
    private boolean renderPortlet = true;

    private String componentIDStr = null;

    private PortletWindow portletWindow = null;
    private String portletClass = null;
    private PortletTitleBar titleBar = new PortletTitleBar();
    private List listeners = new ArrayList();

    public PortletFrame() {}

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getPortletClass() {
        return portletClass;
    }

    public void setPortletTitleBar(PortletTitleBar titleBar) {
        this.titleBar = titleBar;
    }

    public PortletTitleBar getPortletTitleBar() {
        return titleBar;
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

    public List init(List list) {
        list = super.init(list);
        if (titleBar != null) {
            ComponentIdentifier compId = new ComponentIdentifier();
            compId.setPortletLifecycle(titleBar);
            compId.setComponentID(COMPONENT_ID);
            componentIDStr = String.valueOf(COMPONENT_ID);
            compId.setPortletClass(portletClass);
            compId.setClassName(titleBar.getClass().getName());
            if (portletClass != null) {
                titleBar.setPortletClass(portletClass);
                titleBar.addTitleBarListener(this);
            }
            list.add(compId);
            list = titleBar.init(list);
        }
        return list;
    }

    public void login(GridSphereEvent event) {
        UserPortletManager userPortletManager = event.getUserPortletManager();
        //userPortletManager.initUserPortlet(portletClass, event.getSportletRequest(), event.getSportletResponse());
    }

    public void logout(GridSphereEvent event) {
        UserPortletManager userPortletManager = event.getUserPortletManager();
        //userPortletManager.destroyUserPortlet(portletClass, event.getSportletRequest(), event.getSportletResponse());
    }

    public void addFrameListener(PortletFrameListener listener) {
        listeners.add(listener);
    }

    protected void fireFrameEvent(PortletFrameEvent event) throws PortletLayoutException {
        Iterator it = listeners.iterator();
        PortletFrameListener l;
        while (it.hasNext()) {
            l = (PortletFrameListener)it.next();
            l.handleFrameEvent(event);
        }
    }

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     */
    public void handleTitleBarEvent(PortletTitleBarEvent event) throws PortletLayoutException {

        if (event.getAction() == PortletTitleBarEvent.Action.MODE_MODIFY) {
            //previousMode = portletMode;
            //portletMode = event.getMode();
        }
        if (event.getAction() == PortletTitleBarEvent.Action.WINDOW_MODIFY) {

            PortletWindow.State state = event.getState();
            PortletFrameEvent evt = null;
            if (state == PortletWindow.State.MINIMIZED) {
                renderPortlet = false;
                evt = new PortletFrameEventImpl(PortletFrameEvent.Action.FRAME_MINIMIZED, COMPONENT_ID);
            } else if (state == PortletWindow.State.RESIZING) {
                renderPortlet = true;
                evt = new PortletFrameEventImpl(PortletFrameEvent.Action.FRAME_RESIZED, COMPONENT_ID);
            } else if (state == PortletWindow.State.MAXIMIZED) {
                renderPortlet = true;
                evt = new PortletFrameEventImpl(PortletFrameEvent.Action.FRAME_MAXIMIZED, COMPONENT_ID);
            }
            fireFrameEvent(evt);
        }
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {

        // process events
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
        PortletContext ctx = event.getPortletContext();

        req.setAttribute(GridSphereProperties.PORTLETID, portletClass);
        req.setAttribute(GridSphereProperties.COMPONENT_ID, componentIDStr);

        // Set the portlet data
        PortletData data = null;
        try {
            UserManagerService userManager = (UserManagerService)ctx.getService(UserManagerService.class);
            data = userManager.getPortletData(req.getUser(), portletClass);
        } catch (PortletServiceException e) {}
        req.setData(data);

        // now perform actionPerformed on Portlet if it has an action
        String actionStr = req.getParameter(GridSphereProperties.ACTION);
        if (actionStr != null) {
            DefaultPortletAction action = new DefaultPortletAction(actionStr);
            try {
                UserPortletManager userPortletManager = event.getUserPortletManager();
                userPortletManager.actionPerformed(portletClass, action, req, res);
            } catch (PortletException e) {
                System.err.println("titleBar.getPortletClass()= " + portletClass + "  " + actionStr);
            }
        }

    }


    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
        req.setAttribute(GridSphereProperties.PORTLETID, portletClass);
        req.setAttribute(GridSphereProperties.COMPONENT_ID, componentIDStr);

        ///// begin portlet frame
        PrintWriter out = res.getWriter();
        out.println("<table width=\"" + width + "%\"  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\"><tr><td>");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#999999\">");
        out.println("<tr><td width=\"100%\">");

        titleBar.doRender(event);

        out.println("</td></tr>");
        out.println("<tr><td valign=\"top\" align=\"left\"><table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" bgcolor=");
        out.println("\"" + bgColor + "\"<tr><td width=\"25%\" valign=\"center\">");

        PortletErrorMessage error = (PortletErrorMessage)req.getAttribute(GridSphereProperties.PORTLETERROR);
        if ((error != null) && (error.getPortletID() == portletClass)) {
            out.println("<b>Error!</b>");
            out.println(error.getMessage());
        } else {
            if (renderPortlet) {
                UserPortletManager userPortletManager = event.getUserPortletManager();
                try {
                    userPortletManager.service(portletClass, req, res);
                } catch (PortletException e) {
                    out.println(e.getMessage());
                }
            }
        }
        out.println("</tr></table></td></tr></table></td></tr></table>");

    }

}
