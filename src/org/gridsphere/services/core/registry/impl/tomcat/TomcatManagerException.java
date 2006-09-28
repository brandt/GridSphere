/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TomcatManagerException.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.registry.impl.tomcat;

public class TomcatManagerException extends Exception {

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
        super(msg, cause);
    }

}
