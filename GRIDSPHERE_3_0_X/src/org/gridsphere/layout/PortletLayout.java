/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout;

import java.util.List;


/**
 * The <code>PortletLayout</code> is responsible for constructing a layout appropriate
 * to the user's layout preferences.
 * <p/>
 * The <code>PortletFrameLayout</code> provides an abstract implementation of a generic
 * container
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of rows and columns.
 */
public interface PortletLayout extends PortletComponent {

    /**
     * Sets the list of new portlet component to the layout
     *
     * @param components an ArrayList of portlet components
     */
    public void setPortletComponents(List<PortletComponent> components);

    /**
     * Returns a list containing the portlet components in this layout
     *
     * @return a list of portlet components
     */
    public List<PortletComponent> getPortletComponents();

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


