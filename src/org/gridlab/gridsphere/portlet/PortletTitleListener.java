/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.io.IOException;

/**
 * The PortletTitleListener interface has to be implemented if a portlet wants to support client, device,
 * and/or user dependent titles. The interface has to be implemented additionally to the Portlet interface,
 * otherwise the portlet container will pick the title from the portlet configuration.
 */
public interface PortletTitleListener {

    /**
     * Called by the portlet container to render the portlet title.
     * The information in the portlet request (like locale, client, and session information) can
     * but doesn't have to be considered to render dynamic titles.. Examples are
     *
     * language-dependant titles for multi-lingual portals
     * shorter titles for WAP phones
     * the number of messages in a mailbox portlet
     * The session may be null, if the user is not logged in.
     *
     * @param request the portlet request
     * @param response the portlet response
     *
     * @throws <code>PortletException</code>if the portlet title has trouble fulfilling the rendering request
     * @throws java.io.IOException if the streaming causes an I/O problem
     */
    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException;


}
