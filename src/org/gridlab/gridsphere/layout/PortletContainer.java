/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class PortletContainer implements LayoutActionListener {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletContainer.class);

    protected List components = new Vector();

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

    public void doLayoutAction(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        // if there is a layout action do it!
        Iterator it = components.iterator();
        while (it.hasNext()) {
            LayoutActionListener action = (LayoutActionListener)it.next();
            action.doLayoutAction(ctx, req, res);
        }
    }


    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        PrintWriter out = res.getWriter();
        out.println("<html><head><meta HTTP-EQUIV=\"content-type\" CONTENT=\"text/html; charset=ISO-8859-1\">");
        out.println("<title>" + name + "</title>");
        Iterator it = components.iterator();
        while (it.hasNext()) {
            LayoutActionListener action = (LayoutActionListener)it.next();
            action.doRenderFirst(ctx, req, res);
            action.doRenderLast(ctx, req, res);
        }
    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        PrintWriter out = res.getWriter();
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

    public void invalidate() {}

    public void validate() {}

}
