/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortalContextImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portletcontainer.impl.descriptor.CustomPortletMode;
import org.gridsphere.portletcontainer.impl.descriptor.CustomWindowState;
import org.gridsphere.portletcontainer.impl.descriptor.PortletApp;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The <CODE>PortalContext</CODE> interface gives the portlet
 * the ability to retrieve information about the portal calling this portlet.
 * <p/>
 * The portlet can only read the <CODE>PortalContext</CODE> data.
 */
public class PortalContextImpl implements PortalContext {

    private Map props = new HashMap();
    private List windowStates = null;
    private List portletModes = null;

    public PortalContextImpl(PortletApp portletApp) {
        windowStates = new ArrayList();
        windowStates.add(WindowState.MAXIMIZED);
        windowStates.add(WindowState.NORMAL);
        windowStates.add(WindowState.MINIMIZED);
        CustomWindowState[] customStates = portletApp.getCustomWindowState();

        for (int i = 0; i < customStates.length; i++) {
            CustomWindowState state = customStates[i];
            windowStates.add(state);
        }
        portletModes = new ArrayList();
        portletModes.add(PortletMode.EDIT);
        portletModes.add(PortletMode.VIEW);
        portletModes.add(PortletMode.HELP);
        CustomPortletMode[] customModes = portletApp.getCustomPortletMode();
        for (int i = 0; i < customModes.length; i++) {
            CustomPortletMode mode = customModes[i];
            portletModes.add(mode.getPortletMode());
        }
    }

    /**
     * Returns the portal property with the given name,
     * or a <code>null</code> if there is
     * no property by that name.
     *
     * @param name property name
     * @return portal property with key <code>name</code>
     * @exception	IllegalArgumentException if name is <code>null</code>.
     */
    public String getProperty(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        return (String) props.get(name);
    }


    /**
     * Returns all portal property names, or an empty
     * <code>Enumeration</code> if there are no property names.
     *
     * @return All portal property names as an
     *         <code>Enumeration</code> of <code>String</code> objects
     */
    public java.util.Enumeration getPropertyNames() {
        return new Enumerator(props.keySet());
    }


    /**
     * Returns all supported portlet modes by the portal
     * as an enumertation of <code>PortletMode</code> objects.
     * <p/>
     * The portlet modes must at least include the
     * standard portlet modes <code>EDIT, HELP, VIEW</code>.
     *
     * @return All supported portal modes by the portal
     *         as an enumertation of <code>PortletMode</code> objects.
     */
    public java.util.Enumeration getSupportedPortletModes() {
        return new Enumerator(portletModes);
    }


    /**
     * Returns all supported window states by the portal
     * as an enumertation of <code>WindowState</code> objects.
     * <p/>
     * The window states must at least include the
     * standard window states <code> MINIMIZED, NORMAL, MAXIMIZED</code>.
     *
     * @return All supported window states by the portal
     *         as an enumertation of <code>WindowState</code> objects.
     */
    public java.util.Enumeration getSupportedWindowStates() {
        return new Enumerator(windowStates);
    }


    /**
     * Returns information about the portal like vendor, version, etc.
     * <p/>
     * The form of the returned string is <I>servername/versionnumber</I>.
     * <p/>
     * The portlet container may return other optional information  after the
     * primary string in parentheses, for example, <CODE>GridSphere/1.0
     * (JDK 1.3.1; Windows NT 4.0 x86)</CODE>.
     *
     * @return a <CODE>String</CODE> containing at least the portal name and version number
     */
    public String getPortalInfo() {
        return SportletProperties.getInstance().getProperty("gridsphere.release");
    }
}
