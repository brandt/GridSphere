/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl;

import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortletConfig;
import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition;
import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletApp;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>ApplicationSportletConfig</code> is the implementation of
 * <code>ApplicationPortletConfig</code> using Castor for XML to Java
 * bindings.
 */
public class JSRApplicationPortletConfigImpl implements ApplicationPortletConfig {

    private String id = "";
    private String portletName = "";
    private int expiration = 0;

    /**
     *  Constructs an instance of ApplicationSportletConfig
     */
    public JSRApplicationPortletConfigImpl(PortletApp app, PortletDefinition portletDef) {
        this.id = portletDef.getPortletClass().getContent();
        this.portletName = portletDef.getPortletName().getContent();
        if (portletDef.getExpirationCache() != null) {
            expiration = portletDef.getExpirationCache().getContent();
        }

    }

    /**
     * Returns the portlet application id
     *
     * @return the portlet application id
     */
    public String getApplicationPortletID() {
        return id;
    }

    /**
     * Returns the portlet application name
     *
     * @return the portlet application name
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Sets the name of a PortletApplication
     *
     * @param portletName name of a PortletApplication
     */
    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    /**
     * Returns the allowed window states supported by this portlet
     *
     * @return the <code>List</code> of
     * <code>PortletWindow.State</code> elements allowed for this portlet
     */
    public List getAllowedWindowStates() {
        List states = new ArrayList();
        states.add(PortletWindow.State.MAXIMIZED);
        states.add(PortletWindow.State.MINIMIZED);
        states.add(PortletWindow.State.RESIZING);

        // TODO support custom states
        /*
        CustomWindowState[] customStates = portletApp.getCustomWindowState();
        if (customStates != null) {
            for (int i = 0; i < customStates.length; i++) {
                String newstate = customStates[i].getWindowState().getContent();
                PortletWindow.State s = PortletWindow.State.toState(newstate);
                states.add(s);
            }
        }
        */
        return states;
    }

    /**
     * Returns the supported modes for this portlet
     *
     * @return the supported modes for this portlet
     */
    public List getSupportedModes() {
        List modes = new ArrayList();
        modes.add(Portlet.Mode.HELP);
        modes.add(Portlet.Mode.EDIT);
        modes.add(Portlet.Mode.CONFIGURE);
        modes.add(Portlet.Mode.VIEW);

        // TODO support custom modes
        /*
        CustomPortletMode[] customModes = portletApp.getCustomPortletMode();
        if (customModes != null) {
            for (int i = 0; i < customModes.length; i++) {
                String newmode = customModes[i].getPortletMode().getContent();
                Portlet.Mode m = Portlet.Mode.toMode(newmode);
                modes.add(m);
            }
        }
        */
        return modes;
    }

    /**
     * returns the amount of time in seconds that a portlet's content should be cached
     *
     * @return the amount of time in seconds that a portlet's content should be cached
     */
    public long getCacheExpires() {
        return expiration;
    }

}

