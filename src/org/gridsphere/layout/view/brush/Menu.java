package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletMenu;
import org.gridsphere.layout.PortletNavMenu;
import org.gridsphere.layout.PortletTab;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.TabbedPaneView;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.StringTokenizer;

public class Menu extends BaseRender implements TabbedPaneView {

    /**
     * Constructs an instance of PortletTabbedPane
     */
    public Menu() {
    }

    /**
     * Replace blank spaces in title with '&nbsp;'
     *
     * @param title the tab title
     * @return a title without blank spaces
     */
    private static String replaceBlanks(String title) {
        String result = "&nbsp;";
        StringTokenizer st = new StringTokenizer(title);
        while (st.hasMoreTokens()) {
            result += st.nextToken() + "&nbsp;";
        }
        return result;
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        StringBuffer result = new StringBuffer();

        result.append("\n<!-- LAYOUT NAVIGATION -->\n <div id=\"gridsphere-layout-navigation\"> \n<ul id=\"gridsphere-menu\">\n");
        result.append("<li id=\"gridsphere-menu-border-left\">&nbsp;</li>");

        return result;
    }

    public StringBuffer doRenderTab(GridSphereEvent event, PortletNavMenu tabPane, PortletTab tab) {
        // this really creates the individual tabs
        StringBuffer pane = new StringBuffer();
        String link = tab.createTabTitleLink(event);
        String lang = event.getRenderRequest().getLocale().getLanguage();
        String title = tab.getTitle(lang);

        String selected = "gridsphere-menu-nonsel";
        if (tab.isSelected()) {
            selected = "gridsphere-menu-sel";
        }
        pane.append("<li class=\"").append(selected);
        pane.append("\"><a href=\"").append(link).append("\"><span>");
        if (title != null) {
            pane.append(replaceBlanks(title));
        }
        pane.append("</span></a></li>");

        return pane;
    }

    public StringBuffer doRenderEditTab(GridSphereEvent event, PortletNavMenu menu, boolean isSelected) {
        RenderResponse res = event.getRenderResponse();
        RenderRequest req = event.getRenderRequest();

        PortletURL portletURL = res.createActionURL();
        portletURL.setParameter("newmenutab", "true");
        req.setAttribute(SportletProperties.COMPONENT_ID, String.valueOf(menu.getComponentID()));
        String link = portletURL.toString();
        StringBuffer pane = new StringBuffer();


        pane.append("<li><a href=\"").append(link).append("\">");
        pane.append(replaceBlanks("New Tab"));
        pane.append("</a></li>");
        return pane;
    }


    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        PortletMenu menu = (PortletMenu) comp;
        StringBuffer buffer = new StringBuffer();

        buffer.append("<li id=\"gridsphere-menu-border-right\">&nbsp;</li>");
        buffer.append("\n</ul> <!-- END NAVIGATION (SUB) MENU -->\n");
        buffer.append("</div><!--  END LAYOUT NAVIGATION -->");
        buffer.append("<div id=\"gridsphere-menu-bottom-line\">&nbsp;</div>");
//        buffer.append("<div style=\"clear: both;\"></div>");
        buffer.append("<div id=\"gridsphere-layout-body\"> <!-- start the main portlets -->\n");

        return buffer;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n</div> <!-- END gridsphere-layout-body -->\n");
        return buffer;
    }
}
