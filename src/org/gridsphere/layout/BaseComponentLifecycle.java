/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout;


import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import java.util.List;

/**
 * The <code>BaseComponentLifecycle</code> provides an abstract implemetation of the
 * <code>ComponentLifecycle</code> lifecyle methods and is subclassed by the
 * {@link BasePortletComponent}.
 */
public abstract class BaseComponentLifecycle implements ComponentLifecycle {

    protected int COMPONENT_ID = 0;
    protected String componentIDStr = "0";

    protected BaseComponentLifecycle() {
    }

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list) {
        this.COMPONENT_ID = list.size();
        componentIDStr = String.valueOf(COMPONENT_ID);
        return list;
    }

    /**
     * Destroys this portlet component
     */
    public void destroy() {
    }

    /**
     * Returns the associated portlet component id
     *
     * @return the portlet component id
     */
    public int getComponentID() {
        return COMPONENT_ID;
    }

    /**
     * Returns the associated portlet component id
     *
     * @return the portlet component id
     */
    public String getComponentIDAsString() {
        return componentIDStr;
    }

    /**
     * Sets the associated portlet component id
     *
     * @param compId the portlet component id
     */
    public void setComponentID(int compId) {
        this.COMPONENT_ID = compId;
    }

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event) {
        PortletRequest req = event.getActionRequest();
        req.setAttribute(SportletProperties.COMPONENT_ID, componentIDStr);
    }

    /**
     * Renders the portlet component
     *
     * @param event a gridsphere event
     */
    public abstract void doRender(GridSphereEvent event);

    public Object clone() throws CloneNotSupportedException {
        BaseComponentLifecycle b = (BaseComponentLifecycle) super.clone();
        b.COMPONENT_ID = this.COMPONENT_ID;
        b.componentIDStr = this.componentIDStr;
        return b;
    }

}
