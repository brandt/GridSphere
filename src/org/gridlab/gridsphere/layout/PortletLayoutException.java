/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletException;

/**
 * A <code>PortletLayoutException</code> is thrown when the portlet rendering process
 * should fail for any non-I/O related reason
 */
public class PortletLayoutException extends PortletException {

    /**
     * Constructs an instance of PortletLayoutException
     */
    public PortletLayoutException() {
        super();
    }

    /**
     * Constructs an instance of PortletLayoutException with a supplied message
     *
     * @param msg the message
     */
    public PortletLayoutException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of PortletLayoutException with a supplied message
     * and exception
     *
     * @param msg the message
     * @param t   the exception
     */
    public PortletLayoutException(String msg, Throwable t) {
        super(msg, t);
    }

}

