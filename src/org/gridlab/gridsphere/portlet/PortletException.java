/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import javax.servlet.ServletException;

/**
 * The PortletException class defines a general exception that a portlet can throw when it encounters difficulty.
 */
public class PortletException extends ServletException {

    /**
     * Constructs a new portlet exception.
     */
    public PortletException() {
        super();
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public PortletException(String text) {
        super(text);
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

}
