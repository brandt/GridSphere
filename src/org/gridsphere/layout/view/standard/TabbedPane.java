/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
* @version $Id: TabbedPane.java 4496 2006-02-08 20:27:04Z wehrens $
*/

package org.gridsphere.layout.view.standard;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletTab;
import org.gridsphere.layout.PortletTabbedPane;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.TabbedPaneView;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletURI;
import org.gridsphere.portlet.impl.SportletProperties;

import java.util.List;
import java.util.StringTokenizer;

public class TabbedPane extends BaseRender implements TabbedPaneView {

    protected static final StringBuffer TAB_START =
            new StringBuffer("\n<!-- START MODERN TABBED PANE --><div class=\"tab-pane\" ><ul>");

    protected static final StringBuffer TAB_END =
            // unuseful fillup DIV removed
            new StringBuffer("</ul></div><!-- END MODERN TABBED PANE -->\n");    
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
        StringBuffer result = new StringBuffer("&nbsp;");
        StringTokenizer st = new StringTokenizer(title);
        while (st.hasMoreTokens()) {
            result.append(st.nextToken());
            result.append("&nbsp;");
        }
        return result.toString();
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        PortletTabbedPane pane = (PortletTabbedPane)comp;
        if (pane.getStyle().equalsIgnoreCase("sub-menu")) {
           StringBuffer sb = new StringBuffer();
            sb.append("<div class=\"tab-sub-pane\" >");
            sb.append("<ul>\n");
            return sb;
        } else {
            return TAB_START;
        }
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
            pane.append("\n<li>");
            pane.append("<a class=\"tab-sub-inactive\" href=\"");
            pane.append(link).append("&newsubtab=true");
            pane.append("\">");
            pane.append("<span class=\"tab-sub-menu\">");
            pane.append(replaceBlanks("New subtab"));
            pane.append("</span></a>");
            pane.append("</div>");
        } else {
            pane.append("\n<li>");
            pane.append("<a href=\"");
            pane.append(link).append("&newtab=true");
            pane.append("\">");
            pane.append("<span class=\"tab-menu\">");
            pane.append(replaceBlanks("New tab"));
            pane.append("</span></a>");
            pane.append("</li>");
        }
        return pane;
    }


    public StringBuffer doRenderTab(GridSphereEvent event, PortletTabbedPane tabPane, PortletTab tab) {
        // this really creates the individual tabs
        StringBuffer pane = new StringBuffer();
        String link = tab.createTabTitleLink(event);
        String lang = event.getPortletRequest().getLocale().getLanguage();
        String title = tab.getTitle(lang);
        if (title == null) return pane;
        if (tabPane.getStyle().equals("sub-menu")) {
            pane.append("\n<li>");
            if (tab.isSelected()) {
                pane.append("<div class=\"tab-sub-active\">");
                pane.append("<span ");
                pane.append("class=\"tab-sub-menu-active\">");
                pane.append(replaceBlanks(title));
                pane.append("</span>");
                pane.append("</div>");
            } else {
                pane.append("<a class=\"tab-sub-inactive\" href=\"");
                pane.append(link);
                pane.append("\">");
                pane.append("<span class=\"tab-sub-menu\">");
                pane.append(replaceBlanks(title));
                pane.append("</span></a>");
            }
            pane.append("</li>\n");
        } else {
            if (tab.isSelected()) {
                pane.append("\n<li>");
                pane.append("<div class=\"tab-active\">");
                pane.append("<span>");
                pane.append(replaceBlanks(title));
                pane.append("</span>");
                pane.append("</div>");
                pane.append("</li>");
            } else {
                pane.append("\n<li>");
                pane.append("<a href=\"");
                pane.append(link);
                pane.append("\">");
                pane.append("<span class=\"tab-menu\">");
                pane.append(replaceBlanks(title));
                pane.append("</span></a>");
                pane.append("</li>");
            }
        }
        return pane;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent component) {
        // this ends the indivual tabs
        PortletTabbedPane tabPane = (PortletTabbedPane)component;
        StringBuffer pane = new StringBuffer();
        if (tabPane.getStyle().equals("sub-menu")) {
            pane.append("</ul>");
            // unuseful empty DIV commented out
            //pane.append("<div class=\"tab-empty\">&nbsp;</div>");            
            pane.append("</div><!-- END SUB MENU TABBED PANE -->\n");
            return pane;
        } else {
            return TAB_END;
        }
    }

}
