/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.event.PortletTabEvent;
import org.gridlab.gridsphere.layout.event.PortletTabListener;
import org.gridlab.gridsphere.layout.event.impl.PortletTabEventImpl;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A <code>PortletTab</code> represents the visual tab graphical interface and is contained
 * by a {@link PortletTabbedPane}. A tab contains a title and any additional
 * portlet component. Generally, it is either a {@link PortletPanel} or another
 * tabbed pane if a double level tabbed pane is desired.
 */
public class PortletTab extends BasePortletComponent implements Serializable, Cloneable {

    private String title = "";
    private boolean selected = false;
    private PortletComponent portletComponent = null;
    private List listeners = new ArrayList();

    /**
     * Constructs an instance of PortletTab
     */
    public PortletTab() {
    }

    /**
     * Constructs an instance of PortletTab with the supplied title and
     * portlet component.
     *
     * @param title the title of the portlet tab
     * @param portletComponent any portlet component to represent beneath the tab
     */
    public PortletTab(String title, PortletComponent portletComponent) {
        this.title = title;
        this.portletComponent = portletComponent;
    }

    /**
     * Returns the portlet tab title
     *
     * @return the portlet tab title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the portlet tab title
     *
     * @param title the portlet tab title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *  Creates the portlet tab title links that are rendered by the
     * {@link PortletTabbedPane}
     *
     * @param event the gridsphere event
     */
    public String createTabTitleLink(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        req.setAttribute(GridSphereProperties.COMPONENT_ID, componentIDStr);
        PortletURI portletURI = res.createURI();
        portletURI.addParameter(GridSphereProperties.COMPONENT_ID, componentIDStr);
        //portletURI.addParameter(GridSphereProperties.PORTLETTAB, title);
        return portletURI.toString();
    }

    /**
     * Sets the selected flag used in determining if this tab is selected and
     * should be rendered
     *
     * @param selected the selected flag is true if this tag is currently selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns the selected flag used in determining if this tab is selected and
     * hence rendered
     *
     * @return true if the tab is selected, false otherwise
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the concrete portlet component contained by the portlet tab
     *
     * @param portletComponent a concrete portlet component instance
     */
    public void setPortletComponent(PortletComponent portletComponent) {
        this.portletComponent = portletComponent;
    }

    /**
     * Returns the concrete portlet component contained by the portlet tab
     *
     * @return the concrete portlet component instance conatined by this tab
     */
    public PortletComponent getPortletComponent() {
        return portletComponent;
    }

    /**
     * Adds a portlet tab listener used to receive notification when a tab event occurs
     *
     * @param listener a portlet tab listener
     */
    public void addPortletTabListener(PortletTabListener listener) {
        listeners.add(listener);
    }

    /**
     * Initializes the portlet tab. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(List list) {
        list = super.init(list);
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        return portletComponent.init(list);
    }

    /**
     * Performs an action on this portlet tab component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.actionPerformed(event);
        PortletTabListener tabListener;
        PortletTabEvent tabEvent = new PortletTabEventImpl(this, PortletTabEvent.Action.TAB_SELECTED, COMPONENT_ID);
        for (int i = 0; i < listeners.size(); i++) {
            tabListener = (PortletTabListener) listeners.get(i);
            tabListener.handlePortletTabEvent(tabEvent);
        }
    }

    /**
     * Renders the portlet tab component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        portletComponent.doRender(event);
    }

    public Object clone() throws CloneNotSupportedException {
        PortletTab t = (PortletTab)super.clone();
        t.portletComponent = (this.portletComponent == null) ? null : (PortletComponent)this.portletComponent.clone();
        t.title = this.title;
        t.selected = this.selected;
        return t;
    }
}
