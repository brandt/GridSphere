/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 */
public final class DefaultPortletMessage implements PortletMessage {

    private Map store = new Hashtable();
    private String message = "";

    /**
     * Default constructor creates a PortletMessage with an empty message
     */
    public DefaultPortletMessage() {}

    /**
     * Constructor creates a PortletMessage with the provided message
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

    public String toString() {
        return message;
    }
}
