/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.event.Event;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletResponse;

public interface GridSphereEvent extends Event {

    public PortletContext getPortletContext();

    public PortletResponse getPortletResponse();

    public DefaultPortletAction getAction();

    public boolean hasAction();

    public int getPortletComponentID();

}
