/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id$
*/
package org.gridlab.gridsphere.portlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The <code>PortletResponse</code> encapsulates the response sent by the client
 * to the portlet.
 */
public interface PortletResponse extends HttpServletResponse {

    /**
     * Adds the specified cookie to the response.
     *
     * @param cookie the cookie to be added
     */
    public void addCookie(Cookie cookie);

    /**
     * Adds a response header with the given name and date-value.
     *
     * @param name the name to be added
     * @param date the date-value
     */
    public void addDateHeader(String name, long date);

    /**
     * Adds a response header with the given name and value.
     *
     * @param name the name of the header
     * @param value the additional header value
     */
    public void addHeader(String name, String value);

    /**
     * Adds a response header with the given name and integer value.
     *
     * @param name the name of the header
     * @param value the additional header value
     */
    public void addIntHeader(String name, int value);

    /**
     * Returns a boolean indicating whether the named response header has already been set.
     *
     * @return <code>true</code> if response header name has been sent,
     * <code>false</code> otherwise
     */
    public boolean containsHeader(String name);

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
     * Creates a portlet URI pointing to the current portlet mode and given
     * portlet window state.
     *
     * @param state the portlet window state
     */
    public PortletURI createURI(PortletWindow.State state);

    /**
     * Maps the given string value into this portlet's namespace.
     *
     * @param aValue the string value
     */
    public String encodeNamespace(String aValue);

    /**
     * Returns the encoded URL of the resource at the given path.
     *
     * @param path the given path
     * @return the encoded URL
     */
    public String encodeURL(String path);

    /**
     * Returns the name of the charset used for the MIME body sent in this response.
     *
     * @return the character encoding
     */
    public String getCharacterEncoding();

    /**
     * Returns the writer object that can be used to contribute markup to the
     * portlet response.
     *
     * @return the writer
     * @throws IOException if an I/O error occurs
     */
    public PrintWriter getWriter() throws IOException;

    /**
     * Sets a response header with the given name and date-value.
     *
     * @param name the header name
     * @param date the header date-value
     */
    public void setDateHeader(String name, long date);

    /**
     * Sets a response header with the given name and value.
     *
     * @param name the header name
     * @param value the header value
     */
    public void setHeader(String name, String value);

    /**
     * Sets a response header with the given name and integer value.
     * If the header had already been set, the new value overwrites the
     * previous one. The containsHeader  method can be used to test for the
     * presence of a header before setting its value.
     * <p>
     * We need to think about all header methods and how to solve nameclashes, etc.
     *
     */
    public void setIntHeader(String name, int value);

}
