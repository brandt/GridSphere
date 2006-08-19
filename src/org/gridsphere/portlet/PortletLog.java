/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletLog.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

/**
 * The <code>PortletLog</code> provides the portlet with the ability to log
 * information, warning, or error texts. The log is maintained by the portlet
 * container. The type and location of of the log is a matter of implementation.
 * Also, whether logging is enabled or not is at the discretion of the portlet
 * container.
 * <p/>
 * <code>
 * ...
 * PortletLog log = iContext.getLog ();
 * <p/>
 * if (log.isWarnEnabled())
 * log.warn ("Can access the content source");
 * <p/>
 * ...
 * </code>
 */
public interface PortletLog {

    /**
     * Returns whether the debug level of the portlet log is enabled.
     *
     * @return <code>true</code> if debbuging is enabled, <code>false</code>
     *         otherwise
     */
    public boolean isDebugEnabled();

    /**
     * Logs the given informational text in the portlet log.
     *
     * @param text the informational text to log
     */
    public void debug(String text);

    /**
     * Returns whether the info level of the portlet log is enabled.
     *
     * @return <code>true</code> if the log is enabled,
     *         <code>>false</code> otherwise
     */
    public boolean isInfoEnabled();

    /**
     * Logs the given informational text in the portlet log.
     *
     * @param text the informational text to log
     */
    public void info(String text);

    /**
     * Returns whether the warn level of the portlet log is enabled.
     *
     * @return <code>true</code> if the log is enabled,
     *         <code>false</code> otherwise
     */
    public boolean isWarnEnabled();

    /**
     * Logs the given warning text in the portlet log.
     *
     * @param text the warning text to log
     */
    public void warn(String text);

    /**
     * Returns whether the error level of the portlet log is enabled.
     *
     * @return <code>true</code> if the log is enabled,
     *         <code>false</code> otherwise
     */
    public boolean isErrorEnabled();

    /**
     * Logs the given error text in the portlet log.
     *
     * @param text the error text to log
     */
    public void error(String text);

    /**
     * Logs the given error text, cause, and a stack trace in the portlet log.
     *
     * @param text  the error text to log
     * @param cause the cause for logging
     */
    public void error(String text, Throwable cause);

}
