/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portletcontainer.impl.descriptor.AccessRestrictions;
import org.gridlab.gridsphere.portlet.PortletRole;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The <code>ConcretePortletConfig</code> provides concrete portlet
 * configuration information.
 */
public interface ConcretePortletConfig {

    /**
     * The <code>Scope</code> defines the portlets visibility to the portlet container. If the scope is
     * defined to be <code>PRIVATE</code> than the portlet container will not provide any information about this portlet
     * to non-members, if it is <code>PUBLIC</code> (the default) than the portlet conatiner can advertise information
     * about this portlet.
     */
    public class Scope {

        private static int PUBLIC_SCOPE = 2;
        private static int PRIVATE_SCOPE = 7;

        public static final Scope PUBLIC = new Scope(PUBLIC_SCOPE);
        public static final Scope PRIVATE = new Scope(PRIVATE_SCOPE);

        private int id = PUBLIC_SCOPE;

        /**
         * Constructor not available to clients.
         *
         * @param id a scope id
         */
        private Scope(int id) {
            this.id = id;
        }

        /**
         * Returns an instance of Scope from a supplied <code>String</code>
         * representation
         *
         * @param scope the scope expressed as a <code>String</code>
         * @return the <code>Scope</code> matching the provided String
         * @throws IllegalArgumentException if the provided string cannot
         * be parsed
         */
        public static Scope toScope(String scope) throws IllegalArgumentException {
            if (scope.equalsIgnoreCase("PRIVATE")) return PRIVATE;
            if (scope.equalsIgnoreCase("PUBLIC")) return PUBLIC;
            throw new IllegalArgumentException("Unable to find suitable scope for: " + scope);
        }

        /**
         * Returns the scope id
         *
         * @return the scope id
         */
        public int getID() {
            return id;
        }

        /**
         * Returns a <code>String</code> representation of the Scope
         *
         * @return a <code>String</code> representation of the Scope
         */
        public String toString() {
            if (id == PUBLIC_SCOPE) return "PUBLIC";
            if (id == PRIVATE_SCOPE) return "PRIVATE";
            return "Scope Unknown";
        }

        /**
         * Returns a unique hash code
         *
         * @return a unique hash code
         */
        public int hashCode() {
            return id;
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
                Scope scope = (Scope) object;
                return (scope.getID() == getID());
            }
            return false;
        }

    }

    /**
     * Returns the default locale of a portlet
     *
     * @return the default locale of the portlet
     */
    public String getDefaultLocale();

    /**
     * Sets the default locale of a portlet
     *
     * @param defaultLocale the default locale of the portlet
     */
    public void setDefaultLocale(String defaultLocale);

    /**
     * Returns the language info of a portlet
     *
     * @return language info of the portlet
     */
    public List getLanguageList();

    /**
     * Sets the language info of a portlet
     *
     * @param languageInfo the language info of the portlet
     */
    public void setLanguageList(ArrayList languageList);

    /**
     * Returns the name of the portlet
     *
     * @returns the name of the portlet
     */
    public String getName();

    /**
     * sets the name of the portlet
     *
     * @param name the name of the portlet
     */
    public void setName(String name);

    /**
     * Returns the configuration attributes of the portlet
     *
     * @returns the configuration attributes of the portlet
     */
    public Hashtable getConfigAttributes();

    /**
     * Sets the configuration attributes of the portlet
     *
     * @param the configuration attributes of the portlet
     */
    public void setConfigAttributes(Hashtable configAttrs);

    /**
     * Returns the accessibility scope of this portlet
     *
     * @return the accessibility scope of this portlet
     */
    public ConcretePortletConfig.Scope getConcretePortletScope();

    /**
     * Sets the accessibility scope of this portlet
     *
     * @param scope the accessibility scope of this portlet
     */
    public void setConcretePortletScope(ConcretePortletConfig.Scope scope);

    /**
     * Returns the required portlet role necessary to access this portlet
     *
     * @return the required portlet role necessary to access this portlet
     */
    public PortletRole getRequiredRole();

    /**
     * Sets the required portlet role necessary to access this portlet
     *
     * @param role the required portlet role necessary to access this portlet
     */
    public void setRequiredRole(PortletRole role);

}
