/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: BaseComponentLifecycle.java 4986 2006-08-04 09:54:38Z novotny $
 */
package org.gridsphere.layout;


import org.gridsphere.portlet.jsrimpl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import java.io.IOException;
import java.util.List;

/**
 * The <code>BaseComponentLifecycle</code> provides an abstract implemetation of the
 * <code>ComponentLifecycle</code> lifecyle methods and is subclassed by the
 * {@link BasePortletComponent}.
 */
public abstract class BaseComponentLifecycle implements ComponentLifecycle {

    protected int COMPONENT_ID = 0;
    protected String componentIDStr = "0";

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(PortletRequest req, List list) {
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
     * Returns the associated portlet component id variable
     *
     * @return the portlet component id variable
     */
    public String getComponentIDVar(PortletRequest req) {
        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        return compVar;
    }

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event) {
        PortletRequest req = event.getActionRequest();
        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        req.setAttribute(compVar, componentIDStr);
    }

    /**
     * Renders the portlet component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException            if an I/O error occurs during rendering
     */
    public abstract void doRender(GridSphereEvent event);

    public Object clone() throws CloneNotSupportedException {
        BaseComponentLifecycle b = (BaseComponentLifecycle) super.clone();
        b.COMPONENT_ID = this.COMPONENT_ID;
        b.componentIDStr = this.componentIDStr;
        return b;
    }

}
