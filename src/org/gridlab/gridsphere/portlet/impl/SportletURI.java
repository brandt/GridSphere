/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.*;


/**
 * A <code>SportletURI</code> provides an implementation of a
 * <code>PortletURI</code>
 */
public class SportletURI implements PortletURI {

    private HttpServletResponse res = null;
    private HttpServletRequest req = null;
    private Map store = new HashMap();
    private boolean isSecure = false;
    private boolean redirect = true;
    private String contextPath = null;
    private String id = "";
    private Map actionParams = new HashMap();
    private Set sportletProps = null;

    /**
     * Cannot instantiate uninitialized SportletResponse
     */
    private SportletURI() {
    }

    /**
     * Constructs a SportletURI from a <code>HttpServletResponse</code> and a
     * context path obtained from a <code>HttpServletRequest</code>
     *
     * @param res a <code>HttpServletResponse</code>
     * @param contextPath the request context path
     */
    public SportletURI(HttpServletRequest req, HttpServletResponse res, String contextPath) {
        this.store = new HashMap();
        this.contextPath = contextPath;
        this.res = res;
        this.req = req;
        this.id = createUniquePrefix(2);
        // don't prefix these parameters of an action
        sportletProps = new HashSet();
        sportletProps.add(SportletProperties.COMPONENT_ID);
        sportletProps.add(SportletProperties.PORTLET_WINDOW);
        sportletProps.add(SportletProperties.PORTLET_MODE);
    }

    /**
     * Constructs a SportletURI from a <code>HttpServletResponse</code> and a
     * context path obtained from a <code>HttpServletRequest</code>
     *
     * @param res a <code>HttpServletResponse</code>
     * @param contextPath the request context path
     */
    public SportletURI(HttpServletRequest req, HttpServletResponse res, String contextPath, boolean isSecure) {
        this.store = new HashMap();
        this.isSecure = isSecure;
        this.contextPath = contextPath;
        this.req = req;
        this.res = res;
        this.id = createUniquePrefix(2);
        // don't prefix these parameters of an action
        sportletProps = new HashSet();
        sportletProps.add(SportletProperties.COMPONENT_ID);
        sportletProps.add(SportletProperties.PORTLET_WINDOW);
        sportletProps.add(SportletProperties.PORTLET_MODE);
    }

    /**
     *  A string utility that produces a string composed of
     * <code>numChars</code> number of characters
     *
     * @param numChars the number of characters in the resulting <code>String</code>
     * @return the <code>String</code>
     */
    private String createUniquePrefix(int numChars) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i <= numChars; i++) {
            int nextChar = (int) (Math.random() * 62);
            if (nextChar < 10) //0-9
                s.append(nextChar);
            else if (nextChar < 36) //a-z
                s.append((char) (nextChar - 10 + 'a'));
            else
                s.append((char) (nextChar - 36 + 'A'));
        }
        return s.toString();
    }

    /**
     * Determines if the generated URI should be referring back to itself
     *
     * @param redirect <code>true</code> if the generated URI should be
     * redirected, <code>false</code> otherwise
     */
    public void setReturn(boolean redirect) {
        this.redirect = redirect;
    }

    /**
     * Adds the given parameter to this URI. A portlet container may wish to
     * prefix the attribute names internally, to preserve a unique namespace
     * for the portlet.
     *
     * @param name the parameter name
     * @param value the parameter value
     */
    public void addParameter(String name, String value) {
        if (sportletProps.contains(name)) {
            store.put(name, value);
        } else {
            actionParams.put(name, value);
        }
    }

    /**
     * Removes the given parameter from this URI.
     * @param name
     */
    public void removeParameter(String name) {
        if (sportletProps.contains(name)) {
            store.remove(name);
        } else {
            actionParams.remove(name);
        }
    }

    /**
     * Adds the given action to this URI.
     *
     * @param simpleAction the portlet action String
     */
    public void addAction(String simpleAction) {
        DefaultPortletAction dpa = new DefaultPortletAction(simpleAction);
        addAction(dpa);
    }

    /**
     * Adds the given action to this URI. The action is a portlet-defined
     * implementation of the portlet action interface. It can carry any information.
     * How the information is recovered should the next request be for this URI is
     * at the discretion of the portlet container.
     * <p>
     * Unless the ActionListener interface is implemented at the portlet this
     * action will not be delivered.
     *
     * @param action the portlet action
     */
    public void addAction(PortletAction action) {
        if (action instanceof DefaultPortletAction) {
            DefaultPortletAction dpa = (DefaultPortletAction) action;
            if (!dpa.getName().equals("")) {
                store.put(SportletProperties.DEFAULT_PORTLET_ACTION, dpa.getName());
                this.actionParams = dpa.getParameters();
            }
        }
    }

    /**
     * Sets the window state that will be invoked by this URI
     *
     * @param state the window state that will be invoked by this URI
     */
    public void setWindowState(PortletWindow.State state) {
        store.put(SportletProperties.PORTLET_WINDOW, state.toString());
    }

    /**
     * Sets the portlet mode that will be invoked by this URI
     *
     * @param mode the portlet mode that will be invoked by this URI
     */
    public void setPortletMode(Portlet.Mode mode) {
        store.put(SportletProperties.PORTLET_MODE, mode.toString());
    }

    /**
     * Returns the complete URI as a string. The string is ready to be embedded
     * in markup. Once the string has been created, adding more parameters or
     * other listeners will not modify the string. You have to call this method
     * again, to create an updated string.
     *
     * @return the URI as a string
     */
    public String toString() {
        StringBuffer s = new StringBuffer();
        if (isSecure) {
            s.append("https://");
            System.err.println("USING SECURE HTTPS!!!!!!!!!!!!!!!!!!!");
        } else {
            s.append("http://");
        }
        s.append(req.getServerName() + ":" + req.getServerPort());

        // add the actionsprams and prefix them

        Set paramSet = actionParams.keySet();
        Iterator it = paramSet.iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            String newname = id + "_" + name;
            String value = (String) actionParams.get(name);
            store.put(newname, value);
        }
        if (!actionParams.isEmpty()) store.put(SportletProperties.PREFIX, id);

        String url = contextPath;
        String newURL;
        Set set = store.keySet();
        if (!set.isEmpty()) {
            // add question mark
            url = contextPath + contextPath + "?";
        } else {
            return s.append(contextPath + url).toString();
        }
        boolean firstParam = true;
        it = set.iterator();
        //try {
        while (it.hasNext()) {
            if (!firstParam)
                url += "&";
            String name = (String)it.next();

            String encname = URLEncoder.encode(name);
            String val = (String) store.get(name);
            if (val != null) {
                String encvalue = URLEncoder.encode(val);
                url += encname + "=" + encvalue;
            } else {
                url += encname;
            }
            firstParam = false;
        }
        /*
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unable to support UTF-8 encoding!");
        }*/
        if (redirect) {
            newURL = res.encodeRedirectURL(url);
        } else {
            newURL = res.encodeURL(url);
        }
        s.append(newURL);
        return s.toString();
    }

}