/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletSessionListener.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The <code>PortletSessionListener</code> provides an interface for performing
 * login and logout functionality.
 */
public interface PortletSessionListener {

    /**
     * Called by the portlet container to ask the portlet to initialize a
     * personalized user experience. In addition to initializing the session
     * this method allows the portlet to initialize the concrete portlet
     * instance, for example, to store attributes in the session.
     *
     * @param request the portlet request
     */
    public void login(HttpServletRequest request);

    /**
     * Called by the portlet container to indicate that a concrete portlet instance is being removed.
     * This method gives the concrete portlet instance an opportunity to clean up any resources
     * (for example, memory, file handles, threads), before it is removed.
     * This happens if the user logs out, or decides to remove this portlet from a page.
     *
     * @param session the portlet session
     */
    public void logout(HttpSession session);

}
