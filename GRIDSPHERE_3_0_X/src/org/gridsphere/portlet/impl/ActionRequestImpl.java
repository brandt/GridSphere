/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ActionRequestImpl.java 4894 2006-06-28 22:57:23Z novotny $
 */
package org.gridsphere.portlet.impl;

import javax.portlet.ActionRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * The <CODE>ActionRequest</CODE> represents the request sent to the portlet
 * to handle an action.
 * It extends the PortletRequest interface to provide action request
 * information to portlets.<br>
 * The portlet container creates an <CODE>ActionRequest</CODE> object and
 * passes it as argument to the portlet's <CODE>processAction</CODE> method.
 * 
 * @see PortletRequest
 * @see RenderRequest
 */
public class ActionRequestImpl extends PortletRequestImpl implements ActionRequest {

    /**
     * Constructor creates a proxy for a HttpServletRequest
     * All PortletRequest objects come from request or session attributes
     *
     * @param req the HttpServletRequest
     * @param portletContext the PortletContext
     */
    public ActionRequestImpl(HttpServletRequest req, PortletContext portletContext) {
        super(req, portletContext);
    }

    /**
     * Retrieves the body of the HTTP request from client to
     * portal as binary data using
     * an <CODE>InputStream</CODE>. Either this method or
     * {@link #getReader} may be called to read the body, but not both.
     * <p/>
     * For HTTP POST data of type application/x-www-form-urlencoded
     * this method throws an <code>IllegalStateException</code>
     * as this data has been already processed by the
     * portal/portlet-container and is available as request parameters.
     *
     * @return an input stream containing the body of the request
     * @throws IllegalStateException if getReader was already called, or it is a
     *                               HTTP POST data of type application/x-www-form-urlencoded
     * @throws java.io.IOException   if an input or output exception occurred
     */
    public java.io.InputStream getPortletInputStream() throws IOException, IllegalStateException {
        HttpServletRequest req = (HttpServletRequest) super.getRequest();
        // 'POST' replaced by 'post' for XHTML 1.0 Strict compliance
        if (req.getMethod().toUpperCase().equals("POST")) {
            String contentType = req.getContentType();
            if (contentType == null || contentType.equals("application/x-www-form-urlencoded")) {
                throw new IllegalStateException("User request HTTP POST data is of type application/x-www-form-urlencoded. This data has been already processed by the portal/portlet-container and is available as request parameters.");
            }
        }
        hasReader = true;
        return super.getRequest().getInputStream();
    }

    /**
     * Overrides the name of the character encoding used in the body of this
     * request. This method must be called prior to reading input
     * using {@link #getReader} or {@link #getPortletInputStream}.
     * <p/>
     * This method only sets the character set for the Reader that the
     * {@link #getReader} method returns.
     *
     * @param	enc	a <code>String</code> containing the name of
     * the chararacter encoding.
     * @exception		java.io.UnsupportedEncodingException if this is not a valid encoding
     * @exception		IllegalStateException if this method is called after
     * reading request parameters or reading input using
     * <code>getReader()</code>
     */
    public void setCharacterEncoding(String enc)
            throws UnsupportedEncodingException, IllegalStateException {
        if (hasReader) {
            throw new IllegalStateException("Cannot invoke this method after using getReader or reading request parameters!");
        }
        super.getRequest().setCharacterEncoding(enc);
    }

    /**
     * Retrieves the body of the HTTP request from the client to the portal
     * as character data using
     * a <code>BufferedReader</code>.  The reader translates the character
     * data according to the character encoding used on the body.
     * Either this method or {@link #getPortletInputStream} may be called to read the
     * body, not both.
     * <p/>
     * For HTTP POST data of type application/x-www-form-urlencoded
     * this method throws an <code>IllegalStateException</code>
     * as this data has been already processed by the
     * portal/portlet-container and is available as request parameters.
     *
     * @throws java.io.UnsupportedEncodingException
     *                               if the character set encoding used is
     *                               not supported and the text cannot be decoded
     * @throws IllegalStateException if {@link #getPortletInputStream} method
     *                               has been called on this request,  it is a
     *                               HTTP POST data of type application/x-www-form-urlencoded.
     * @throws java.io.IOException   if an input or output exception occurred
     * @return	a <code>BufferedReader</code>
     * containing the body of the request
     * @see #getPortletInputStream
     */
    public java.io.BufferedReader getReader()
            throws java.io.IOException {
        hasReader = true;
        return super.getRequest().getReader();
    }

    /**
     * Returns the name of the character encoding used in the body of this request.
     * This method returns <code>null</code> if the request
     * does not specify a character encoding.
     *
     * @return		a <code>String</code> containing the name of
     * the chararacter encoding, or <code>null</code>
     * if the request does not specify a character encoding.
     */
    public String getCharacterEncoding() {
        return super.getRequest().getCharacterEncoding();
    }

    /**
     * Returns the MIME type of the body of the request,
     * or null if the type is not known.
     *
     * @return		a <code>String</code> containing the name
     * of the MIME type of the request, or null
     * if the type is not known.
     */
    public String getContentType() {
        return super.getRequest().getContentType();
    }

    /**
     * Returns the length, in bytes, of the request body
     * which is made available by the input stream, or -1 if the
     * length is not known.
     *
     * @return		an integer containing the length of the
     * request body or -1 if the length is not known
     */
    public int getContentLength() {
        return super.getRequest().getContentLength();
    }

}
