/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: AllowsWindowStates.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.impl.descriptor;

import org.exolab.castor.types.AnyNode;
import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.PortletWindow;
import org.gridsphere.portlet.impl.SportletLog;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>AllowsWindowStates</code> allows application portlets to define
 * the supported window states. If none is specifed, then it is assumed that
 * all window states are supported.
 */
public class AllowsWindowStates {

    private PortletLog log = SportletLog.getInstance(AllowsWindowStates.class);
    private List windowStates = new ArrayList();
    private List statesAsStrings = new ArrayList();
    private List statesAsStates = new ArrayList();

    /**
     * Constructs an instance of AllowsWindowStates
     */
    public AllowsWindowStates() {
    }

    /**
     * For use by Castor. Clients should use #setPortletWindowStates
     * <p/>
     * Sets the supported window states for this portlet as a list conatining
     * Castor <code>AnyNode</code> elements.
     *
     * @param windowStates an <code>ArrayList</code> containing the window states
     */
    public void setWindowStates(ArrayList windowStates) {
        if (windowStates != null) this.windowStates = windowStates;
    }

    /**
     * For use by Castor. Clients should use #setPortletWindowStates
     * <p/>
     * Returns the supported window states for this portlet
     *
     * @return the window states list composed of <code>AnyNode</code> elements
     */
    public List getWindowStates() {
        return windowStates;
    }

    /**
     * Return the list of window states as a <code>List</code> of
     * <code>String</code>s
     *
     * @return the list of window states
     */
    public List getWindowStatesAsStrings() {
        if (statesAsStrings.isEmpty()) updateStates();
        return statesAsStrings;
    }

    /**
     * Convert the window states into a list of <code>PortletWindow.State</code>
     * elements
     */
    protected void updateStates() {
        AnyNode a = null;
        PortletWindow.State state = null;
        if (windowStates.isEmpty()) {
            if (statesAsStates.isEmpty()) {
                statesAsStates.add(PortletWindow.State.MINIMIZED);
                statesAsStates.add(PortletWindow.State.RESIZING);
                statesAsStates.add(PortletWindow.State.MAXIMIZED);
                statesAsStates.add(PortletWindow.State.CLOSED);
                statesAsStates.add(PortletWindow.State.FLOATING);
            }
            if (statesAsStrings.isEmpty()) {
                statesAsStrings.add(PortletWindow.State.MINIMIZED.toString());
                statesAsStrings.add(PortletWindow.State.RESIZING.toString());
                statesAsStrings.add(PortletWindow.State.MAXIMIZED.toString());
                statesAsStrings.add(PortletWindow.State.CLOSED.toString());
                statesAsStrings.add(PortletWindow.State.FLOATING.toString());
            }
        }
        for (int i = 0; i < windowStates.size(); i++) {
            a = (AnyNode) windowStates.get(i);
            try {
                state = PortletWindow.State.toState(a.getLocalName());
                statesAsStates.add(state);
                statesAsStrings.add(state.toString());
            } catch (IllegalArgumentException e) {
                log.error("unable to parse state: " + state);
            }
        }
        for (int i = 0; i < statesAsStates.size(); i++) {
            PortletWindow.State s = (PortletWindow.State) statesAsStates.get(i);
        }
    }

    /**
     * Returns the allowed window states as <code>PortletWindow.State</code> objects
     *
     * @return the allowed window states
     */
    public List getPortletWindowStates() {
        if (statesAsStates.isEmpty()) {
            updateStates();
        }
        return statesAsStates;
    }

}
