package org.gridsphere.layout.event;


/**
 * The <code>PortletTabListener</code> is an interface for an observer to register to
 * receive notifications of changes to a portlet tab component.
 */
public interface PortletTabListener {

    /**
     * Gives notification that a portlet tab event has occured
     *
     * @param event the portlet tab event
     */
    public void handlePortletTabEvent(PortletTabEvent event);

}
