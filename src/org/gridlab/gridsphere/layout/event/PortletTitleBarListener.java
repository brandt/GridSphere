/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout.event;


import org.gridlab.gridsphere.layout.PortletLayoutException;

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
    public void handleTitleBarEvent(PortletTitleBarEvent event) throws PortletLayoutException;


}
