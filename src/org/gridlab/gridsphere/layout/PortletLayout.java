/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.ArrayList;
import java.util.List;


/**
 * The <code>PortletLayout</code> is responsible for constructing a layout appropriate
 * to the user's layout preferences.
 * <p>
 * The <code>PortletFrameLayout</code> provides an abstract implementation of a generic
 * container
 * The <code>PortletGridLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of rows and columns.
 */
public interface PortletLayout extends ComponentLifecycle {

    /**
     * Sets the list of new portlet component to the layout
     *
     * @param components an ArrayList of portlet components
     */
    public void setPortletComponents(ArrayList components);

    /**
     * Returns a list containing the portlet components in this layout
     *
     * @return a list of portlet components
     */
    public List getPortletComponents();

    /**
     * Adds a new portlet component to the layout
     *
     * @param component a portlet component
     */
    public void addPortletComponent(PortletComponent component);

    /**
     * Removes a new portlet component to the layout
     *
     * @param component a portlet component
     */
    public void removePortletComponent(PortletComponent component);

}


