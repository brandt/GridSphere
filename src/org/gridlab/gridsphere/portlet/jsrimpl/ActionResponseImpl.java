
package org.gridlab.gridsphere.portlet.jsrimpl;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletWindow;

import javax.portlet.ActionRequest;
import javax.portlet.PortletResponse;
import javax.portlet.WindowStateException;
import javax.portlet.WindowState;
import javax.portlet.PortletModeException;
import javax.portlet.PortletMode;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;


/**
 * The <CODE>ActionResponse</CODE> interface represents the portlet
 * response to an action request.
 * It extends the <CODE>PortletResponse</CODE> interface to provide specific
 * action response functionality to portlets.<br>
 * The portlet container creates an <CODE>ActionResponse</CODE> object and
 * passes it as argument to the portlet's <CODE>processAction</CODE> method.
 *
 * @see ActionRequest
 * @see PortletResponse
 */
public class ActionResponseImpl extends PortletResponseImpl implements ActionResponse
{

    /**
     * Is it still allowed to invoke the method sendRedirect() ?
     */
    boolean isRedirectAllowed = true;

    private boolean redirected;
    private String redirectLocation;
    protected Map renderParams = null;
    protected PortletMode portletMode;
    protected WindowState windowState;

    /**
     * Constructs an instance of SportletResponse using an
     * <code>HttpServletResponse</code> as a proxy
     *
     * @param res the <code>HttpServletRequest</code>
     */
    public ActionResponseImpl(HttpServletRequest req, HttpServletResponse res) {
        super(req, res);
        renderParams = new HashMap();
    }

    /**
     * Sets the window state of a portlet to the given window state.
     * <p>
     * Possible values are the standard window states and any custom
     * window states supported by the portal and the portlet.
     * Standard window states are:
     * <ul>
     * <li>MINIMIZED
     * <li>NORMAL
     * <li>MAXIMIZED
     * </ul>
     *
     * @param windowState
     *               the new portlet window state
     *
     * @exception WindowStateException
     *                   if the portlet cannot switch to the specified window state.
     *                   To avoid this exception the portlet can check the allowed
     *                   window states with <code>Request.isWindowStateAllowed()</code>.
     * @exception IllegalStateException
     *                    if the method is invoked after <code>sendRedirect</code> has been called.
     *
     * @see WindowState
     */
    public void setWindowState(WindowState windowState)
            throws WindowStateException {
        if (redirected) {
            throw new IllegalStateException("it is not allowed to invoke setWindowState after sendRedirect has been called");
        }
        PortletWindow.State ws = PortletWindow.State.NORMAL;
        if (windowState == WindowState.MAXIMIZED) {
            ws = PortletWindow.State.MAXIMIZED;
        } else if (windowState == WindowState.MINIMIZED) {
            ws = PortletWindow.State.MINIMIZED;
        } else if (windowState == WindowState.NORMAL) {
            ws = PortletWindow.State.NORMAL;
        }
        isRedirectAllowed = false;
        this.windowState = windowState;
        req.setAttribute(SportletProperties.PORTLET_WINDOW, ws);
    }

    /**
     * Sets the portlet mode of a portlet to the given portlet mode.
     * <p>
     * Possible values are the standard portlet modes and any custom
     * portlet modes supported by the portal and the portlet. Portlets
     * must declare in the deployment descriptor the portlet modes they
     * support for each markup type.
     * Standard portlet modes are:
     * <ul>
     * <li>EDIT
     * <li>HELP
     * <li>VIEW
     * </ul>
     * <p>
     * Note: The portlet may still be called in a different window
     *       state in the next render call, depending on the portlet container / portal.
     *
     * @param portletMode
     *               the new portlet mode
     *
     * @exception PortletModeException
     *                   if the portlet cannot switch to this portlet mode,
     *                   because the portlet or portal does not support it for this markup,
     *                   or the current user is not allowed to switch to this portlet mode.
     *                   To avoid this exception the portlet can check the allowed
     *                   portlet modes with <code>Request.isPortletModeAllowed()</code>.
     * @exception IllegalStateException
     *                    if the method is invoked after <code>sendRedirect</code> has been called.
     */
    public void setPortletMode (PortletMode portletMode)
            throws PortletModeException {

        if (redirected) {
            throw new IllegalStateException("it is not allowed to invoke setPortletMode after sendRedirect has been called");
        }

        Portlet.Mode mode = Portlet.Mode.VIEW;
        if (portletMode == PortletMode.EDIT) {
            mode = Portlet.Mode.EDIT;
        } else if (portletMode == PortletMode.HELP) {
            mode = Portlet.Mode.HELP;
        } else {
            mode = Portlet.Mode.toMode(portletMode.toString());
        }
        this.portletMode = portletMode;
        req.setAttribute(SportletProperties.PORTLET_MODE, mode);
        isRedirectAllowed = false;
    }

