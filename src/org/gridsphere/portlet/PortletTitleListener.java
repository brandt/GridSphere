/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletTitleListener.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

import java.io.IOException;

/**
 * The <code>PortletTitleListener</code> interface provides a portlet title
 * listener that is implemented by the
 * {@link org.gridsphere.portlet.AbstractPortlet} and allows portlets to
 * update the title bar presentation by over-riding the doTitle() method.
 */
public interface PortletTitleListener {

    /**
     * Called by the portlet container to render the portlet title.
     * The information in the portlet request (like locale, client, and
     * session information) can but doesn't have to be considered to render
     * dynamic titles.. Examples are
     * <p/>
     * language-dependant titles for multi-lingual portals
     * shorter titles for WAP phones
     * the number of messages in a mailbox portlet
     * The session may be null, if the user is not logged in.
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws PortletException if the portlet title has trouble fulfilling
     *                          the rendering request
     * @throws IOException      if the streaming causes an I/O problem
     */
    public void doTitle(PortletRequest request, PortletResponse response)
            throws PortletException, IOException;

}
