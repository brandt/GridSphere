/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.layout.event.PortletComponentEvent;
import org.gridlab.gridsphere.layout.event.PortletFrameEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of columns.
 * Portlets are arranged in column-wise order starting from the left most column.
 */
public class PortletTableLayout extends PortletFrameLayout implements Cloneable {

    protected static final String PORTLET_ADD_ACTION = "addPortlet";
    protected static final String PORTLET_NO_ACTION = "none";

    /** css Style of the table */
    protected String style = null;


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
        synchronized(scomponents) {
            for (int i=0;i<scomponents.size();i++) {
                p = (PortletComponent)scomponents.get(i);
                if (p instanceof PortletLayout) {
                    PortletComponent layout = this.getMaximizedComponent(((PortletLayout)p).getPortletComponents());
                    if (layout!=null) {
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

        String portletClass = req.getParameter(PORTLET_ADD_ACTION);

        if (portletClass.equals(PORTLET_NO_ACTION)) return;

        String colStr = req.getParameter("col");
        int col = Integer.valueOf(colStr).intValue();

        boolean done = false;
        // first loop thru rows to see if one is empty for the requested column
        for (int j = 0; j < components.size(); j++) {
            Object o  = components.get(j);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout r = (PortletFrameLayout)o;
                List l = r.getPortletComponents();
                int i = 0;
                while ((i < l.size()) && (!done)) {
                    Object c = l.get(i);
                    if (i == col) {
                        System.err.println("found the right column");
                        if (c instanceof PortletFrameLayout) {
                            PortletFrameLayout existingColumn = (PortletFrameLayout)c;
                            if (existingColumn.getPortletComponents().isEmpty()) {
                                if (!portletClass.equals("")) {
                                    PortletFrame frame = new PortletFrame();
                                    System.err.println("adding portlet " + portletClass);
                                    frame.setPortletClass(portletClass);
                                    System.err.println("column is empty");
                                    existingColumn.addPortletComponent(frame);
                                    done = true;
                                }
                            } else{
                                System.err.println("column not empty");
                            }
                        }
                    }
                    i++;
                }
            }
        }

        // if free row not found make one
        if (!done) {
            PortletRowLayout row = new PortletRowLayout();
            PortletColumnLayout newcol;
            PortletRowLayout r = (PortletRowLayout)components.get(0);
            List l = r.getPortletComponents();
            for (int i = 0; i < l.size(); i++) {
                PortletColumnLayout existingColumn = (PortletColumnLayout)l.get(i);
                if (i == col) {
                    newcol = new PortletColumnLayout();
                    if (!portletClass.equals("")) {
                        PortletFrame frame = new PortletFrame();
                        System.err.println("adding portlet " + portletClass);
                        frame.setPortletClass(portletClass);
                        newcol.addPortletComponent(frame);
                    }
                    newcol.setWidth(existingColumn.getWidth());
                } else {
                    newcol = new PortletColumnLayout();
                    newcol.setWidth(existingColumn.getWidth());
                }
                row.addPortletComponent(newcol);
            }
            components.add(row);
        }

        req.setAttribute(SportletProperties.INIT_PAGE, "true");
        System.err.println("yeehaw!");
    }

    public void customActionPerformed(GridSphereEvent event) throws
               PortletLayoutException, IOException {


        System.err.println("action in PortletTableLayout" + this.getComponentID());
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
        System.err.println("\t\tin render TableLayout");
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
        synchronized(scomponents) {
            for (int i=0;i<scomponents.size();i++) {
                p = (PortletComponent)scomponents.get(i);
                if (p instanceof PortletLayout) {
                    PortletComponent maxi = getMaximizedComponent(scomponents);
                    if (maxi!=null) {
                        out.println("<table border=\"0\" width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"><tbody><tr><td>");
                        maxi.doRender(event);
                        out.println("</td></tr></tbody></table>");
                        System.err.println("in porllet table layout handling maximized");
                        if ((canModify) && (!hasFrameMaximized)) {
                            renderUserSelects(event);
                        }
                        return;
                    }
                }
            }

            // starting of the gridtable
            out.println("<table ");
            if (this.style!=null) {
                out.print("class=\""+this.style+"\" ");
            }
            out.println("border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody>");

            for (int i=0;i<scomponents.size();i++) {
                p = (PortletComponent) scomponents.get(i);
                //out.println("<tr><td valign=\"top\" width=\"100%\">");
                out.println("<tr><td valign=\"top\" width=\"" + p.getWidth() + "\">");
                if (p.getVisible()) {
                    p.doRender(event);
                    //out.println("grid comp: "+i);
                }
                out.println("</td> </tr>");
            }

            System.err.println("canModify" + canModify);

            /** setup bottom add portlet listbox */
            if ((canModify) && (!hasFrameMaximized)) {
                renderUserSelects(event);
            }

            out.println("</tbody></table>");
        }
    }

    public void renderUserSelects(GridSphereEvent event) throws IOException {
        System.err.println("in canmodify");
        PrintWriter out = event.getPortletResponse().getWriter();
        out.println("<tr><td valign=\"top\" width=\"100%\">");
        //out.println("<tr>");
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                System.err.println("in canmodify has a row");
                PortletFrameLayout layout = (PortletRowLayout)o;
                out.println(" <!-- START ROW --> ");
                out.println("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
                out.println("<tbody><tr>");
                List cols = layout.getPortletComponents();

                for (int j = 0; j < cols.size(); j++) {
                    System.err.println("in canmodify has a col");
                    o = cols.get(j);
                    if (o instanceof PortletFrameLayout) {
                        PortletFrameLayout col = (PortletFrameLayout)o;
                        out.println("<td valign=\"top\" width=\"" + col.getWidth() + "\">");
                        out.println("<table width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"> <!-- START COLUMN -->");

                        out.println("<tbody><tr><td>");

                        //
                        renderAddPortlets(event, j);

                        out.println("</td></tr></tbody>");
                        out.println("</table> <!-- END COLUMN -->");
                    }

                    out.println("</td>");
                }

                out.println("</tr></tbody></table>");
                out.println("<!-- END ROW -->");
            }
        }
        out.println("</td> </tr>");
    }

    public void renderAddPortlets(GridSphereEvent event, int col) throws IOException {

        System.err.println("in renderAddPortlets");
        PortletRequest req = event.getPortletRequest();
        Locale locale = req.getLocale();
        PortletRegistry registry = PortletRegistry.getInstance();
        req.setAttribute(SportletProperties.COMPONENT_ID, componentIDStr);
        
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();
        PortletURI uri = res.createURI();
        uri.addAction(PORTLET_ADD_ACTION);

        Map groups = (Map)req.getAttribute(SportletProperties.PORTLETGROUPS);

        Map availPortlets = new HashMap();
        Iterator it = groups.keySet().iterator();
        while (it.hasNext()) {
            PortletGroup g = (PortletGroup)it.next();
            if (g.equals(PortletGroupFactory.GRIDSPHERE_GROUP)) continue;
            Iterator sit = g.getPortletRoleList().iterator();
            PortletRole role = (PortletRole)groups.get(g);
            while (sit.hasNext()) {
                SportletRoleInfo info = (SportletRoleInfo)sit.next();
                String pclass = info.getPortletClass();
                PortletRole reqRole = info.getPortletRole();
                if (role.compare(role, reqRole) >= 0) {
                    if (!availPortlets.containsKey(pclass)) {
                        String appID = PortletRegistry.getApplicationPortletID(info.getPortletClass());
                        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
                        if (appPortlet != null) {
                            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(info.getPortletClass());

                            String dispName = concPortlet.getDisplayName(locale);

                            availPortlets.put(pclass, dispName);
                        }
                    }
                }

            }
        }

        out.println("<form action=\"" + uri.toString() + "\" + method=\"POST\" name=\"addform\">");
        out.println("<input type=\"hidden\" name=\"col\" value=\"" + col + "\"/>");

        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String addLabel = bundle.getString("ADDPORTLETS");
        String addButton = bundle.getString("ADD");
        String noPortletsMsg = bundle.getString("NOPORTLETS");

        out.println("<b>" + addLabel + "</b>");
        out.println("<select name=\"" + PORTLET_ADD_ACTION + "\">");

        if (availPortlets.isEmpty()) {
            out.println("<option value=\"" + PORTLET_NO_ACTION + "\">" + noPortletsMsg + "</option>");
        }
        it = availPortlets.keySet().iterator();
        while (it.hasNext()) {
            String pclass = (String)it.next();
            String dispName = (String)availPortlets.get(pclass);
            out.println("<option value=\"" + pclass + "\">" + dispName + "</option>");
        }

        out.println("</select>");
        out.println("<input type=\"submit\" name=\"gs_action=addportlet\" value=\"" + addButton + "\"");
        out.println("</form>");
    }


    public Object clone() throws CloneNotSupportedException {
        PortletTableLayout g = (PortletTableLayout)super.clone();
        g.style = this.style;
        return g;
    }

}