/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Enumeration;

/**
 * The PortletData contains information about the concrete portlet instance.
 * Also, it is through the data that the portlet has access to the personalized data.
 * The portlet can therefore only read the personalization data.
 * Only when the portlet is in EDIT mode, it has write access to the personalization data.
 */
public interface PortletData {

    /**
     * Returns the value of the attribute with the given name, or null if no such attribute exists.
     *
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(String name);

    /**
     * Returns an enumeration of all available attributes names.
     *
     * @return an enumeration of all available attributes names
     */
    public Enumeration getAttributeNames();

    /**
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     */
    public void removeAttribute(String name) throws AccessDeniedException;

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void setAttribute(String name, String value) throws AccessDeniedException;

    /**
     * Stores all attributes.
     */
    public void store() throws AccessDeniedException;

}
