/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;


/**
 * The <code>PortletGroup</code> interface describes portlet group used by the
 * portal.
 *
 * @see org.gridlab.gridsphere.portlet.PortletRole
 */
public interface PortletGroup {

    /**
     * Tests to see if this group object is a <code>BASE</code>
     * group that every user is a member of by default.
     *
     * @return <code>true</code> if this group is a <code>BASE</group>,
     * returns <code>false</code> otherwise.
     */
    public boolean isBaseGroup();

    /**
     * Tests to see if this group object is a <code>SUPER</code>
     * group that every super user is a member of by default.
     *
     * @return <code>true</code> if this group is a <code>SUPER</group>,
     * returns <code>false</code> otherwise.
     */
    public boolean isSuperGroup();

    /**
     * Returns the name of this group
     *
     * @return the name of this group
     */
    public String getName();

    /**
     * Returns the id of this group
     *
     * @return the id of this group
     */
    public String getID();

    /**
     * Tests to see if this group is equal to a supplied group
     *
     * @param object a <code>PortletGroup</code> object
     * @return <code>true</code> if groups are equal, <code>false</code>
     * otherwise
     */
    public boolean equals(Object object);

    /**
     * Returns a String representation of this group
     *
     * @return a <code>String</code> representation of this group
     */
    public String toString();

}
