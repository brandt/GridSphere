/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: AccessRestrictions.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl.descriptor;

import org.gridsphere.portletcontainer.ConcretePortletConfig;


/**
 * The <code>AccessRestrictions</code> class is responsible for specifying
 * what access rights are required to access this portlet. Visibility/Scope
 * can be either <code>PUBLIC</code> (information about this portlet is made
 * available to all) or <code>PRIVATE</code> implying that only authorzed
 * users of this portlet can view information about it.
 * <p/>
 * <code>AccessRestrictions</code> also determnines which roles can have access to
 * this portlet.
 */
public class AccessRestrictions {

    private String roleName = "";
    private String visibility = ConcretePortletConfig.Scope.PUBLIC.toString();

    /**
     * Constructs an instance of AccessRestrictions
     */
    public AccessRestrictions() {
    }

    /**
     * Intended for use only by Castor. Clients hould use #setScope instead.
     * <p/>
     * Sets the visibility of this portlet. Possible values are <code>PUBLIC</code>
     * meaning that anyone can view portlet meta-information, or <code>PRIVATE</code>
     * which restricts information about this portlet to those with proper access
     * determined
     *
     * @param visibility the visibility of this portlet, either <code>PUBLIC</code>
     *                   or <code>PRIVATE</code>
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    /**
     * Intended for use only by Castor. Clients should use #getScope instead.
     * <p/>
     * Returns the visibility of this portlet. Possible values are <code>PUBLIC</code>
     * meaning that anyone can view portlet meta-information, or <code>PRIVATE</code>
     * which restricts information about this portlet to those with proper access
     * determined
     *
     * @return the visibility of this portlet, either <code>PUBLIC</code>
     *         or <code>PRIVATE</code>
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * Returns the visibility of this portlet. If no scope was specified in
     * the portlet descriptor, <code>ConcretePortlet.Scope.PUBLIC</code>
     * will be returned
     *
     * @return scope the visibility scope of this concrete portlet
     */
    public ConcretePortletConfig.Scope getScope() {
        try {
            return ConcretePortletConfig.Scope.toScope(visibility);
        } catch (Exception e) {
            return ConcretePortletConfig.Scope.PUBLIC;
        }
    }

    /**
     * Sets the visibility of this concrete portlet
     *
     * @param scope the concrete portlet scope of this concrete portlet
     */
    public void setScope(ConcretePortletConfig.Scope scope) {
        this.visibility = scope.toString();
    }

    /**
     * Internal method intended for use by Castor. Clients should use
     * #setSportletRole instead
     * <p/>
     * Sets the role required to have access to this portlet
     *
     * @param roleName a role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Internal method intended for use by Castor. Clients should use
     * #getSportletRole instead
     * <p/>
     * Returns the portlet role required to have access to this portlet
     *
     * @return the required role name
     */
    public String getRoleName() {
        return roleName;
    }

}
