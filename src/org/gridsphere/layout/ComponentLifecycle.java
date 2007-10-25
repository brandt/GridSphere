/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout;

import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import java.util.List;

/**
 * The <code>ComponentLifecycle</code> represents the lifecycle methods required by any
 * PortletComponent.
 */
public interface ComponentLifecycle extends Cloneable {

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param req  the portlet request
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list);

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event);

    /**
     * Renders the portlet component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event);

    public Object clone() throws CloneNotSupportedException;

    /**
     * Destroys this portlet component
     */
    public void destroy();

    /**
     * Returns the associated portlet component id
     *
     * @return the portlet component id
     */
    public int getComponentID();

    /**
     * Sets the associated portlet component id
     *
     * @param compId the portlet component id
     */
    public void setComponentID(int compId);

}
