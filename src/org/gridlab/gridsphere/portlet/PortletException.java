/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id$
*/
package org.gridlab.gridsphere.portlet;

import javax.servlet.ServletException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>PortletException</code> class defines a general exception that a
 * portlet can throw when it encounters an exceptional condition.
 */
public class PortletException extends ServletException {

    private Throwable cause = null;
    private String text = "";
    private List messages = new ArrayList();

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
        this.text += "\n" + text;
    }

    /**
     * Constructs a new portlet exception with the given text.
     * The portlet container may use the text write it to a log.
     *
     * @param text the exception text
     * @param cause the root cause
     */
    public PortletException(String text, Throwable cause) {
        super(text);
        this.text = text;
        this.cause = cause;
    }

    /**
     * Constructs a new portlet exception when the portlet needs to throw an exception.
     * The exception's message is based on the localized message of the underlying exception.
     *
     * @param cause the root cause
     */
    public PortletException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    /**
     * Return the exception message
     *
     * @return the exception message
     */
    public String getMessage() {
        return text;
    }

    /**
     * Return the exception message
     *
     * @return the exception message
     */
    public List getMessages() {
        return messages;
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (cause != null) {
            ps.println();
            ps.println();
            ps.println("Caused by:");
            cause.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        pw.println(text);
        if (cause != null) {
            pw.println();
            pw.println();
            pw.println("Caused by:");
            pw.println(cause.getMessage());
            cause.printStackTrace(pw);
        }
    }
}
