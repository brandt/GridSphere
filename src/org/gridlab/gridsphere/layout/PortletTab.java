/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.layout.impl.PortletTabEventImpl;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class PortletTab extends BasePortletLifecycle {

    private String title;
    private boolean selected = false;
    private PortletComponent portletComponent;
    private List listeners = new ArrayList();

    public PortletTab() {}

    public PortletTab(String title, PortletComponent portletComponent) {
        this.title = title;
        this.portletComponent = portletComponent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setPortletComponent(PortletComponent portletComponent) {
        this.portletComponent = portletComponent;
    }

    public PortletComponent getPortletComponent() {
        return portletComponent;
    }

    public List init(List list) {
        COMPONENT_ID = list.size();
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletLifecycle(this);
        compId.setComponentID(list.size());
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        return portletComponent.init(list);
    }

    public void addPortletTabListener(PortletTabListener listener) {
        listeners.add(listener);
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletTabListener tabListener;
        PortletTabEvent tabEvent = new PortletTabEventImpl(this, PortletTabEvent.Action.TAB_SELECTED, COMPONENT_ID);
        for (int i = 0; i < listeners.size(); i++) {
            tabListener = (PortletTabListener)listeners.get(i);
            tabListener.handlePortletTabEvent(tabEvent);
        }
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        portletComponent.doRender(event);
    }

}
