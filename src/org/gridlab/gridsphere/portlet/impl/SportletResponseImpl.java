/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id$
*/
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portlet.PortletResponse;

import javax.servlet.ServletResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Locale;

public class SportletResponseImpl implements PortletResponse {

    private HttpServletResponse res = null;

    /**
     * Constructor creates a facade for a HttpServletResponse
     *
     * @param req the HttpServletRequest
     */
    public SportletResponseImpl(HttpServletResponse res) {
        this.res = res;
    }

    /**
     * Adds the specified cookie to the response.
     *
     * @param cookie the cookie to be added
     */
    public void addCookie(Cookie cookie) {
        res.addCookie(cookie);
    }

    /**
     * Adds a response header with the given name and date-value.
     *
     * @param name the name to be added
     * @param date the date-value
     */
    public void addDateHeader(String name, long date) {
        res.addDateHeader(name, date);
    }

    /**
     * Adds a response header with the given name and value.
     *
     * @param name the name of the header
     * @param value the additional header value
     */
    public void addHeader(String name, String value) {
        res.addHeader(name, value);
    }

    /**
     * Adds a response header with the given name and integer value.
     *
     * @param name the name of the header
     * @param value the additional header value
     */
    public void addIntHeader(String name, int value) {
        res.addIntHeader(name, value);
    }

    /**
     * Returns a boolean indicating whether the named response header has already been set.
     *
     * @return true if response header name has been sent, false otherwise
     */
    public boolean containsHeader(String name) {
        return res.containsHeader(name);
    }

    /**
     * Creates a portlet URI pointing at the referrer of the portlet.
     *
     * @return the portletURI
     */
    public PortletURI createReturnURI() {
        // XXX: FILL ME IN
        return null;
    }


    /**
     * Creates a portlet URI pointing to the current portlet mode.
     *
     * @return the portlet URI
     */
    public PortletURI createURI() {
        // XXX: FILL ME IN
        return null;
    }

    /**
     * Creates a portlet URI pointing to the current portlet mode and given portlet window state.
     *
     * @param state the portlet window state
     */
    public PortletURI createURI(PortletWindow.State state) {
        // XXX: FILL ME IN
        return null;
    }

    /**
     * Maps the given string value into this portlet's namespace.
     *
     * @param aValue the string value
     */
    public String encodeNamespace(String aValue) {
        // XXX: FILL ME IN
        return aValue;
    }

    /**
     * Returns the encoded URI of the resource at the given path.
     *
     * @param path the given path
     * @return the encoded URI
     */
    public String encodeURL(String path) {
        // XXX: FILL ME IN
        return path;
    }

    /**
     * Returns the name of the charset used for the MIME body sent in this response.
     *
     * @return the character encoding
     */
    public String getCharacterEncoding() {
        return res.getCharacterEncoding();
    }

    /**
     * Returns the content type that can be used to contribute markup to the portlet response.
     *
     * @return the content type (NULL right now)
     */
    public String getContentType() {
        // XXX: FILL ME IN
        return null;
    }

    /**
     * Returns the writer object that can be used to contribute markup to the portlet response.
     *
     * @return the writer
     */
    public PrintWriter getWriter() throws IOException {
        return res.getWriter();
    }

    /**
     * Sets a response header with the given name and date-value.
     *
     * @param name the header name
     * @param date the header date-value
     */
    public void setDateHeader(String name, long date) {
        res.setDateHeader(name, date);
    }

    /**
     * Sets a response header with the given name and value.
     *
     * @param name the header name
     * @param value the header value
     */
    public void setHeader(String name, String value) {
        res.setHeader(name, value);
    }

    /**
     * Sets a response header with the given name and integer value.
     * If the header had already been set, the new value overwrites the previous one.
     * The containsHeader  method can be used to test for the presence of a header before setting its value.
     *
     * We need to think about all header methods and how to solve nameclashes, etc.
     *
     *
     */
    public void setIntHeader(String name, int value) {
        res.setIntHeader(name, value);
    }

    public final ServletOutputStream getOutputStream() throws IOException {
        return res.getOutputStream();
    }

    public final String encodeRedirectURL(String redirectURL) {
        return res.encodeRedirectURL(redirectURL);
    }

    public final String encodeRedirectUrl(String redirectURL) {
        return res.encodeRedirectUrl(redirectURL);
    }

    public final String encodeUrl(String url) {
        return res.encodeUrl(url);
    }

    public final void sendError(int errno, String msg) throws IOException {
        res.sendError(errno, msg);
    }

    public final void sendError(int errno) throws IOException {
        res.sendError(errno);
    }

    public final void sendRedirect(String redirect) throws IOException {
        res.sendRedirect(redirect);
    }

    public final void setContentLength(int len) {
        res.setContentLength(len);
    }

    public final void setContentType(String type) {
        res.setContentType(type);
    }

    public final void setBufferSize(int size) {
        res.setBufferSize(size);
    }

    public final int getBufferSize() {
        return res.getBufferSize();
    }

    public final void flushBuffer() throws IOException {
        res.flushBuffer();
    }

    public final void resetBuffer() {
        res.resetBuffer();
    }

    public final boolean isCommitted() {
        return res.isCommitted();
    }

    public final void reset() {
        res.reset();
    }

    public final void setStatus(int status) {
        res.setStatus(status);
    }

    public final void setStatus(int status, String msg) {
        res.setStatus(status, msg);
    }

    public void setLocale(Locale loc) {
        res.setLocale(loc);
    }

    public Locale getLocale() {
        return res.getLocale();
    }

}
