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
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.provider.portlet.jsr.PortletServlet;

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

    public StringBuffer doRenderSelectPortlets(GridSphereEvent event, PortletTableLayout tableLayout) {
        event.getRenderRequest().setAttribute(SportletProperties.COMPONENT_ID, String.valueOf(tableLayout.getComponentID()));
        //System.err.println("in doRenderSelPortlets cid=" + (String) event.getRenderRequest().getAttribute(SportletProperties.COMPONENT_ID));
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
                        table.append(renderAddPortlets(event, j, tableLayout.getAllPortletsToAdd(event)));

                        table.append("</div>");
                    }
                }
            }
        }
        table.append("</div>");
        return table;
    }

    public StringBuffer doRenderSelectContent(GridSphereEvent event, PortletTableLayout tableLayout) {
        event.getRenderRequest().setAttribute(SportletProperties.COMPONENT_ID, String.valueOf(tableLayout.getComponentID()));
        //System.err.println("in doRenderSelContent cid=" + (String) event.getRenderRequest().getAttribute(SportletProperties.COMPONENT_ID));
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
                        Map availContent = tableLayout.getAllContentToAdd(event);
                        table.append(renderAddContent(event, j, availContent));
                        table.append(renderAddUrl(event, j));

                        table.append("</div>");
                    }
                }
            }
        }
        table.append("</div>");
        return table;
    }

    private StringBuffer renderAddStart(GridSphereEvent event, int col, String addLayout, String addLabel) {
        StringBuffer table = new StringBuffer();

        PortletRequest req = event.getRenderRequest();
        RenderResponse res = event.getRenderResponse();
        PortletURLImpl url = (PortletURLImpl) res.createActionURL();
        url.setAction(addLayout);
        String extraMode = (String) req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE);
        if (extraMode != null) {
            url.setParameter("usertable", "edit");
        }

        table.append("<form action=\"").append(url.toString()).append("\"  method=\"post\" name=\"addform\"><p>");
        table.append("<input type=\"hidden\" name=\"" + PortletTableLayout.PORTLET_COL + "\" value=\"").append(col).append("\"/>");
        table.append("<b>").append(addLabel).append("</b>&nbsp;&nbsp;&nbsp;");

        return table;
    }

    private StringBuffer renderAddEnd(GridSphereEvent event) {
        PortletRequest req = event.getRenderRequest();
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String addButton = bundle.getString("ADD");
        StringBuffer table = new StringBuffer();

        String action = SportletProperties.DEFAULT_PORTLET_ACTION + "=addportlet";

        table.append("&nbsp;&nbsp;<input type=\"submit\" name=\"").append(action).append("\" value=\"").append(addButton).append("\">");
        table.append("</p></form>");
        return table;
    }

    public StringBuffer renderAddUrl(GridSphereEvent event, int col) {
        StringBuffer table = new StringBuffer();
        PortletRequest req = event.getRenderRequest();
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);

        table.append(renderAddStart(event, col, PortletTableLayout.PORTLET_ADD_URL, bundle.getString("ADDURL")));

        table.append("<input type=\"text\" name=\"").append(PortletTableLayout.PORTLET_ADD_URL).append("\"/>&nbsp;").append(bundle.getString("ADDURLHEIGHT"));
        table.append("<input size=\"7\" type=\"text\" name=\"").append(PortletTableLayout.PORTLET_ADD_URL_HEIGHT).append("\"/>");
        table.append(renderAddEnd(event));
        return table;
    }

    public StringBuffer renderAddPortlets(GridSphereEvent event, int col, Set<ApplicationPortlet> availPortlets) {
        StringBuffer table = new StringBuffer();
        PortletRequest req = event.getRenderRequest();
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String noPortletsMsg = bundle.getString("NOPORTLETS");
        table.append(renderAddStart(event, col, PortletTableLayout.PORTLET_ADD_PORTLET, bundle.getString("ADDPORTLETS")));

        table.append("<select name=\"" + PortletTableLayout.PORTLET_ADD_PORTLET + "\">");
        if (availPortlets.isEmpty()) {
            table.append("<option value=\"" + PortletTableLayout.PORTLET_NO_ACTION + "\">").append(noPortletsMsg).append("</option>");
        }

        for (ApplicationPortlet appPortlet : availPortlets) {
            String concID = appPortlet.getConcretePortletID();
            // we don't want to list PortletServlet loader!
            if (concID.startsWith(PortletServlet.class.getName())) continue;
            String dispName = appPortlet.getDisplayName(locale);
            table.append("<option value=\"").append(appPortlet.getConcretePortletID()).append("\">").append(appPortlet.getWebApplicationName()).append(" - ").append(dispName).append("</option>");
        }
        table.append("</select>");

        table.append(renderAddEnd(event));
        return table;
    }

    public StringBuffer renderAddContent(GridSphereEvent event, int col, Map availContent) {
        StringBuffer table = new StringBuffer();
        PortletRequest req = event.getRenderRequest();
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String noPortletsMsg = bundle.getString("NOCONTENT");
        table.append(renderAddStart(event, col, PortletTableLayout.PORTLET_ADD_CONTENT, bundle.getString("ADDCONTENT")));
        table.append("<select name=\"" + PortletTableLayout.PORTLET_ADD_CONTENT + "\">");

        if (availContent.isEmpty()) {
            table.append("<option value=\"" + PortletTableLayout.PORTLET_NO_ACTION + "\">").append(noPortletsMsg).append("</option>");
        }

        for (Object o : availContent.keySet()) {
            String pid = (String) o;
            String dispName = (String) availContent.get(pid);
            table.append("<option value=\"").append(pid).append("\">").append(dispName).append("</option>");
        }
        table.append("</select>");

        table.append(renderAddEnd(event));
        return table;
    }

}
