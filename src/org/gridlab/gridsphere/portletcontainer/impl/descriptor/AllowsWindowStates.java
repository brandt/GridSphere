/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl.descriptor;

import org.exolab.castor.types.AnyNode;
import org.gridlab.gridsphere.portlet.PortletWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>AllowsWindowStates</code> allows application portlets to define
 * the supported window states. If none is specifed, then it is assumed that
 * all window states are supported.
 */
public class AllowsWindowStates {

    private List windowStates = new ArrayList();
    private List statesAsStrings = null;
    private List statesAsStates = null;

    /**
     * Constructs an instance of AllowsWindowStates
     */
    public AllowsWindowStates() {
    }

    /**
     * For use by Castor. Clients should use #setPortletWindowStates
     * <p>
     * Sets the supported window states for this portlet as a list conatining
     * Castor <code>AnyNode</code> elements.
     *
     * @param windowStates an <code>ArrayList</code> containing the window states
     */
    public void setWindowStates(ArrayList windowStates) {
        this.windowStates = windowStates;
    }

    /**
     * For use by Castor. Clients should use #setPortletWindowStates
     * <p>
     * Returns the supported window states for this portlet
     *
     * @return the window states list composed of <code>AnyNode</code> elements
     */
    public List getWindowStates() {
        return windowStates;
    }

    /**
     * Convert <code>AnyNode</code> representation of window states into
     * <code>String</code>s
     */
    protected void convertStates() {
        AnyNode a = null;
        statesAsStrings = new ArrayList();
        for (int i = 0; i < windowStates.size(); i++) {
            a = (AnyNode) windowStates.get(i);
            statesAsStrings.add(a.getLocalName());
        }
    }

    /**
     * Return the list of window states as a <code>List</code> of
     * <code>String</code>s
     *
     * @return the list of window states
     */
    public List getWindowStatesAsStrings() {
        if (statesAsStrings == null) {
            convertStates();
        }
        return statesAsStrings;
    }

    /**
     * Convert the window states into a list of <code>PortletWindow.State</code>
     * elements
     */
    protected void convert2WindowStates() {
        AnyNode a = null;
        PortletWindow.State state;
        statesAsStates = new ArrayList();
        for (int i = 0; i < windowStates.size(); i++) {
            a = (AnyNode) windowStates.get(i);
            try {
                state = PortletWindow.State.toState(a.getLocalName());
                statesAsStates.add(state);
            } catch (Exception e) {  // do nothing
            }
        }
    }

    /**
     * Returns the allowed window states as <code>PortletWindow.State</code> objects
     *
     * @return the allowed window states
     */
    public List getPortletWindowStates() {
        if (statesAsStates == null) {
            convert2WindowStates();
        }
        return statesAsStates;
    }
}
