/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Set;


/**
 * The <code>PortletGroup</code> interface describes portlet group used by the
 * portal.
 *
 * @see org.gridlab.gridsphere.portlet.PortletRole
 */
public interface PortletGroup extends Cloneable {

    /**
     * Returns the id of this group
     *
     * @return the id of this group
     */
    public String getID();

    /**
     * Returns the name of this group
     *
     * @return the name of this group
     */
    public String getName();

    /**
     * Returns the description of this group
     *
     * @return the description of this group
     */
    public String getDescription();

    /**
     * Returns the label of this group
     *
     * @return the label of this group
     */
    public String getLabel();

    public Set getPortletRoleList();

    /**
     * Tests to see if this group is equal to a supplied group
     *
     * @param object a <code>PortletGroup</code> object
     * @return <code>true</code> if groups are equal, <code>false</code>
     *         otherwise
     */
    public boolean equals(Object object);

    /**
     * Returns a String representation of this group
     *
     * @return a <code>String</code> representation of this group
     */
    public String toString();

    public boolean isPublic();

    public boolean isCore();
}
