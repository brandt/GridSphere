/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletSession.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

import javax.servlet.http.HttpSession;


/**
 * The <code>PortletSession</code> holds the user-specific data that the portlet
 * needs to personalize the one global portlet instance. Together with the
 * portlet, the portlet session constitutes the concrete portlet instance.
 */
public interface PortletSession extends HttpSession {

}
