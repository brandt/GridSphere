/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletTabEventImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.event.impl;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletTab;
import org.gridsphere.layout.event.ComponentAction;
import org.gridsphere.layout.event.PortletTabEvent;

import javax.portlet.ActionRequest;

/**
 * A <code>PortletTabEventImpl</code> is the concrete implementation of
 * <code>PortletTabEvent</code>
 */
public class PortletTabEventImpl implements PortletTabEvent {

    private ComponentAction action;
    private PortletTab portletTab;
    private int id;
    private ActionRequest request;


    /**
     * Constructs an instance of PortletTabEventImpl from a portlet tab, a
     * tab event action and the portlet tab component id
     *
     * @param portletTab the portlet tab
     * @param action     the portlet tab event action
     * @param id         the portlet component id
     * @see PortletTab
     */
    public PortletTabEventImpl(PortletTab portletTab, ActionRequest request, ComponentAction action, int id) {
        this.action = action;
        this.portletTab = portletTab;
        this.id = id;
        this.request = request;
    }

    public ActionRequest getRequest() {
        return request;
    }

    /**
     * Returns true if this event actually triggered an action
     *
     * @return true if this event actually triggered an action
     */
    public boolean hasAction() {
        return (action != null);
    }

    /**
     * Returns the portlet tab event action
     *
     * @return the portlet tab event action
     */
    public ComponentAction getAction() {
        return action;
    }

    /**
     * Returns the PortletTab that was selected
     *
     * @return the PortletTab that was selcted
     */
    public PortletComponent getPortletComponent() {
        return portletTab;
    }

    /**
     * Returns the component id of the portlet tab
     *
     * @return the component id of the portlet tab
     */
    public int getID() {
        return id;
    }

}
