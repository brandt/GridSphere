/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat;

public class TomcatManagerException extends Exception {

    private String msg = "";
    private Throwable cause = null;

    /**
     * Constructs an instance of PortletException with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param msg the exception text
     */
    public TomcatManagerException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param msg   the exception text
     * @param cause the root cause
     */
    public TomcatManagerException(String msg, Throwable cause) {
        super(msg);
        this.msg = msg;
        this.cause = cause;
    }
}
