/*
* @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
* @version $Id: PortletException.java 4670 2006-03-27 17:56:20Z novotny $
*/
package org.gridsphere.portletcontainer;

import javax.servlet.ServletException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * The <code>PortletException</code> class defines a general exception that a
 * portlet can throw when it encounters an exceptional condition.
 */
public class PortletDispatcherException extends ServletException {

    private Throwable cause = null;
    private String text = "";

    /**
     * Constructs an instance of PortletException
     */
    public PortletDispatcherException() {
        super();
    }

    /**
     * Constructs an instance of PortletException with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     */
    public PortletDispatcherException(String text) {
        super(text);
        this.text = text;
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text  the exception text
     * @param cause the root cause
     */
    public PortletDispatcherException(String text, Throwable cause) {
        super(text, cause);
        this.text = text;
        this.cause = cause;
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PortletDispatcherException(Throwable cause) {
        super(cause);
        this.cause = cause;
        text = cause.getMessage();
    }

    /**
     * Return the exception message
     *
     * @return the exception message
     */
    public String getMessage() {
        return text;
    }

    public Throwable getCause() {
        return ((cause != null) ? cause.getCause() : null);
    }

    public void printStackTrace() {
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        if (text != null) ps.println(text);
        if (cause != null) {
            ps.println("Caused by:");
            cause.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        if (text != null) pw.println(text);
        if (cause != null) {
            pw.println("Caused by:");
            cause.printStackTrace(pw);
        }
    }

    /**
     * Returns the exception that caused this servlet exception.
     *
     *
     * @return                  the <code>Throwable</code>
     *                          that caused this servlet exception
     *
     */

    public Throwable getRootCause() {
        return cause;
    }

}
