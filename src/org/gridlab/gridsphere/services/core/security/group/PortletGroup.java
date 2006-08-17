/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id: PortletGroup.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.services.core.security.group;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;


/**
 * The <code>PortletGroup</code> interface describes portlet group used by the
 * portal.
 *
 * @see org.gridlab.gridsphere.portlet.PortletRole
 */
public class PortletGroup implements Serializable, Cloneable {

    private String oid = null;
    private String name = "";
    private boolean isPublic = true;
    private String description = "";
    private boolean isCore = false;

    private int type = Type.PUBLIC.getType();

    private Set portletRoleList = new HashSet();

    public static class Type implements Serializable, Cloneable {

        public static final Type PUBLIC = new Type(1);
        public static final Type PRIVATE = new Type(2);
        public static final Type HIDDEN = new Type(3);

        private String oid = null;

        private int type = 1;

        public Type() {}

        public static Type getType(String groupType) {
            if (groupType.toUpperCase().equals("PUBLIC")) return PUBLIC;
            if (groupType.toUpperCase().equals("PRIVATE")) return PRIVATE;
            if (groupType.toUpperCase().equals("HIDDEN")) return HIDDEN;
            throw new IllegalArgumentException("Unknown group type specified: " + groupType);
        }

        public static Type getType(int groupType) {
            if (groupType == 1) return PUBLIC;
            if (groupType == 2) return PRIVATE;
            if (groupType == 3) return HIDDEN;
            throw new IllegalArgumentException("Unknown group type specified: " + groupType);
        }

        private Type(int type) {
            this.type = type;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public Object clone() throws CloneNotSupportedException {
            Type m = (Type) super.clone();
            m.type = this.type;
            return m;
        }

        public boolean equals(Object o) {
            if ((o != null) && (o instanceof Type)) {
                return (this.type == ((Type) o).getType());
            }
            return false;
        }

        public int hashCode() {
            return type;
        }

        public String toString() {
            return "" + type;
        }

        private Object readResolve() {
            Type m = Type.PUBLIC;
            switch (type) {
                case 1:
                    m = Type.PUBLIC;
                    break;
                case 2:
                    m = Type.PRIVATE;
                    break;
                case 3:
                    m = Type.HIDDEN;
                    break;
            }
            return m;
        }

    }

    /**
     * Constructs an instance of SportletGroup
     */
    public PortletGroup() {}

    /**
     * Constructs an instance of SportletGroup with a chosen name
     *
     * @param groupName the name of the group
     */
    public PortletGroup(String groupName) {
        super();
        if (groupName == null) name = "Unknown Group";
        this.name = groupName;
    }

    public PortletGroup(String groupName, String groupDescription) {
        super();
        if (groupName == null) name = "Unknown Group";
        this.name = groupName;
        this.description = groupDescription;
    }

    public Set getPortletRoleList() {
        return portletRoleList;
    }

    public void setPortletRoleList(Set portletRoleList) {
        this.portletRoleList = portletRoleList;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Sets the name of the group
     *
     * @param name the group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the portlet group name
     *
     * @return the portlet group name
     */
    public String getName() {
        return name;
    }

    public boolean isCore() {
        return isCore;
    }

    public void setCore(boolean core) {
        isCore = core;
    }

    public boolean getCore() {
        return isCore;
    }

    /**
     * Sets the description of the group
     *
     * @param description the group description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the portlet group description
     *
     * @return the portlet group description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the portlet group label
     *
     * @return the portlet group label
     */
    public String getLabel() {
        String label = null;
        if (name.equals("")) {
            label = "";
        } else {
            label = name.substring(0, 1).toUpperCase()
                    + name.substring(1);
        }
        return label;
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
     *         otherwise
     */
    public boolean equals(Object object) {
        if (object != null && (object.getClass().equals(this.getClass()))) {
            PortletGroup portletGroup = (PortletGroup) object;
            return name.equals(portletGroup.getName());
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        PortletGroup s = (PortletGroup) super.clone();
        s.name = this.name;
        return s;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean getPublic() {
        return isPublic;
    }

    public void setType(Type type) {
        this.type = type.getType();
    }

    public Type getType() {
        return Type.getType(type);
    }

    /**
     * @deprecated
     *
     * @param type the group type
     */
    public void setGroupType(int type) {
        this.type = type;
    }

    /**
     * @deprecated
     *
     * @return the group type
     */
    public int getGroupType() {
        return type;
    }

    /**
     * Returns a unique hashcode
     *
     * @return a unique hash code
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns the group name
     *
     * @return the group name
     */
    public String toString() {
        return name;
    }
}
