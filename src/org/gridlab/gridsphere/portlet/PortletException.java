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

    public PortletException(String text, Throwable t) {
        super(text, t);
    }

}
