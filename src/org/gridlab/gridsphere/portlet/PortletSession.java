/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;


/**
 * The PortletSession holds the user-specific data that the portlet needs to
 * personalize the one global portlet instance. Together with the portlet,
 * the portlet session constitutes the concrete portlet instance.
 */
public interface PortletSession extends HttpSession {

    /**
     * Returns the point of time that this session was created.
     * Essentially, this will also be the time when the user logged in.
     * The time is returned as the number of milliseconds since January 1, 1970 GMT.
     *
     * @return the time of creation
     */
    public long getCreationTime();

    /**
     * Returns the point of time that this session was last accessed.
     * The time is returned as the number of milliseconds since January 1, 1970 GMT.
     *
     * @return the time of the last access
     */
    public long getLastAccessedTime();

    /**
     * Associates an attribute with the given name and value with this session.
     * If a portlet needs to communicate information to embedded servlets or JSP,
     * this methods can used carry the information along.
     *
     * The portlet provider should take care that the the namespace of attribute
     * names is not unnecessarily polluted. It is recommended to prefix all attributes
     * the package and class name of the portlet that makes use of this method.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, Object value);

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name);

    /**
     * Returns an enumeration of names of all attributes available to this session.
     * This method returns an empty enumeration if the session has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames();

    /**
     * Removes the attribute with the given name.
     *
     * @param name the name of attribute to be removed
     */
    public void removeAttribute(String name);

}
