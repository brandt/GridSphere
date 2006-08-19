/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Capability.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

/**
 * Instances of the <code>Capability</code> class correspond to particular properties which can be assigned to the client devices.
 * The class has only a private constructor, so that it is not possible to dynamically create objects of this class
 * from outside the class. A set of predefined isntances (ie. capabilities) is provided.
 */
public class Capability {

    public static final int HTML_2_0 = 0;

    public static final int HTML_3_0 = 1;

    public static final int HTML_3_2 = 2;

    public static final int HTML_4_0 = 3;

    public static final int HTML_JAVA = 4;

    public static final int HTML_JAVASCRIPT = 5;

    public static final int HTML_NESTED_TABLE = 6;

    public static final int HTML_FRAME = 7;

    public static final int HTML_IFRAME = 8;

    public static final int HTML_CSS = 9;

    public static final int WML_1_1 = 10;

    public static final int WML_1_2 = 11;

    public static final int WML_TABLE = 12;

    /**
     * Capability constructor cannot be instantiated
     */
    private Capability() {

    }

    /**
     * Returns a hash code for this capability.
     *
     * @return the hash code
     */
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns whether this and the given object represent the same capability.
     *
     * @param object the object to compare with
     * @return true if the objects represent the same capability, false otherwise
     */
    public boolean equals(Object object) {
        return super.equals(object);
    }

    /**
     * Returns the capability as a displayable string.
     *
     * @return the displayable string
     */
    public String toString() {
        return super.toString();
    }

    /**
     * Returns the identifier of this capability.
     *
     * @return the capability
     */
    public String getIdentifier() {
        return "";
    }

    /**
     * Returns the integer value of this capability.
     *
     * @return the integer value
     */
    protected int getValue() {
        return 0;
    }

}
