/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

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
    protected List portletComponents = new ArrayList();

    // The list of portlets a user has-- generally contained within a PortletFrame/PortletTitleBar combo
    protected List portlets = new ArrayList();

    protected LayoutManager layoutManager;
    protected String name = "";

    public PortletContainer() {}

    public void add(PortletComponent comp) {
        components.add(comp);
    }

    public void add(PortletComponent comp, int index) {
        components.add(index, comp);
    }

    public void setContainerName(String name) {
        this.name = name;
    }

    public String getContainerName() {
        return name;
    }

    public List init(List list) {
        Iterator it = components.iterator();
        PortletLifecycle cycle;
        ComponentIdentifier compId;
        while (it.hasNext()) {
            compId = new ComponentIdentifier();
            cycle = (PortletLifecycle)it.next();
            compId.setPortletLifecycle(cycle);
            compId.setClassName(cycle.getClass().getName());
            compId.setComponentID(list.size());
            list.add(compId);
            portletComponents = cycle.init(list);
        }

        System.err.println("Made a components list!!!! " + portletComponents.size());
        for (int i = 0; i < portletComponents.size(); i++) {
            ComponentIdentifier c = (ComponentIdentifier)portletComponents.get(i);
            System.err.println(c.getComponentID() + " : " + c.getClassName());
        }
        return portletComponents;
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
            ComponentIdentifier compId = (ComponentIdentifier)portletComponents.get(event.getPortletComponentID() - 1);
            PortletLifecycle l = compId.getPortletLifecycle();
            if (l != null) {
                l.actionPerformed(event);
            }
        }
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();
        out.println("<html><head><meta HTTP-EQUIV=\"content-type\" CONTENT=\"text/html; charset=ISO-8859-1\">");
        out.println("<title>" + name + "</title>");
        Iterator it = components.iterator();
        while (it.hasNext()) {
            PortletRender action = (PortletRender)it.next();
            action.doRender(event);
        }
        out.println("</html>");
    }

    public PortletComponent getPortletComponent(int n) {
        return (PortletComponent)components.get(n);
    }

    public int getPortletComponentCount() {
        return components.size();
    }

    public void setPortletComponents(ArrayList components) {
        this.components = components;
    }

    public List getPortletComponents() {
        return components;
    }

    public void remove(int index) {
        components.remove(index);
    }

    public void remove(PortletComponent comp) {
        components.remove(comp);
    }

    public void removeAll() {
        for (int i = 0; i < components.size(); i++) {
            components.remove(i);
        }
    }

    public LayoutManager getLayout() {
        return layoutManager;
    }

    public void setLayout(LayoutManager mgr) {
        layoutManager = mgr;
    }

    public int getComponentID() {
        return COMPONENT_ID;
    }
}
