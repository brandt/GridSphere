/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PortletContainer implements PortletLifecycle {

    protected int COMPONENT_ID = 0;

    // The actual portlet layout components
    protected List components = new ArrayList();

    // The component ID's of each of the layout components
    protected List ComponentIdentifiers = new ArrayList();

    // The list of portlets a user has-- generally contained within a PortletFrame/PortletTitleBar combo
    protected List portlets = new ArrayList();

    protected LayoutManager layoutManager;
    protected String name = "";
    protected String uiTheme = "xp";

    public PortletContainer() {}

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
            comp = (PortletComponent)it.next();
            comp.setTheme(uiTheme);
            list = comp.init(list);
        }
        System.err.println("Made a components list!!!! " + list.size());
        for (int i = 0; i < list.size(); i++) {
            ComponentIdentifier c = (ComponentIdentifier)list.get(i);
            System.err.println("id: " + c.getComponentID() + " : " + c.getClassName() +  " : " + c.hasPortlet());

        }
        ComponentIdentifiers = list;
        return ComponentIdentifiers;
    }

    public void login(GridSphereEvent event) {
        Iterator it = components.iterator();
        PortletLifecycle cycle;
        while (it.hasNext()) {
            cycle = (PortletLifecycle)it.next();
            cycle.login(event);
        }
    }

    public void logout(GridSphereEvent event) {
        Iterator it = components.iterator();
        PortletLifecycle cycle;
        while (it.hasNext()) {
            cycle = (PortletLifecycle)it.next();
            cycle.logout(event);
        }
    }

    public void destroy() {
        Iterator it = components.iterator();
        while (it.hasNext()) {
            PortletComponent comp = (PortletComponent)it.next();
            comp.destroy();
        }
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {

        // if there is a layout action do it!
        if (event.hasAction()) {
            // off by one calculations for array indexing (because all component id's are .size() which is
            // one more than we use to index the components
            ComponentIdentifier compId = (ComponentIdentifier)ComponentIdentifiers.get(event.getPortletComponentID());
            System.err.println("handlingaction in " + event.getPortletComponentID() + compId.getClassName());
            PortletLifecycle l = compId.getPortletLifecycle();
            if (l != null) {
                l.actionPerformed(event);
            }
        }
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("  <title>" + name + "</title>");
        out.println("  <link type=\"text/css\" href=\"themes/"+uiTheme+"/css"+
         "/default.css\" rel=\"STYLESHEET\"/>");
        out.println("</head>\n<body>");

        // for css title
        out.println("<div id=\"page-logo\">" + name + "</div>");
        out.println("<div id=\"page-tagline\">Bigger. Better. Faster. More.</div>");

        Iterator it = components.iterator();
        while (it.hasNext()) {
            PortletRender action = (PortletRender)it.next();
            action.doRender(event);
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
        return ComponentIdentifiers;
    }

    public void setComponentIdentifierList(List ComponentIdentifiers) {
        this.ComponentIdentifiers = ComponentIdentifiers;
    }

    public int getComponentID() {
        return COMPONENT_ID;
    }
}
