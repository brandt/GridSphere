/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class PortletDefinition {

    private PortletApp portletApp = new PortletApp();
    private Vector concreteApps = new Vector();

    public Vector getConcreteApps() {
        return concreteApps;
    }

    public void setConcreteApps(Vector concreteApps) {
        this.concreteApps = concreteApps;
    }

    public void setPortletApp(PortletApp portletApp) {
        this.portletApp = portletApp;
    }

    public PortletApp getPortletApp() {
        return portletApp;
    }

}


