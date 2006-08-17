/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: TableLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.layout.view.classic;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletFrameLayout;
import org.gridlab.gridsphere.layout.PortletRowLayout;
import org.gridlab.gridsphere.layout.PortletTableLayout;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.TableLayoutView;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.util.*;

public class TableLayout extends BaseRender implements TableLayoutView {

    protected static final StringBuffer START_MAXIMIZED =
            new StringBuffer("\n<!-- START MAXIMIZED TABLE COMP --><table border=\"0\" width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"><tbody><tr><td>");

    protected static final StringBuffer END_MAXIMIZED =
            new StringBuffer("\n</td></tr></tbody></table><!-- END MAXIMIZED TABLE COMP -->\n");

    protected static final StringBuffer END_BORDER = new StringBuffer("</td> </tr><!-- END TABLE BORDER -->\n");

    protected static final StringBuffer END_TABLE = new StringBuffer("</tbody></table><!-- END TABLE -->\n");

    /**
     * Constructs an instance of PortletTableLayout
     */
    public TableLayout() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        // starting of the gridtable
        PortletTableLayout tableLayout = (PortletTableLayout)comp;
        StringBuffer table = new StringBuffer();
        table.append("\n<!-- START TABLE --><table ");
        if (!tableLayout.getStyle().equals("")) {
            table.append("class=\"" + tableLayout.getStyle() + "\" ");
        }
        table.append("border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody>");
        return table;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        //PortletTableLayout tableLayout = (PortletTableLayout)comp;
        return END_TABLE;
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        StringBuffer table = new StringBuffer();
        table.append("\n<!-- START TABLE BORDER --><tr><td valign=\"top\" width=\"" + comp.getWidth() + "\">");
        return table;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return END_BORDER;
    }


    public StringBuffer doStartMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout) {
        return START_MAXIMIZED;
    }

    public StringBuffer doEndMaximizedComponent(GridSphereEvent event, PortletTableLayout tableLayout) {
        return END_MAXIMIZED;
    }

    public StringBuffer doRenderUserSelects(GridSphereEvent event, PortletTableLayout tableLayout) {
        PortletRequest req = event.getPortletRequest();
        req.setAttribute(tableLayout.getComponentIDVar(req), tableLayout.getLabel());
        StringBuffer table = new StringBuffer();
        table.append("<tr><td valign=\"top\" width=\"100%\">");
        List components = tableLayout.getPortletComponents();
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
                        table.append("<table width=\"100%\" cellspacing=\"2\" cellpadding=\"0\">");

                        table.append("<tbody><tr><td>");

                        // render add portlets listbox
                        Map availPortlets = tableLayout.getAllPortletsToAdd(event);
                        table.append(renderAddPortlets(event, j, availPortlets));

                        table.append("</td></tr></tbody>");
                        table.append("</table>");
                    }

                    table.append("</td>");
                }

                table.append("</tr></tbody></table>");
                table.append("<!-- END ROW -->");
            }
        }
        table.append("</td> </tr>");
        return table;
    }

    public StringBuffer renderAddPortlets(GridSphereEvent event, int col, Map availPortlets) {
        StringBuffer table = new StringBuffer();

        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        Locale locale = req.getLocale();
        PortletURI uri = res.createURI();
                uri.addAction(PortletTableLayout.PORTLET_ADD_ACTION);

        table.append("<form action=\"" + uri.toString() + "\"  method=\"POST\" name=\"addform\">");
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
        table.append("&nbsp;&nbsp;<input type=\"submit\" name=\"gs_action=addportlet\" value=\"" + addButton + "\">");
        table.append("</form>");
        return table;
    }


}