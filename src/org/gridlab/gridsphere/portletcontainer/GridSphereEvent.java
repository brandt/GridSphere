/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletURI;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.services.registry.PortletManagerService;

public interface GridSphereEvent {

    public PortletContext getPortletContext();

    public SportletRequest getSportletRequest();

    public SportletResponse getSportletResponse();

    public PortletEventDispatcher getPortletEventDispatcher();

    public DefaultPortletAction getAction();

    public boolean hasAction();

    public int getPortletComponentID();

    public SportletURI createNewAction(int PortletComponentID, String ActivePortletID);

}
