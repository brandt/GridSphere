/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletURI;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;
import org.gridlab.gridsphere.portletcontainer.descriptor.AllowsWindowStates;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

public class PortletBorder implements PortletRender {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletBorder.class);


    private String borderColor = "#336699";
    private String thickness = "1";

    public PortletBorder() {}

    public void setBorderColor(String color) {
        this.borderColor = color;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {

    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {

    }

}
