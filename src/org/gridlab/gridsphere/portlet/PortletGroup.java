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

    public final Type PUBLIC = new Type(1);
    public final Type PRIVATE = new Type(2);
    public final Type HIDDEN = new Type(3);

    public class Type {

        private int type = 1;

        public Type() {}

        private Type(int type) {
            this.type = type;
        }

        public static Type getType(int type) {
            if (type == 3) return HIDDEN;
            if (type == 2) return PRIVATE;
            if (type == 1) return PUBLIC;
            throw new IllegalArgumentException("Specified group type: " + type + " doesn't exist!");
        }

        public boolean equals(Object object) {
            if (object != null && (object.getClass().equals(this.getClass()))) {
                Type gtype = (Type) object;
                return type == gtype.getType();
            }
            return false;
        }


        public int getType() {
            return type;
        }
    }

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

    public PortletGroup.Type getType();

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
