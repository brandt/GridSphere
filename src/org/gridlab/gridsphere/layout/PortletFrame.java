/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;

import java.io.IOException;
import java.io.PrintWriter;

public class PortletFrame extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFrame.class);

    private String portletClass;
    private String windowState;

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

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        super.doRender(ctx, req, res);
        log.debug("in doRender()");
        PortletRegistryService registryService = null;

        try {
            registryService = (PortletRegistryService) ctx.getService(PortletRegistryService.class);
        } catch (PortletServiceException e) {
            log.error("Failed to get registry instance in PortletFrame: ", e);
            throw new PortletLayoutException("Unable to get portlet instance");
        }
        // To alter the window and mode properties of the active portlet
        //PortletWindow window = req.getWindow();
        //Portlet.Mode mode = req.getMode();


        if (border == null) border = new PortletBorder();

        // need to invoke the doView or whatever of the portlet class
        DefaultPortletAction layoutAction = new DefaultPortletAction("layout");

        //props.setPortletID(portletClass);
        System.err.println("before creating URI ");
        PortletURI windowURI = res.createURI(PortletWindow.NORMAL);
        windowURI.addAction(layoutAction);

        String windowLink = windowURI.toString();

        layoutAction.addParameter("layoutproperties", "");
        System.err.println("contacting regiostry for portlet: " + portletClass);
        RegisteredPortlet registeredPortlet = registryService.getRegisteredPortlet(portletClass);
        AbstractPortlet abstractPortlet = registeredPortlet.getActivePortlet();
        System.err.println("now I have an abstract portlet");
        PortletSettings settings = registeredPortlet.getPortletSettings(false);
        System.err.println("after portlet settings: ");
        // set the portlet frame title
        String title = settings.getTitle(req.getLocale(), req.getClient());
        border.setTitle(title);

        // render portlet frame
        ///// begin portlet frame
        PrintWriter out = res.getWriter();
        out.println("<table width=\"" + width + "%\"  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\"><tr><td>");
        System.err.println("before borderin PortletFrame");
        border.doRender(ctx, req, res);

        out.println("<tr><td valign=\"top\" align=\"left\"><table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" bgcolor=");
        out.println("\"" + bgColor + "\"<tr><td width=\"25%\" valign=\"center\">");

        System.err.println("in PortletFrame");

        try {
            if (abstractPortlet != null) {
                abstractPortlet.service(req, res);
            }
        } catch (PortletException e) {
            log.error("Failed invoking portlet service method: ", e);
            throw new PortletLayoutException("Failed invoking portlet service method");
        }
        out.println("</tr></table></td></tr></table></td></tr></table>");
        ///// end portlet frame


    }
}
