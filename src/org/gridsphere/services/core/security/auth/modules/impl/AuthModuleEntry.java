package org.gridsphere.services.core.security.auth.modules.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: AuthModuleEntry.java 4496 2006-02-08 20:27:04Z wehrens $
 */
public class AuthModuleEntry {

    private String oid = null;
    protected String moduleClassName = "";
    protected Map attributes = new HashMap();
    protected String userid = "";

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setModuleClassName(String moduleClassName) {
        this.moduleClassName = moduleClassName;
    }

    public String getModuleClassName() {
        return moduleClassName;
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
    public Object getAttribute(String name) {
        return attributes.get(name);
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

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

}
