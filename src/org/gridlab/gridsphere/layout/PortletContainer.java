/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.PortletEventDispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PortletContainer {

    protected int COMPONENT_ID = 0;

    // The actual portlet layout components
    protected List components = new ArrayList();

    // The component ID's of each of the layout components
    protected List componentIdentifiers = new ArrayList();

    // The list of portlets a user has-- generally contained within a PortletFrame/PortletTitleBar combo
    protected List portlets = new ArrayList();

    protected String name = "";
    protected String uiTheme = "xp";

    public PortletContainer() {
    }

    public void setContainerName(String name) {
        this.name = name;
    }

    public String getContainerName() {
        return name;
    }

    public List init(List list) {
        Iterator it = components.iterator();
        PortletComponent comp;
        while (it.hasNext()) {
            comp = (PortletComponent) it.next();
            comp.setTheme(uiTheme);
            list = comp.init(list);
        }
        System.err.println("Made a components list!!!! " + list.size());
        for (int i = 0; i < list.size(); i++) {
            ComponentIdentifier c = (ComponentIdentifier) list.get(i);
            System.err.println("id: " + c.getComponentID() + " : " + c.getClassName() + " : " + c.hasPortlet());

        }
        componentIdentifiers = list;
        return componentIdentifiers;
    }

    public void loginPortlets(GridSphereEvent event) throws PortletException {
        PortletEventDispatcher dispatcher = event.getPortletEventDispatcher();
        Iterator it = componentIdentifiers.iterator();
        ComponentIdentifier cid = null;
        PortletFrame f = null;
        while (it.hasNext()) {
            cid = (ComponentIdentifier) it.next();
            System.err.println(cid.getClassName());
            if (cid.getClassName().equals("org.gridlab.gridsphere.layout.PortletFrame")) {
                f = (PortletFrame) cid.getPortletComponent();
                portlets.add(f.getPortletClass());
                dispatcher.portletLogin(f.getPortletClass());
            }
        }
    }

    public void logoutPortlets(GridSphereEvent event) throws PortletException {
        PortletEventDispatcher dispatcher = event.getPortletEventDispatcher();
        Iterator it = componentIdentifiers.iterator();
        ComponentIdentifier cid = null;
        PortletFrame f = null;
        while (it.hasNext()) {
            cid = (ComponentIdentifier) it.next();
            if (cid.getPortletClass().equals("org.gridlab.gridsphere.layout.PortletFrame")) {
                f = (PortletFrame) cid.getPortletComponent();
                dispatcher.portletLogout(f.getPortletClass());
            }
        }
    }

    public void destroy() {
        Iterator it = components.iterator();
        while (it.hasNext()) {
            PortletComponent comp = (PortletComponent) it.next();
            comp.destroy();
        }
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        // if there is a layout action do it!
        if (event.getPortletComponentID() != -1) {
            // off by one calculations for array indexing (because all component id's are .size() which is
            // one more than we use to index the components
            ComponentIdentifier compId = (ComponentIdentifier) componentIdentifiers.get(event.getPortletComponentID());
            PortletComponent comp = compId.getPortletComponent();
            if (comp != null) {
                comp.actionPerformed(event);
            }
        }
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("  <title>" + name + "</title>");
        out.println("  <link type=\"text/css\" href=\"themes/" + uiTheme + "/css" +
                "/default.css\" rel=\"STYLESHEET\"/>");
        out.println("<script language=\"JavaScript\" src=\"javascript/gridsphere.js\"></script>");
        out.println("</head>\n<body>");

        Iterator it = components.iterator();
        while (it.hasNext()) {
            PortletComponent comp = (PortletComponent) it.next();
            comp.doRender(event);
        }
        out.println("</body></html>");
    }

    public void setTheme(String theme) {
        this.uiTheme = theme;
    }

    public String getTheme() {
        return uiTheme;
    }

    public void setPortletComponents(ArrayList components) {
        this.components = components;
    }

    public List getPortletComponents() {
        return components;
    }

    public List getComponentIdentifierList() {
        return componentIdentifiers;
    }

    public void setComponentIdentifierList(List ComponentIdentifiers) {
        this.componentIdentifiers = ComponentIdentifiers;
    }

    public int getComponentID() {
        return COMPONENT_ID;
    }

}
