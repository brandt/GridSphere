package org.gridsphere.portlets.core.about;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

public class AboutPortlet extends ActionPortlet {

    private String deployedPath = "";

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        DEFAULT_VIEW_PAGE = "doView";
        deployedPath = config.getPortletContext().getRealPath("");
    }

    public void doView(RenderFormEvent event) throws PortletException {
        String release = SportletProperties.getInstance().getProperty("gridsphere.release");
        event.getRenderRequest().setAttribute("version", release);
        event.getRenderRequest().setAttribute("path", deployedPath);
        setNextState(event.getRenderRequest(), "about/view.jsp");
    }

}
