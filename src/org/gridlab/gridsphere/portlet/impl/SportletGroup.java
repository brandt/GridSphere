/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.PortletGroup;

/**
 * <code>SportletGroup</code> is the implementation of <code>PortletGroup</code>
 * Portlet API interface to define portal groups.
 * <p>
 * This implemnetation uses Castor doclets to generate the SQL data bindings
 *
 * @see org.gridlab.gridsphere.portlet.PortletRole
 *
 * @table sportletgroup
 */
public class SportletGroup extends BaseObject implements PortletGroup {

    private static final String SUPER_GROUP = "super";
    private static final String CORE_GROUP = "core";

    /**
     * The <code>BASE</code> PortletGroup is the group that every portal user
     * is a member of by default
     */
    public static final PortletGroup CORE = new SportletGroup(SportletGroup.CORE_GROUP);

    /**
     * The <code>SUPER</code> PortletGroup is the group that only super users of
     * the portal belong to
     */
    public static final PortletGroup SUPER = new SportletGroup(SportletGroup.SUPER_GROUP);

    /**
     * @sql-size 128
     * @sql-name base
     */
    private String Name = new String();

    /**
     * Constructs an instance of SportletGroup
     */
    public SportletGroup() {
        super();
    }

    /**
     * Constructs an instance of SportletGroup with a chosen name
     *
     * @param groupName the name of the group
     */
    public SportletGroup(String groupName) {
        super();
        this.Name = groupName;
    }

    /**
     * Sets the name of the group
     *
     * @param name the group name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Returns the portlet group name
     *
     * @return the portlet group name
     */
    public String getName() {
        return Name;
    }

    /**
     * Returns the group id
     *
     * @return the group id
     */
    public String getID() {
        return getOid();
    }

    /**
     * Sets the group id
     *
     * @param id the group id
     */
    public void setID(String id) {
        setOid(id);
    }

    /**
     * Tests the equality of two groups
     *
     * @param object the <code>PortletGroup</code> to be tested
     * @return <code>true</code> if the groups are equal, <code>false</code>
     * otherwise
     */
    public boolean equals(Object object) {
        if (object != null && (object.getClass().equals(this.getClass()))) {
            PortletGroup portletGroup = (PortletGroup) object;
            return (Name == portletGroup.getName());
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        SportletGroup s = (SportletGroup)super.clone();
        s.Name = this.Name;
        return s;
    }

    /**
     * Returns a unique hashcode
     *
     * @return a unique hash code
     */
    public int hashCode() {
        return Name.hashCode();
    }

    /**
     * Returns the group name
     *
     * @return the group name
     */
    public String toString() {
        return Name;
    }
}
