/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import java.io.IOException;
import java.util.List;

/**
 * The <code>BaseComponentLifecycle</code> provides an abstract implemetation of the
 * <code>ComponentLifecycle</code> lifecyle methods and is subclasses by the
 * {@link BasePortletComponent}.
 */
public abstract class BaseComponentLifecycle implements ComponentLifecycle {

    protected int COMPONENT_ID = -1;
    protected String componentIDStr = "-1";

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(List list) {
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
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletRequest req = event.getPortletRequest();
        req.setAttribute(GridSphereProperties.COMPONENT_ID, componentIDStr);
    }

    /**
     * Renders the portlet component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public abstract void doRender(GridSphereEvent event) throws PortletLayoutException, IOException;

}
