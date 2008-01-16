/*
* @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
* @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
* @version $Id$
*/

package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletNavMenu;
import org.gridsphere.layout.PortletTab;
import org.gridsphere.layout.PortletTabbedPane;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.TabbedPaneView;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;
import java.util.StringTokenizer;

public class TabbedPane extends BaseRender implements TabbedPaneView {

    // TAB
    protected static final StringBuffer TAB_START_MENU =
            new StringBuffer("\n<!-- LAYOUT NAVIGATION -->\n <div id=\"gridsphere-layout-navigation\"> \n<ul id=\"gridsphere-nav\"><li id=\"gridsphere-nav-border-left\">&nbsp;</li>\n");
    protected static final StringBuffer TAB_END_MENU =
            new StringBuffer("\n<li id=\"gridsphere-nav-border-right\">&nbsp;</li></ul> <!-- END NAVIGATION (SUB) MENU -->\n");

    // SUB TAB
    protected static final StringBuffer TAB_START_SUBMENU =
            new StringBuffer("\n<ul id=\"gridsphere-sub-nav\"><li id=\"gridsphere-sub-nav-border-left\">&nbsp;</li>\n");
    protected static final StringBuffer TAB_END_SUBMENU =
            new StringBuffer("\n<li id=\"gridsphere-sub-nav-border-right\">&nbsp;</li></ul> <!-- END NAVIGATION (SUB) MENU -->\n");

    // SIDEMENU
    protected static final StringBuffer TAB_START_SIDEMENU =
            new StringBuffer("\n<!-- BEGIN NAVIGATION SIDE MENU -->\n")
                    .append("<div id=\"gridsphere-layout-body\">\n")
                    .append("<div class=\"gridsphere-layout-row\">\n ")
                    .append("<div class=\"gridsphere-layout-column\" style=\"width: 20%\">\n")
                    .append("<div id=\"gridsphere-layout-sidemenu\">\n")
                    .append("<div id=\"button\">\n <ul>\n");

    protected static final StringBuffer TAB_END_SIDEMENU =
            new StringBuffer("</ul>\n</div><!-- END BUTTON -->\n</div> <!-- END SIDEMENU -->\n </div> <!-- END LAYOUT COLUMN -->\n")
                    .append("<div class=\"gridsphere-layout-column\" style=\"width: 80%\"> <!-- START MAIN PORTLET AREA -->\n");

    // TOPMENU
    protected static final StringBuffer TAB_START_TOPMENU =
            new StringBuffer("\n<!-- START NAVIGATION TOP MENU-->\n <div id=\"gridsphere-layout-navigation\"> \n<ul id=\"gridsphere-nav\"><li id=\"gridsphere-nav-border-left\">&nbsp;</li>\n");
    protected static final StringBuffer TAB_END_TOPMENU =
            new StringBuffer("\n<li id=\"gridsphere-nav-border-right\">&nbsp;</li></ul></div>\n<!-- END NAVIGATION TOP MENU -->\n");


    private static String TAB_STYLE_MENU = "menu";
    private static String TAB_STYLE_SUBMENU = "sub-menu";
    private static String TAB_STYLE_SIDEMENU = "side-menu";
    private static String TAB_STYLE_TOPMENU = "top-menu";

    private boolean canModify = false;

    /**
     * Constructs an instance of PortletTabbedPane
     */
    public TabbedPane() {
    }


