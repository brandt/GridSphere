/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletException;

/**
 * The PortletException class defines a general exception that a portlet can throw when it encounters difficulty.
 */
public class ConcretePortletException extends PortletException {

    /**
     * Constructs a new portlet exception.
     */
    public ConcretePortletException() {
        super();
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public ConcretePortletException(String text) {
        super(text);
    }

    public ConcretePortletException(String text, Throwable t) {
        super(text, t);
    }

}
