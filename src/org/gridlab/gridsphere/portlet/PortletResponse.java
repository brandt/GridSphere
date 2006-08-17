/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id$
*/
package org.gridlab.gridsphere.portlet;

import javax.servlet.http.HttpServletResponse;

/**
 * The <code>PortletResponse</code> encapsulates the response sent by the client
 * to the portlet.
 */
public interface PortletResponse extends HttpServletResponse {

    /**
     * Creates a portlet URI pointing at the referrer of the portlet.
     *
     * @return the portletURI
     */
    public PortletURI createReturnURI();


    /**
     * Creates a portlet URI pointing to the current portlet mode.
     *
     * @return the portlet URI
     */
    public PortletURI createURI();

    /**
     * Creates a portlet URI pointing to the current portlet mode with the specified security.
     *
     * @return the portlet URI
     */
    public PortletURI createURI(boolean isSecure);

    /**
     * Creates a portlet URI pointing to the current portlet mode and given
     * portlet window state.
     *
     * @param state the portlet window state
     */
    public PortletURI createURI(PortletWindow.State state);

    /**
     * Creates a portlet URI pointing to the given portlet mode and current
     * portlet window state.
     *
     * @param mode the portlet mode
     */
    public PortletURI createURI(Mode mode);

    /**
     * Creates a portlet URI pointing to another portal component with the specified security
     *
     * @return the portlet component label
     */
    public PortletURI createURI(String componentLabel, boolean isSecure);


    /**
     * Maps the given string value into this portlet's namespace.
     *
     * @param aValue the string value
     */
    public String encodeNamespace(String aValue);

}