    /**
     * Creates the portlet tab link URIs that are used to send events to
     * the portlet tabs.
     *
     * @param event the gridsphere event
     * @param menu  the menu parent
     * @return a string array of the tab hyperlinks
     */
    protected static String[] createTabLinks(GridSphereEvent event, PortletNavMenu menu) {
        // Make tab links
        String[] tabLinks = new String[menu.getPortletTabs().size()];
        List tabs = menu.getPortletTabs();
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab) tabs.get(i);
            tabLinks[i] = tab.createTabTitleLink(event);
        }
        return tabLinks;
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
        PortletTabbedPane pane = (PortletTabbedPane) comp;

        StringBuffer result = new StringBuffer();
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_SUBMENU)) {
            result.append(TAB_START_SUBMENU);
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_MENU)) {
            result.append(TAB_START_MENU);
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_SIDEMENU)) {
            result.append(TAB_START_SIDEMENU);
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_TOPMENU)) {
            result.append(TAB_START_TOPMENU);
        }


        return result;
    }

    public StringBuffer doRenderTab(GridSphereEvent event, PortletNavMenu tabPane, PortletTab tab) {
        // this really creates the individual tabs
        StringBuffer pane = new StringBuffer();
        String link = tab.createTabTitleLink(event);
        String lang = event.getRenderRequest().getLocale().getLanguage();
        String title = tab.getTitle(lang);

        if (title == null) return pane;


        if (tabPane.getStyle().equalsIgnoreCase(TAB_STYLE_SUBMENU)) {
            pane.append("\n<li");
            if (tab.isSelected()) {
                pane.append(" class=\"sub-nav-sel\"");
            }
            pane.append(">");
            pane.append("<a href=\"").append(link).append("\">").append(replaceBlanks(title)).append("</a>\n");
            pane.append("</li>\n");
        }
        if (tabPane.getStyle().equalsIgnoreCase(TAB_STYLE_MENU) || tabPane.getStyle().equalsIgnoreCase(TAB_STYLE_TOPMENU)) {
            String selected = "nav-nonsel";
            if (tab.isSelected()) {
                selected = "nav-sel";
            }
            if (!tab.getOutline()) {
                selected = "";
            }
            String style = "";
            if (tab.getPadding() != null) {
                style += " padding: " + tab.getPadding() + "; ";
            }
            if (tab.getAlign().equals("right")) {
                style += " float: right; ";
            }
            pane.append("<li class=\"").append(selected);
            if (!style.equals("")) {
                pane.append("\" style=\"").append(style).append("\">");
            } else {
                pane.append("\">");
            }
            pane.append("<a href=\"").append(link).append("\"><span>");

            if (title != null) {
                pane.append(replaceBlanks(title));
            }
            pane.append("</span></a>");
            pane.append("</li>");
        }
        if (tabPane.getStyle().equalsIgnoreCase(TAB_STYLE_SIDEMENU)) {
            String selected = "";
            if (tab.isSelected()) {
                selected = " class =\"selected\" ";
            }
            pane.append("<li ").append(selected).append("><a href=\"").append(link).append("\">").
                    append(replaceBlanks(title)).append("</a></li>");
        }


        return pane;
    }

    public StringBuffer doRenderEditTab(GridSphereEvent event, PortletNavMenu tabPane, boolean isSelected) {
        RenderResponse res = event.getRenderResponse();
        RenderRequest req = event.getRenderRequest();
        PortletURL portletURL = res.createActionURL();

        StringBuffer pane = new StringBuffer();
        req.setAttribute(SportletProperties.COMPONENT_ID, String.valueOf(tabPane.getComponentID()));
        if (tabPane.getStyle().equals(TAB_STYLE_SUBMENU)) {
            pane.append("\n<li");
            if (isSelected) {
                pane.append(" class=\"sub-nav-sel\"");
            }
            pane.append(">");
            portletURL.setParameter("newsubtab", "true");
            pane.append("<a href=\"").append(portletURL.toString()).append("\">").append(replaceBlanks(tabPane.getLocalizedText(req, "NEW_SUBTAB"))).append("</a></li>\n");
        }
        if (tabPane.getStyle().equals(TAB_STYLE_MENU)) {
            String selected = "nav-nonsel";
            if (isSelected) {
                selected = "nav-sel";
            }
            pane.append("<li class=\"").append(selected).append("\">");
            portletURL.setParameter("newtab", "true");
            pane.append("<a href=\"").append(portletURL.toString()).append("\"><span>").append(replaceBlanks(tabPane.getLocalizedText(req, "NEW_TAB"))).append("</span></a></li>");
        }

        return pane;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        StringBuffer result = new StringBuffer();
        PortletTabbedPane pane = (PortletTabbedPane) comp;
        if (pane.getCanModify()) {


            result.append("<div style='float:right'>M2</div>");
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_SUBMENU)) {
            result.append(TAB_END_SUBMENU);
            result.append("\n</div> <!--  END LAYOUT NAVIGATION -->\n<div id=\"gridsphere-layout-body\"> <!-- start the main portlets -->\n");
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_MENU)) {
            result.append(TAB_END_MENU);
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_TOPMENU)) {
            result.append(TAB_END_TOPMENU);
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_SIDEMENU)) {
            result.append(TAB_END_SIDEMENU);
        }
        return result;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        StringBuffer buffer = new StringBuffer();
        PortletTabbedPane pane = (PortletTabbedPane) comp;
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_MENU)) {
            buffer.append("\n</div> <!-- END gridsphere-layout-body -->\n");
        }
        if (pane.getStyle().equalsIgnoreCase(TAB_STYLE_SIDEMENU)) {
            buffer.append("\n </div> <!-- END LAYOUT COLUMN -->\n </div> <!-- END LAYOUT ROW --> \n</div> <!-- END LAYOUT BODY --> \n<!-- END MAIN PORTLET AREA -->");
        }
        return buffer;
    }
}