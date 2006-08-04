/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
* @version $Id: TabbedPane.java 4496 2006-02-08 20:27:04Z wehrens $
*/

package org.gridlab.gridsphere.layout.view.brush;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletTab;
import org.gridlab.gridsphere.layout.PortletTabbedPane;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.TabbedPaneView;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import java.util.List;
import java.util.StringTokenizer;

public class TabbedPane extends BaseRender implements TabbedPaneView {

    protected static final StringBuffer TAB_START_MENU =
            new StringBuffer("\n<!-- LAYOUT NAVIGATION -->\n <div id=\"gridsphere-layout-navigation\"> \n<ul id=\"gridsphere-nav\"><li id=\"gridsphere-nav-border-left\">&nbsp;</li>\n");
    protected static final StringBuffer TAB_START_SUBMENU =
            new StringBuffer("\n<ul id=\"gridsphere-sub-nav\"><li id=\"gridsphere-sub-nav-border-left\">&nbsp;</li>\n");

    protected static final StringBuffer TAB_END_MENU =
            new StringBuffer("\n<li id=\"gridsphere-nav-border-right\">&nbsp;</li></ul> <!-- END NAVIGATION (SUB) MENU -->\n");
    protected static final StringBuffer TAB_END_SUBMENU =
            new StringBuffer("\n<li id=\"gridsphere-sub-nav-border-right\">&nbsp;</li></ul> <!-- END NAVIGATION (SUB) MENU -->\n");

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
     */
    protected static String[] createTabLinks(GridSphereEvent event, PortletTabbedPane pane) {
        // Make tab links
        String[] tabLinks = new String[pane.getPortletTabs().size()];
        List tabs = pane.getPortletTabs();
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
        if (pane.getStyle().equalsIgnoreCase("sub-menu")) {
            result.append(TAB_START_SUBMENU);
        }
        if (pane.getStyle().equals("menu")) {
            result.append(TAB_START_MENU);
        }

        return result;
    }

    public StringBuffer doRenderTab(GridSphereEvent event, PortletTabbedPane tabPane, PortletTab tab) {
        // this really creates the individual tabs
        StringBuffer pane = new StringBuffer();
        String link = tab.createTabTitleLink(event);
        String lang = event.getPortletRequest().getLocale().getLanguage();
        String title = tab.getTitle(lang);

        if (tabPane.getStyle().equals("sub-menu")) {
            pane.append("\n<li");
            if (tab.isSelected()) {
                pane.append(" class=\"sub-nav-sel\"");
            }
            pane.append(">");
            pane.append("<a href=\"").append(link).append("\">").append(replaceBlanks(title)).append("</a></li>\n");
        }
        if (tabPane.getStyle().equals("menu")) {
            String selected = "nav-nonsel";
            if (tab.isSelected()) {
                selected = "nav-sel";
            }
            pane.append("<li class=\"").append(selected).append("\">");
            pane.append("<a href=\"").append(link).append("\"><span>").append(replaceBlanks(title)).append("</span></a></li>");
        }
        return pane;
    }

    public StringBuffer doRenderEditTab(GridSphereEvent event, PortletTabbedPane tabPane, boolean isSelected) {
        PortletResponse res = event.getPortletResponse();
        PortletRequest req = event.getPortletRequest();
        PortletURI portletURI = res.createURI();
        String extraQuery = (String)req.getAttribute(SportletProperties.EXTRA_QUERY_INFO);
        String link = portletURI.toString() + extraQuery;
        StringBuffer pane = new StringBuffer();

        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        String cid = (String)req.getAttribute(compVar);

        if (tabPane.getStyle().equals("sub-menu")) {
            pane.append("\n<li");
            if (isSelected) {
                pane.append(" class=\"sub-nav-sel\"");
            }
            pane.append(">");
            pane.append("<a href=\"").append(link).append("&newsubtab=true").append("\">").append(replaceBlanks("New subtab")).append("</a></li>\n");
        }
        if (tabPane.getStyle().equals("menu")) {
            String selected = "nav-nonsel";
            if (isSelected) {
                selected = "nav-sel";
            }
            pane.append("<li class=\"").append(selected).append("\">");
            pane.append("<a href=\"").append(link).append("&newtab=true").append("\"><span>").append(replaceBlanks("New tab")).append("</span></a></li>");
        }

        return pane;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        StringBuffer result = new StringBuffer();
        PortletTabbedPane pane = (PortletTabbedPane) comp;
        if (pane.getStyle().equalsIgnoreCase("sub-menu")) {
            result.append(TAB_END_SUBMENU);
            result.append("\n</div> <!--  END LAYOUT NAVIGATION -->\n<div id=\"gridsphere-layout-body\"> <!-- start the main portlets -->\n");
        }
        if (pane.getStyle().equals("menu")) {
            result.append(TAB_END_MENU);
        }
        return result;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        StringBuffer buffer = new StringBuffer();
        PortletTabbedPane pane = (PortletTabbedPane) comp;
        if (pane.getStyle().equalsIgnoreCase("menu")) {
            buffer.append("\n</div> <!-- END gridsphere-layout-body -->\n");
        }
        return buffer;
    }
}
