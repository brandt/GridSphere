/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

/**
 * This feature is an addition to the Portlet interface. To support session features in the portlet,
 * this interface has to be implemented additionally to the Portlet interface.
 */
public interface PortletSessionListener {

    /**
     * Called by the portlet container to ask the portlet to initialize a personalized user experience.
     * In addition to initializing the session this method allows the portlet to initialize the
     * concrete portlet instance, for example, to store attributes in the session.
     *
     * @param request the portlet request
     */
    public void login(PortletRequest request) throws PortletException;

    /**
     * Called by the portlet container to indicate that a concrete portlet instance is being removed.
     * This method gives the concrete portlet instance an opportunity to clean up any resources
     * (for example, memory, file handles, threads), before it is removed.
     * This happens if the user logs out, or decides to remove this portlet from a page.
     */
    public void logout(PortletSession session) throws PortletException;

}
