/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.portal;

import java.util.*;

/**
 * A container for the GridSphere portal administrative settings
 */
public class PortalConfigSettings {

    private String oid = null;

    private boolean canCreateAccount = false;

    // @deprecated
    private Set defaultGroups = null;

    private String defaultTheme = null;

    private Map attributes = new HashMap();

    public PortalConfigSettings() {
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setCanUserCreateAccount(boolean canCreateAccount) {
        this.canCreateAccount = canCreateAccount;
    }

    public boolean getCanUserCreateAccount() {
        return canCreateAccount;
    }

    // @deprecated
    public Set getDefaultGroups() {
        return defaultGroups;
    }

    // @deprectated
    public void setDefaultGroups(Set defaultGroups) {
        this.defaultGroups = defaultGroups;
    }

    public void setDefaultTheme(String theme) {
        this.defaultTheme = theme;
    }

    public String getDefaultTheme() {
        return defaultTheme;
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }


    /**
     * Returns the value of the attribute with the given name,s
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }

    /**
     * Sets the value of the attribute with the given name,
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames() {
        return new Hashtable(attributes).keys();
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeValues() {
        return new Hashtable(attributes).elements();
    }
}
