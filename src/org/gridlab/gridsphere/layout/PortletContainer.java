/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

public class PortletContainer implements PortletLifecycle {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletContainer.class);

    protected List components = new Vector();
    protected List portletComponents = new ArrayList();
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
        log.info("in init()");
        Iterator it = components.iterator();
        PortletLifecycle cycle;
        while (it.hasNext()) {
            cycle = (PortletLifecycle)it.next();
            list.add(cycle);
            portletComponents = cycle.init(list);
        }

        System.err.println("Made a components list!!!! " + portletComponents.size());
        for (int i = 0; i < portletComponents.size(); i++) {
            PortletLifecycle c = (PortletLifecycle)portletComponents.get(i);
            System.err.println(c.getClass().getName());
        }
        return portletComponents;
    }

    public void login() {
        log.info("in login()");
        Iterator it = components.iterator();
        PortletLifecycle cycle;
        while (it.hasNext()) {
            cycle = (PortletLifecycle)it.next();
            cycle.login();
        }
    }

    public void logout() {
        log.info("in logout()");
        Iterator it = components.iterator();
        PortletLifecycle cycle;
        while (it.hasNext()) {
            cycle = (PortletLifecycle)it.next();
            cycle.logout();
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
            System.err.println("In PORTLET CONTAINER: ACTION = " + event.getAction().toString());
            System.err.println("In PORTLET CONTAINER: ID = " + event.getPortletComponentID());
            PortletLifecycle l = (PortletLifecycle)portletComponents.get(event.getPortletComponentID() - 1);

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

    public void setPortletComponents(Vector components) {
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

}
