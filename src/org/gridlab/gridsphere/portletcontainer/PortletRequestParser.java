/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portlet.impl.ClientImpl;
import org.gridlab.gridsphere.portletcontainer.descriptor.Markup;
import org.gridlab.gridsphere.portletcontainer.descriptor.SupportsModes;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * The DefaultPortletAction is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public final class PortletRequestParser implements PortletAction {

    private String concreteID;
    private User user = null;
    private Client client = null;
    private HttpServletRequest req = null;

    /**
     * Constructs a GridSpherePortletAction from the PortletRequest
     */
    public PortletRequestParser(HttpServletRequest req) {
        this.req = req;
        concreteID = req.getParameter(GridSphereProperties.PORTLETID);
        user = (User)req.getSession().getAttribute(GridSphereProperties.USER);
        if (user == null) user = GuestUser.getInstance();

        //Portlet.Mode previousMode = (Portlet.Mode)req.getParameter(GridSphereProperties.PORTLETMODE);
            //Portlet.Mode mode = Portlet.Mode.VIEW;
            //req.setAttribute(GridSphereProperties.PORTLETMODE, mode);

        client = new ClientImpl(req);
        req.setAttribute(GridSphereProperties.CLIENT, client);

        if (concreteID != null) {
            PortletRegistryManager registry = PortletRegistryManager.getInstance();
            String appID = registry.getApplicationPortletID(concreteID);
            ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concreteID);

            // set portlet settings
            PortletSettings settings = concPortlet.getSportletSettings();
            req.setAttribute(GridSphereProperties.PORTLETSETTINGS, settings);

            // set portlet mode
            SupportsModes supportedModes = appPortlet.getPortletApplicationDescriptor().getSupportsModes();
            List markups = supportedModes.getMarkupList();
            Iterator it = markups.iterator();
            while (it.hasNext()) {
                Markup m = (Markup)it.next();
                String[] modes = m.getPortletModesAsStrings();
                System.err.println(m.getName());
                for (int i = 0; i < modes.length; i++) {
                    System.err.println(modes[i]);
                }
            }
        }
    }

    public boolean hasAction() {
        if (getName() != null)
            return true;
        return false;
    }

    public String getConcretePortletID() {
        return concreteID;
    }

    public PortletSettings getPortletSettings() {
        return (PortletSettings)req.getAttribute(GridSphereProperties.PORTLETSETTINGS);
    }

    public Portlet.Mode getPortletMode() {
        return (Portlet.Mode)req.getAttribute(GridSphereProperties.PORTLETMODE);
    }

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    /**
     * Returns the name of this action
     *
     * @return the name of this action
     */
    public String getName() {
        return req.getParameter(GridSphereProperties.ACTION);
    }

    /**
     * Returns the constructed application portlet action.
     * Reconstructs the PortletAction attributes store from request names (painful!)
     */
    public DefaultPortletAction getPortletAction() {
        // Here is some code for handling actions
        DefaultPortletAction a = new DefaultPortletAction(getName());
        /* Need to reclaim parameter values
        Enumeration enum = req.getParameterNames();
        String action = getName();
        String value = null;
        int index = action.length() + 1;
        String name = null;
        String newname = null;
        while (enum.hasMoreElements()) {
            name = (String)enum.nextElement();
            if (name.startsWith(action)) {
                newname = name.substring(index, name.length());
                value = req.getParameter(name);
                a.addParameter(newname, value);
            }
        }
        */
        return a;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GridSpherePortletAction");
        sb.append(" Action Name: " + getName());
        sb.append(" Concrete Portlet ID: " + getConcretePortletID());
        return sb.toString();
    }
}
