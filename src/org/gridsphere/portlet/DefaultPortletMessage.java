/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: DefaultPortletMessage.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

/**
 * The <code>DefaultPortletMessage</code> is a portlet message with default parameters.
 * You can use this portlet message to construct a portlet message
 * This default implementation demonstrates how to implement it.
 */
public final class DefaultPortletMessage implements PortletMessage {

    private String message = "";

    /**
     * Default constructor creates a PortletMessage with an empty message
     */
    public DefaultPortletMessage() {
    }

    /**
     * Constructor creates a PortletMessage with the provided message
     *
     * @param message the portlet message
     */
    public DefaultPortletMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the portlet message
     *
     * @return the portlet message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the portlet message
     *
     * @param message the portlet message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the message String
     *
     * @return the message <code>String</code>
     */
    public String toString() {
        return message;
    }
}
