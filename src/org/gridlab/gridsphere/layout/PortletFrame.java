/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;

import java.io.PrintWriter;
import java.io.IOException;

public class PortletFrame extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFrame.class);

    private String portletClass;

    public PortletFrame() {}

    public void setConcretePortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getConcretePortletClass() {
        return portletClass;
    }

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        super.doRender(ctx, req, res);
        log.debug("in doRender()");
        try {
            PortletRegistryService registryService =
                (PortletRegistryService) ctx.getService(PortletRegistryService.class);

            // To alter the window and mode properties of the active portlet
            PortletWindow window = req.getWindow();
            Portlet.Mode mode = req.getMode();

            if (border == null) border = new PortletBorder();

            // need to invoke the doView or whatever of the portlet class
            RegisteredPortlet registeredPortlet = registryService.getRegisteredPortlet(portletClass);
            AbstractPortlet abstractPortlet = registeredPortlet.getActivePortlet();
            PortletSettings settings = registeredPortlet.getPortletSettings();

            // set the portlet frame title
            String title = settings.getTitle(req.getLocale(), req.getClient());
            border.setTitle(title);

            // render portlet frame
            ///// begin portlet frame
            PrintWriter out = res.getWriter();
            out.println("<table width=\"90%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\"><tr><td>");

            border.doRender(ctx, req, res);

            out.println("<tr><td valign=\"top\" align=\"left\"><table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" bgcolor=");
            out.println("\"" + bgColor + "\"<tr><td width=\"25%\" valign=\"center\">");


            if (abstractPortlet != null)
                abstractPortlet.service(req, res);

            out.println("</tr></table></td></tr></table></td></tr></table>");
            ///// end portlet frame

        } catch (PortletServiceUnavailableException e) {
            throw new PortletLayoutException("Unable to get portlet instance");
        } catch (PortletException e) {
            throw new PortletLayoutException("Failed invoking portlet service method");
        }
    }
}
