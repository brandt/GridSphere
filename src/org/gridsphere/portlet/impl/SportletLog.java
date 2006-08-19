/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletLog.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.PortletLog;

import org.apache.log4j.*;


/**
 * The <code>PortletLog</code> provides the portlet with the ability to log
 * information, warning, or error texts. The log is maintained by the portlet
 * container. The type and location of of the log is a matter of implementation.
 * Also, whether logging is enabled or not is at the discretion of the
 * portlet container.
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
 * <p/>
 */
public class SportletLog implements PortletLog {

    private static final String FQCN = SportletLog.class.getName() + ".";
    private final Logger logger;

    /**
     * Constructor not accessible. Use getDefault instead.
     */
    private SportletLog(final Class clazz) {
        logger = LogManager.getLogger(clazz);
    }

    /**
     * Return an instance of the PortletLog for a particular class
     *
     * @return the PortletLog
     */
    public static synchronized PortletLog getInstance(Class clazz) {
        return new SportletLog(clazz);
    }

    /**
     * Returns whether the debug level of the portlet log is enabled.
     *
     * @return true if debbuging is enabled, false otherwise
     */
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Logs the given informational text in the portlet log.
     *
     * @param text the informational text to log
     */
    public void debug(String text) {
        logger.log(FQCN, Level.DEBUG, text, null);
    }

    /**
     * Returns whether the info level of the portlet log is enabled.
     *
     * @return true if the log is enabled, false otherwise
     */
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    /**
     * Logs the given informational text in the portlet log.
     *
     * @param text the informational text to log
     */
    public void info(String text) {
        logger.log(FQCN, Level.INFO, text, null);
    }

    /**
     * Returns whether the warn level of the portlet log is enabled.
     *
     * @return true for the moment
     */
    public boolean isWarnEnabled() {
        return logger.isEnabledFor(Priority.WARN);
    }

    /**
     * Logs the given warning text in the portlet log.
     *
     * @param text the warning text to log
     */
    public void warn(String text) {
        logger.log(FQCN, Level.WARN, text, null);
    }

    /**
     * Returns whether the error level of the portlet log is enabled.
     *
     * @return true if the log is enabled, false otherwise
     */
    public boolean isErrorEnabled() {
        return logger.isEnabledFor(Priority.ERROR);
    }

    /**
     * Logs the given error text in the portlet log.
     *
     * @param text the error text to log
     */
    public void error(String text) {
        logger.log(FQCN, Level.ERROR, text, null);
    }

    /**
     * Logs the given error text, cause, and a stack trace in the portlet log.
     *
     * @param text  the error text to log
     * @param cause the cause for logging
     */
    public void error(String text, Throwable cause) {
        logger.log(FQCN, Level.ERROR, text, cause);
    }

}
