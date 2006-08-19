/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletApplicationSettings.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

import java.io.IOException;
import java.util.Enumeration;

/**
 * The <code>PortletApplicationSettings</code> interface provides the portlet
 * with the application's dynamic configuration. The configuration holds
 * information about the portlet application that is valid per concrete portlet
 * application for all users, and is maintained by the administrator. The
 * portlet can therefore only read the dynamic configuration. Only when the
 * portlet is in <code>CONFIGURE</code> mode, it has write access to the dynamic
 * configuration data
 */
public interface PortletApplicationSettings {

    /**
     * Returns the value of the attribute with the given name, or
     * <code>null</code> if no such attribute exists.
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
    public void removeAttribute(String name);

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value);

    /**
     * Stores all attributes.
     *
     * @throws IOException if the streaming causes an I/O problem
     */
    public void store() throws IOException;

}
