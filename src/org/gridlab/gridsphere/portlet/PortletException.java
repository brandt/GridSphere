/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import javax.servlet.ServletException;

/**
 * The <code>PortletException</code> class defines a general exception that a
 * portlet can throw when it encounters an exceptional condition.
 */
public class PortletException extends ServletException {

    private String text;

    /**
     * Constructs an instance of PortletException
     */
    public PortletException() {
        super();
    }

    /**
     * Constructs an instance of PortletException with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public PortletException(String text) {
        super(text);
        this.text = text;
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     * @param cause the root cause
     */
    public PortletException(String text, Throwable cause) {
        super(text, cause);
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PortletException(Throwable cause) {
        super(cause);
    }

    /**
     * Return the exception message
     *
     * @return the exception message
     */
    public String getMessage() {
        return text;
    }

}
