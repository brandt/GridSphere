/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 10, 2003
 * Time: 3:29:09 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.grid.security.credential;

public class CredentialMappingAction {

    public static final CredentialMappingAction ADD = new CredentialMappingAction("add");
    public static final CredentialMappingAction EDIT = new CredentialMappingAction("edit");
    public static final CredentialMappingAction REMOVE = new CredentialMappingAction("remove");

    private String name = null;

    private CredentialMappingAction() {
    }

    private CredentialMappingAction(String name) {
        this.name = name;
    }

    public boolean equals(Object object) {
        if (object instanceof CredentialMappingAction) {
            CredentialMappingAction thatAction = (CredentialMappingAction)object;
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

    public static CredentialMappingAction toCredentialMappingAction(String name) {
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
