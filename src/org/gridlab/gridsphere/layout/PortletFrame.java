/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletWindow;
import org.gridlab.gridsphere.portlet.impl.SportletMode;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;
import org.gridlab.gridsphere.event.WindowListener;
import org.gridlab.gridsphere.layout.impl.PortletFrameEventImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class PortletFrame extends BasePortletComponent implements PortletTitleBarListener {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFrame.class);

    protected String name = PortletFrame.class.getName();

    // renderPortlet is true in doView and false on minimized
    private boolean renderPortlet = true;

    private String componentIDStr = null;

    private PortletWindow portletWindow = null;

    private PortletTitleBar titleBar = new PortletTitleBar();
    private List listeners = new ArrayList();

    public PortletFrame() {}

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

    public String getClassName() {
        return PortletFrame.class.getName();
    }

    public List init(List list) {
        list = super.init(list);
        componentIDStr = String.valueOf(COMPONENT_ID);
        list.add((PortletComponent)titleBar);
        list = titleBar.init(list);
        titleBar.addTitleBarListener(this);
        return list;
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
        req.setAttribute(GridSphereProperties.COMPONENT_ID, componentIDStr);
        UserPortletManager userPortletManager = UserPortletManager.getInstance();

        // now perform actionPerformed on Portlet if it has an action
        String actionStr = req.getParameter(GridSphereProperties.ACTION);
        if (actionStr != null) {
            DefaultPortletAction action = new DefaultPortletAction(actionStr);
            try {
            userPortletManager.actionPerformed(titleBar.getPortletClass(), action, req, res);
            } catch (PortletException e) {
                System.err.println("titleBar.getPortletClass()= " + titleBar.getPortletClass() + "  " + actionStr);
            }
        }

    }


    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
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
        if ((error != null) && (error.getPortletID() == titleBar.getPortletClass())) {
            out.println("<b>Error!</b>");
            out.println(error.getMessage());
        } else {
            if (renderPortlet) {
                PortletRegistryManager registryManager = PortletRegistryManager.getInstance();
                UserPortletManager userPortletManager = UserPortletManager.getInstance();
                try {
                    userPortletManager.service(titleBar.getPortletClass(), req, res);
                } catch (PortletException e) {
                    out.println(e.getMessage());
                }
            }
        }
        out.println("</tr></table></td></tr></table></td></tr></table>");

    }

}
