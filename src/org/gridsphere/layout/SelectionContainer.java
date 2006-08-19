package org.gridsphere.layout;

import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portletcontainer.GridSphereEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>PortletContainer</code> acts a container for a list of one or more portlet components
 */
public class SelectionContainer extends BasePortletComponent implements
        Serializable, Cloneable {

    protected List components = new ArrayList();
    protected String activeLabel = null;
    protected String defaultLabel = null;

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see org.gridsphere.layout.ComponentIdentifier
     */
    public List init(PortletRequest req, List list) {
        list = super.init(req, list);
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);


        PortletComponent p;
        for (int i = 0; i < components.size(); i++) {
            p = (PortletComponent)components.get(i);
            if (p.getLabel().equals(defaultLabel)) activeLabel = defaultLabel;
            // invoke init on each component
            list = p.init(req, list);
            p.setParentComponent(this);
        }

        if (activeLabel == null) activeLabel = ((PortletComponent)components.get(0)).getLabel();
        return list;
    }

    /**
     * Sets the list of new portlet component to the layout
     *
     * @param components an ArrayList of portlet components
     */
    public void setPortletComponents(List components) {
        this.components = components;
    }

    /**
     * Returns a list containing the portlet components in this layout
     *
     * @return a list of portlet components
     */
    public List getPortletComponents() {
        return components;
    }

    public String getDefaultLabel() {
        return defaultLabel;
    }

    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

    /**
     * Destroys this portlet component
     */
    public void destroy() {
        components = null;
    }

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event) {
        super.actionPerformed(event);
        updateActiveLabel(event);
    }

    /**
     * Renders the portlet components in the frame layout
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        PortletComponent p;
        for (int i = 0; i < components.size(); i++) {
            p = (PortletComponent)components.get(i);
            if (p.getLabel().equals(activeLabel)) {
                p.doRender(event);
                setBufferedOutput(event.getPortletRequest(), p.getBufferedOutput(event.getPortletRequest()));
                return;
            }
        }
    }

    protected void updateActiveLabel(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        String selectedLabel = req.getParameter("select");
        if (selectedLabel != null) {
            PortletComponent comp = null;
            for (int i = 0; i < components.size(); i++) {
                comp = (PortletComponent)components.get(i);
                if (comp.getLabel().equals(selectedLabel)) {
                    activeLabel = selectedLabel;
                    return;
                }
            }
        }
        activeLabel = defaultLabel;
    }

    public Object clone() throws CloneNotSupportedException {
        SelectionContainer f = (SelectionContainer) super.clone();

        f.components = new ArrayList(components.size());
        for (int i = 0; i < components.size(); i++) {
            PortletComponent comp = (PortletComponent) components.get(i);
            f.components.add(comp.clone());
        }
        return f;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("activeLabel=").append(activeLabel);
        sb.append("defaultLabel=").append(defaultLabel);
        return sb.toString();
    }

}
