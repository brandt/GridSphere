/*
 * Created by Dmitry Gavrilov 05.04.2005
 */
package org.gridsphere.portlet;

import java.io.Serializable;
import java.security.Principal;

/**
 * This class represents a very simple implementation of the
 * <code>java.security.Principal</code> interface.
 * 
 * @author Dmitry Gavrilov
 */
public class UserPrincipal implements Principal, Serializable {

    /* A <code> serialVersionUID </code> value. */
    private static final long serialVersionUID = 3546647607158846512L;

    /* A name of this principal. */
    private String principalName = null;

    /**
     * Constructs a <code>UserPrincipal</code> object with the given name.
     * 
     * @param userName the user name
     *            a name of this principal.
     * @throws IllegalArgumentException
     *             if the name of principal is null.
     */
    public UserPrincipal(String userName) {
      principalName = userName;
    }

    /**
     * Returns a principal's name.
     * 
     * @return a principal's name.
     */
    public String getName() {
        return principalName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof UserPrincipal))
            return false;
        UserPrincipal other = (UserPrincipal) obj;
        return principalName.equals(other.principalName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return principalName.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return principalName;
    }

}
