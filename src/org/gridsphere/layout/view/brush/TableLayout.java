/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: TableLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletFrameLayout;
import org.gridsphere.layout.PortletRowLayout;
import org.gridsphere.layout.PortletTableLayout;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.TableLayoutView;
import org.gridsphere.portlet.impl.PortletURLImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import java.util.*;

public class TableLayout extends BaseRender implements TableLayoutView {

    protected static final StringBuffer START_MAXIMIZED =
            // class "window-maximized" added
            new StringBuffer("\n<!-- START MAXIMIZED MODERN TABLE COMP --><div class=\"window-maximized\">");

    protected static final StringBuffer END_MAXIMIZED =
            new StringBuffer("\n</div><!-- END MAXIMIZED MODERN TABLE COMP -->\n");

    protected static final StringBuffer END_BORDER = new StringBuffer("\n");// unuseful end-border DIV removed

    protected static final StringBuffer END_TABLE = new StringBuffer("</div><!-- END MODERN TABLE -->\n");

    /**
     * Constructs an instance of PortletTableLayout
     */
    public TableLayout() {
    }


    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer();
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        StringBuffer table = new StringBuffer();
        table.append("\n<!--  _____________________________________ table layout start -->\n");
        return table;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer("\n<!-- _____________________________________ table layout end -->\n");
    }

    public StringBuffer doStartMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout) {
        return START_MAXIMIZED;
    }

    public StringBuffer doEndMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout) {
        return END_MAXIMIZED;
    }

    public StringBuffer doRenderUserSelects(GridSphereEvent event, PortletTableLayout tableLayout) {
        event.getRenderRequest().setAttribute(SportletProperties.COMPONENT_ID, String.valueOf(tableLayout.getComponentID()));
        System.err.println("in doRenderSel cid=" + (String)event.getRenderRequest().getAttribute(SportletProperties.COMPONENT_ID));
        StringBuffer table = new StringBuffer();
        table.append("<div class=\"gridsphere-layout-row\">");
        List components = tableLayout.getPortletComponents();
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout layout = (PortletRowLayout) o;
                List cols = layout.getPortletComponents();

                for (int j = 0; j < cols.size(); j++) {
                    o = cols.get(j);
                    if (o instanceof PortletFrameLayout) {
                        PortletFrameLayout col = (PortletFrameLayout) o;

                        table.append("<div class=\"gridsphere-layout-column\"");
                        if (!col.getWidth().equals("")) {
                            table.append(" style=\"width: ").append(col.getWidth()).append("\"");
                        }
                        table.append(">");
                        // render add portlets listbox
                        Map availPortlets = tableLayout.getAllPortletsToAdd(event);
                        table.append(renderAddPortlets(event, j, availPortlets));

                        table.append("</div>");
                    }
                }
            }
        }
        table.append("</div>");
        return table;
    }

    public StringBuffer renderAddPortlets(GridSphereEvent event, int col, Map availPortlets) {
        StringBuffer table = new StringBuffer();

        PortletRequest req = event.getRenderRequest();
        RenderResponse res = event.getRenderResponse();
        Locale locale = req.getLocale();

        PortletURLImpl url = (PortletURLImpl)res.createActionURL();

        System.err.println("in doRenderSel cid=" + (String)event.getRenderRequest().getAttribute(SportletProperties.COMPONENT_ID));

        url.setAction(PortletTableLayout.PORTLET_ADD_ACTION);
        String extraMode = (String)req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE);
        if (extraMode != null) {
            url.setParameter("usertable", "edit");
        }

        table.append("<form action=\"" + url.toString() + "\"  method=\"post\" name=\"addform\"><p>");
        table.append("<input type=\"hidden\" name=\"" + PortletTableLayout.PORTLET_COL + "\" value=\"" + col + "\"/>");

        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String addLabel = bundle.getString("ADDPORTLETS");
        String addButton = bundle.getString("ADD");
        String noPortletsMsg = bundle.getString("NOPORTLETS");

        table.append("<b>" + addLabel + "</b>&nbsp;&nbsp;&nbsp;");
        table.append("<select name=\"" + PortletTableLayout.PORTLET_ADD_ACTION + "\">");

        if (availPortlets.isEmpty()) {
            table.append("<option value=\"" + PortletTableLayout.PORTLET_NO_ACTION + "\">" + noPortletsMsg + "</option>");
        }

        Iterator it = availPortlets.keySet().iterator();
        while (it.hasNext()) {
            String pid = (String) it.next();
            String dispName = (String) availPortlets.get(pid);
            table.append("<option value=\"" + pid + "\">" + dispName + "</option>");
        }

        table.append("</select>");

        String action = SportletProperties.DEFAULT_PORTLET_ACTION + "=addportlet";

        table.append("&nbsp;&nbsp;<input type=\"submit\" name=\"" + action + "\" value=\"" + addButton + "\">");
        table.append("</p></form>");
        return table;
    }
}
