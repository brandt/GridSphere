/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 10, 2003
 * Time: 3:29:09 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.acl;

public class GroupAction {

    public static final GroupAction ADD = new GroupAction("add");
    public static final GroupAction EDIT = new GroupAction("edit");
    public static final GroupAction REMOVE = new GroupAction("remove");

    private String name = null;

    private GroupAction() {
    }

    private GroupAction(String name) {
        this.name = name;
    }

    public boolean equals(Object object) {
        if (object instanceof GroupAction) {
            GroupAction thatAction = (GroupAction)object;
            String thatName = thatAction.name;
            return this.name.equalsIgnoreCase(thatName);
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name;
    }

    public static GroupAction toGroupAction(String name) {
        if (name.equalsIgnoreCase(ADD.name)) {
            return ADD;
        } else if (name.equalsIgnoreCase(EDIT.name)) {
            return EDIT;
        } else if (name.equalsIgnoreCase(REMOVE.name)) {
           return REMOVE;
        } else {
            throw new IllegalArgumentException("Not a recognized action: " + name);
        }
    }
}
