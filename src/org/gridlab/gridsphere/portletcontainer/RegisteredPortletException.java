/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletException;

import javax.servlet.ServletException;

/**
 * The PortletException class defines a general exception that a portlet can throw when it encounters difficulty.
 */
public class RegisteredPortletException extends PortletException {

    /**
     * Constructs a new portlet exception.
     */
    public RegisteredPortletException() {
        super();
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public RegisteredPortletException(String text) {
        super(text);
    }

    public RegisteredPortletException(String text, Throwable t) {
        super(text, t);
    }

}
