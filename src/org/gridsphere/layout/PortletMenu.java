package org.gridsphere.layout;

import org.gridsphere.layout.event.PortletTabListener;
import org.gridsphere.layout.view.TabbedPaneView;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.portlet.impl.SportletProperties;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import java.io.Serializable;
import java.util.List;

/**
 * The <code>PortletTabbedPane</code> represents the visual portlet tabbed pane interface
 * and is a container for a {@link org.gridsphere.layout.PortletTab}.
 */
public class PortletMenu extends PortletNavMenu implements Serializable, PortletTabListener, Cloneable {


    private transient TabbedPaneView menuView = null;

    /**
     * Constructs an instance of PortletTabbedPane
     */
    public PortletMenu() {
    }


    /**
     * Initializes the portlet tabbed pane component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see org.gridsphere.layout.ComponentIdentifier
     */
    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list) {

        menuView = (TabbedPaneView)getRenderClass(req, "Menu");

        return super.init(req, list);
    }

    /**
     * Renders the portlet frame component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event) {
        //super.doRender(event);
        StringBuffer pane = new StringBuffer();
        RenderRequest req = event.getRenderRequest();


        //log.debug("in tabbed pane: my comp is=" + componentIDStr);
        pane.append(menuView.doStart(event, this));

        PortletTab tab;
        List tabs = getPortletTabs();
        for (int i = 0; i < tabs.size(); i++) {
            tab = (PortletTab) tabs.get(i);
            String tabRole = tab.getRequiredRole();
            if (tabRole.equals("") || (req.isUserInRole(tabRole))) {
                pane.append(menuView.doRenderTab(event, this, tab));
            } else {
                // if role is < required role we try selecting the next possible tab
                //System.err.println("in PortletTabbedPane menu: role is < required role we try selecting the next possible tab");
                if (tab.isSelected()) {
                    int index = (i + 1);
                    if (index < tabs.size()) {
                        PortletTab newtab = (PortletTab) tabs.get(index);
                        setSelectedPortletTab(newtab);
                    }
                }
            }
        }


        if (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null) {
            pane.append(menuView.doRenderEditTab(event, this, false));
        }

        pane.append(menuView.doEndBorder(event, this));


        // render the selected tab
        if (!tabs.isEmpty()) {
            PortletTab selectedTab = getSelectedTab();
            //System.err.println("selected tab= " + selectedTab.toString());
            if (selectedTab != null) {
                selectedTab.doRender(event);
                pane.append(selectedTab.getBufferedOutput(req));
            }
        }

        pane.append(menuView.doEnd(event, this));
        

        setBufferedOutput(req, pane);

    }


    public Object clone() throws CloneNotSupportedException {
        return (PortletMenu) super.clone();
    }

}