    /**
     * Instructs the portlet container to send a redirect response
     * to the client using the specified redirect location URL.
     * <p>
     * This method only accepts an absolute URL (e.g.
     * <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
     * or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
     * If required,
     * the portlet container may encode the given URL before the
     * redirection is issued to the client.
     * <p>
     * The sendRedirect method can not be invoked after any of the
     * following methods of the ActionResponse interface has been called:
     * <ul>
     * <li>setPortletMode
     * <li>setWindowState
     * <li>setRenderParameter
     * <li>setRenderParameters
     * </ul>
     *
     * @param		location	the redirect location URL
     *
     * @exception	java.io.IOException
     *                    if an input or output exception occurs.
     * @exception	IllegalArgumentException
     *                    if a relative path URL is given
     * @exception IllegalStateException
     *                    if the method is invoked after any of above mentioned methods of
     *                    the ActionResponse interface has been called.
     */
    public void sendRedirect(String location)
            throws java.io.IOException {
        // TODO needs work
        if (isRedirectAllowed) {
            if (location != null) {
                HttpServletResponse res = (HttpServletResponse)super.getResponse();
                if (location.indexOf("://") != -1) {
                    //   provider.setAbsoluteURL(location);
                } else {
                    //   provider.setFullPath(location);
                }
                location = res.encodeRedirectURL(location.toString());
                redirectLocation = location;
                redirected = true;
                //res.sendRedirect(location);
            }
        } else {
            throw new IllegalStateException("Can't invoke sendRedirect() after certain methods have been called");
        }
    }

    /**
     * Sets a parameter map for the render request.
     * <p>
     * All previously set render parameters are cleared.
     * <p>
     * These parameters will be accessible in all
     * sub-sequent render calls via the
     * <code>PortletRequest.getParameter</code> call until
     * a new request is targeted to the portlet.
     * <p>
     * The given parameters do not need to be encoded
     * prior to calling this method.
     *
     * @param  parameters   Map containing parameter names for
     *                      the render phase as
     *                      keys and parameter values as map
     *                      values. The keys in the parameter
     *                      map must be of type String. The values
     *                      in the parameter map must be of type
     *                      String array (<code>String[]</code>).
     *
     * @exception	IllegalArgumentException
     *                      if parameters is <code>null</code>, if
     *                      any of the key/values in the Map are <code>null</code>,
     *                      if any of the keys is not a String, or if any of
     *                      the values is not a String array.
     * @exception IllegalStateException
     *                    if the method is invoked after <code>sendRedirect</code> has been called.
     */
    public void setRenderParameters(java.util.Map parameters) {
        if (redirected) {
            throw new IllegalStateException("Can't invoke setRenderParameters() after sendRedirect() has been called");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Render parameters must not be null.");
        }
        for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry)iter.next();
            if (!(entry.getKey() instanceof String)) {
                throw new IllegalArgumentException("Key must not be null and of type java.lang.String.");
            }
            if (!(entry.getValue() instanceof String[])) {
                throw new IllegalArgumentException("Value must not be null and of type java.lang.String[].");
            }
        }

        renderParams.putAll(parameters);

        isRedirectAllowed = false;

        this.renderParams = parameters;
    }


    /**
     * Sets a String parameter for the render request.
     * <p>
     * These parameters will be accessible in all
     * sub-sequent render calls via the
     * <code>PortletRequest.getParameter</code> call until
     * a request is targeted to the portlet.
     * <p>
     * This method replaces all parameters with the given key.
     * <p>
     * The given parameter do not need to be encoded
     * prior to calling this method.
     *
     * @param  key    key of the render parameter
     * @param  value  value of the render parameter
     *
     * @exception	IllegalArgumentException
     *                      if key or value are <code>null</code>.
     * @exception IllegalStateException
     *                    if the method is invoked after <code>sendRedirect</code> has been called.
     */
    public void setRenderParameter(String key, String value) {
        if (redirected) {
            throw new IllegalStateException("Can't invoke setRenderParameter() after sendRedirect() has been called");
        }

        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException("Render parameter key or value  must not be null.");
        }

        System.err.println("placing render poaram " + key + " " + value);
        renderParams.put(key, new String[] {value});

        isRedirectAllowed = false;
    }

    /**
     * Sets a String array parameter for the render request.
     * <p>
     * These parameters will be accessible in all
     * sub-sequent render calls via the
     * <code>PortletRequest.getParameter</code> call until
     * a request is targeted to the portlet.
     * <p>
     * This method replaces all parameters with the given key.
     * <p>
     * The given parameter do not need to be encoded
     * prior to calling this method.
     *
     * @param  key     key of the render parameter
     * @param  values  values of the render parameter
     *
     * @exception	IllegalArgumentException
     *                      if key or value are <code>null</code>.
     * @exception IllegalStateException
     *                    if the method is invoked after <code>sendRedirect</code> has been called.
     */
    public void setRenderParameter(String key, String[] values) {
        if (redirected) {
            throw new IllegalStateException("Can't invoke setRenderParameter() after sendRedirect() has been called");
        }

        if (key == null || values == null || values.length == 0) {
            throw new IllegalArgumentException("Render parameter key or value  must not be null or values be an empty array.");
        }

        renderParams.put(key, values);

        isRedirectAllowed = false;
    }

    public Map getRenderParameters() {
        return renderParams;
    }

    public String getRedirectLocation() {
        return redirectLocation;
    }

    public PortletMode getChangedPortletMode()
    {
        return this.portletMode;
    }

    public WindowState getChangedWindowState()
    {
        return this.windowState;
    }
}


