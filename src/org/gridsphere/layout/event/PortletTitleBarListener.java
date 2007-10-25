/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout.event;

/**
 * The <code>PortletTitleBarListener</code> is an interface for an observer to register to
 * receive notifications of changes to a portlet title bar component.
 */
public interface PortletTitleBarListener {

    /**
     * Gives notification that a portlet title bar event has occured
     *
     * @param event the portlet title bar event
     */
    public void handleTitleBarEvent(PortletTitleBarEvent event);

}
