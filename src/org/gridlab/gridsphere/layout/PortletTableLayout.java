/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of columns.
 * Portlets are arranged in column-wise order starting from the left most column.
 */
public class PortletTableLayout extends PortletFrameLayout implements Cloneable {

    protected static final String PORTLET_COL = "gs_col";
    protected static final String PORTLET_ADD_ACTION = "gs_addPortlet";
    protected static final String PORTLET_NO_ACTION = "gs_none";

    /**
     * css Style of the table
     */
    protected String style = null;

    protected StringBuffer table = null;

    /**
     * Constructs an instance of PortletTableLayout
     */
    public PortletTableLayout() {
    }

    public List init(PortletRequest req, List list) {
        return super.init(req, list);
    }

    /**
     * Returns the CSS style name for the grid-layout.
     *
     * @return css style name
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the CSS style name for the grid-layout.
     * This needs to be set if you want to have transparent portlets, if there is
     * no background there can't be a real transparent portlet.
     * Most likely one sets just the background in that one.
     *
     * @param style css style of the that layout
     */
    public void setStyle(String style) {
        this.style = style;
    }

    private PortletComponent getMaximizedComponent(List components) {
        PortletComponent p = null;
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            for (int i = 0; i < scomponents.size(); i++) {
                p = (PortletComponent) scomponents.get(i);
                if (p instanceof PortletLayout) {
                    PortletComponent layout = this.getMaximizedComponent(((PortletLayout) p).getPortletComponents());
                    if (layout != null) {
                        p = layout;
                    }
                }
                if (p.getWidth().equals("100%")) {
                    return p;
                }
            }
        }
        return null;
    }

    protected void addPortlet(GridSphereEvent event) {

        PortletRequest req = event.getPortletRequest();

        String portletId = req.getParameter(PORTLET_ADD_ACTION);

        if (portletId.equals(PORTLET_NO_ACTION)) return;

        String colStr = req.getParameter(PORTLET_COL);
        int col = Integer.valueOf(colStr).intValue();

        // first loop thru rows to see if one is empty for the requested column
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout r = (PortletFrameLayout) o;
                List cols = r.getPortletComponents();

                Object c = cols.get(col);

                if (c instanceof PortletFrameLayout) {
                    PortletFrameLayout existingColumn = (PortletFrameLayout) c;

                    if (!portletId.equals("")) {
                        PortletFrame frame = new PortletFrame();
                        frame.setPortletClass(portletId);
                        existingColumn.addPortletComponent(frame);
                    }
                }
            }
        }

        req.setAttribute(SportletProperties.INIT_PAGE, "true");
    }

    public void customActionPerformed(GridSphereEvent event) throws
            PortletLayoutException, IOException {

        if (event.hasAction()) {
            if (event.getAction().getName().equals(PORTLET_ADD_ACTION)) {
                addPortlet(event);
            }
        }
    }

    /**
     * Renders the portlet grid layout component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException            if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
    	String markupName=event.getPortletRequest().getClient().getMarkupName();
    	if (markupName.equals("html")){
    		doRenderHTML(event);
    	}
    	else
    	{
    		doRenderWML(event);
    	}
    }

    public void doRenderWML(GridSphereEvent event) throws PortletLayoutException, IOException {

        super.doRender(event);

        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();

        PortletComponent p = null;

        // check if one window is maximized
        /*
        StringBuffer frame = new StringBuffer();
        StringWriter storedWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(storedWriter);
        PortletResponse wrappedResponse = new StoredPortletResponseImpl(res, writer);
        */
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            for (int i = 0; i < scomponents.size(); i++) {
                p = (PortletComponent) scomponents.get(i);
                if (p instanceof PortletLayout) {
                    PortletComponent maxi = getMaximizedComponent(scomponents);
                    if (maxi != null) {
                        //out.println("<table border=\"0\" width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"><tbody><tr><td>");
                        maxi.doRender(event);
                        out.println("<p />");
                        //out.println("</td></tr></tbody></table>");
                        if ((canModify) && (!hasFrameMaximized)) {
                            renderUserSelects(event);
                        }
                        return;
                    }
                }
            }

            // starting of the gridtable
            //out.println("<table ");
            if (this.style != null) {
                // out.print("class=\"" + this.style + "\" ");
            }
            //out.println("border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody>");

            for (int i = 0; i < scomponents.size(); i++) {
                p = (PortletComponent) scomponents.get(i);

                //out.println("<tr><td valign=\"top\" width=\"" + p.getWidth() + "\">");
                if (p.getVisible()) {
                    p.doRender(event);
                    //out.println("grid comp: "+i);
                }
                out.println ("<p />");
                //out.println("</td> </tr>");
            }

            /** setup bottom add portlet listbox */
            if ((canModify) && (!hasFrameMaximized)) {
                renderUserSelects(event);
            }

            //out.println("</tbody></table>");
    }
}
    public void doRenderHTML(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);

        PortletResponse res = event.getPortletResponse();
        //PrintWriter out = res.getWriter();

        table = new StringBuffer();
        PortletComponent p = null;

        // check if one window is maximized
        /*
        StringBuffer frame = new StringBuffer();
        StringWriter storedWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(storedWriter);
        PortletResponse wrappedResponse = new StoredPortletResponseImpl(res, writer);
        */
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            for (int i = 0; i < scomponents.size(); i++) {
                p = (PortletComponent) scomponents.get(i);
                if (p instanceof PortletLayout) {
                    PortletComponent maxi = getMaximizedComponent(scomponents);
                    if (maxi != null) {
                        table.append("<table border=\"0\" width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"><tbody><tr><td>");
                        maxi.doRender(event);
                        table.append(maxi.getBufferedOutput());
                        table.append("</td></tr></tbody></table>");
                        if ((canModify) && (!hasFrameMaximized)) {
                            renderUserSelects(event);
                        }
                        return;
                    }
                }
            }

            // starting of the gridtable
            table.append("<table ");
            if (this.style != null) {
                table.append("class=\"" + this.style + "\" ");
            }
            table.append("border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody>");

            for (int i = 0; i < scomponents.size(); i++) {
                p = (PortletComponent) scomponents.get(i);
                //out.println("<tr><td valign=\"top\" width=\"100%\">");
                table.append("<tr><td valign=\"top\" width=\"" + p.getWidth() + "\">");
                if (p.getVisible()) {
                    p.doRender(event);
                    table.append(p.getBufferedOutput());
                    //out.println("grid comp: "+i);
                }
                table.append("</td> </tr>");
            }

            /** setup bottom add portlet listbox */
            if ((canModify) && (!hasFrameMaximized)) {
                renderUserSelects(event);
            }

            table.append("</tbody></table>");
        }
    }

    public StringBuffer getBufferedOutput() {
        return table;
    }
    public void renderUserSelects(GridSphereEvent event) throws IOException {
        //PrintWriter out = event.getPortletResponse().getWriter();
        table.append("<tr><td valign=\"top\" width=\"100%\">");
        //out.println("<tr>");
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout layout = (PortletRowLayout) o;
                table.append(" <!-- START ROW --> ");
                table.append("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
                table.append("<tbody><tr>");
                List cols = layout.getPortletComponents();

                for (int j = 0; j < cols.size(); j++) {
                    o = cols.get(j);
                    if (o instanceof PortletFrameLayout) {
                        PortletFrameLayout col = (PortletFrameLayout) o;
                        table.append("<td valign=\"top\" width=\"" + col.getWidth() + "\">");
                        table.append("<table width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"> <!-- START COLUMN -->");

                        table.append("<tbody><tr><td>");

                        // render add portlets listbox
                        renderAddPortlets(event, j);

                        table.append("</td></tr></tbody>");
                        table.append("</table> <!-- END COLUMN -->");
                    }

                    table.append("</td>");
                }

                table.append("</tr></tbody></table>");
                table.append("<!-- END ROW -->");
            }
        }
        table.append("</td> </tr>");
    }

    public void renderAddPortlets(GridSphereEvent event, int col) throws IOException {
        PortletRequest req = event.getPortletRequest();
        Locale locale = req.getLocale();
        PortletRegistry registry = PortletRegistry.getInstance();
        req.setAttribute(SportletProperties.COMPONENT_ID, componentIDStr);

        PortletResponse res = event.getPortletResponse();
        //PrintWriter out = res.getWriter();
        PortletURI uri = res.createURI();
        uri.addAction(PORTLET_ADD_ACTION);

        Map groups = (Map) req.getAttribute(SportletProperties.PORTLETGROUPS);

        Map availPortlets = new HashMap();
        Iterator it = groups.keySet().iterator();
        while (it.hasNext()) {
            PortletGroup g = (PortletGroup) it.next();
            //System.err.println("group= " + g.getName());
            if (g.equals((PortletGroup)req.getAttribute(SportletProperties.PORTLET_GROUP))) continue;
            Iterator sit = g.getPortletRoleList().iterator();
            PortletRole role = (PortletRole) groups.get(g);
            while (sit.hasNext()) {
                SportletRoleInfo info = (SportletRoleInfo) sit.next();

                String appID = PortletRegistry.getApplicationPortletID(info.getPortletClass());

                //System.err.println("info class= " + info.getPortletClass());

                PortletRole reqRole = info.getPortletRole();
                //System.err.println("role= " + info.getPortletRole());

                if (role.compare(role, reqRole) >= 0) {
                    if (!availPortlets.containsKey(appID)) {
                        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
                        if (appPortlet != null) {
                            //System.err.println("appID= " + appID);
                            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(appID);
                            if (concPortlet != null) {
                                String dispName = concPortlet.getDisplayName(locale);

                                //System.err.println("show portlet = " + dispName);
                                availPortlets.put(appID, dispName);
                            }
                        } else {
                            //System.err.println("app portlet was null for " + appID);
                        }
                    }
                }
            }
        }

        table.append("<form action=\"" + uri.toString() + "\"  method=\"POST\" name=\"addform\">"); //WAP 2.0 changes: method instead of + method
        table.append("<input type=\"hidden\" name=\"" + PORTLET_COL + "\" value=\"" + col + "\"/>");

        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String addLabel = bundle.getString("ADDPORTLETS");
        String addButton = bundle.getString("ADD");
        String noPortletsMsg = bundle.getString("NOPORTLETS");

        table.append("<b>" + addLabel + "</b>&nbsp;&nbsp;&nbsp;");
        table.append("<select name=\"" + PORTLET_ADD_ACTION + "\">");

        if (availPortlets.isEmpty()) {
            table.append("<option value=\"" + PORTLET_NO_ACTION + "\">" + noPortletsMsg + "</option>");
        }
        it = availPortlets.keySet().iterator();
        while (it.hasNext()) {
            String pid = (String) it.next();
            String dispName = (String) availPortlets.get(pid);
            table.append("<option value=\"" + pid + "\">" + dispName + "</option>");
        }

        table.append("</select>");
        table.append("&nbsp;&nbsp;<input type=\"submit\" name=\"gs_action=addportlet\" value=\"" + addButton + "\">");//WAP 2.0 changes:added a closing >
        table.append("</form>");
    }


    public Object clone() throws CloneNotSupportedException {
        PortletTableLayout g = (PortletTableLayout) super.clone();
        g.style = this.style;
        return g;
    }

}