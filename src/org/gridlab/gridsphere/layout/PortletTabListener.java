package org.gridlab.gridsphere.layout;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 9, 2003
 * Time: 10:12:18 PM
 * To change this template use Options | File Templates.
 */
public interface PortletTabListener {

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     */
    public void handlePortletTabEvent(PortletTabEvent event);

}
