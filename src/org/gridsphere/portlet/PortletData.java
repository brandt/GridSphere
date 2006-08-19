/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletData.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

import java.io.IOException;
import java.util.Enumeration;

/**
 * The <code>PortletData</code> contains information about the concrete portlet instance.
 * Also, it is through the data that the portlet has access to the personalized data.
 * The portlet can therefore only read the personalization data.
 * Only when the portlet is in <code>EDIT</code> mode, it has write access to
 * the personalization data.
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
     * Returns an iterator of all available attributes names.
     *
     * @return an iterator of all available attributes names
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
     * @throws IOException if the store failed
     */
    public void store() throws IOException;

}
