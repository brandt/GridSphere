/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.ArrayList;
import java.util.List;

public class PortletDefinition {

    private ApplicationPortletDescriptor appDescriptor = new ApplicationPortletDescriptor();
    private List concreteApps = new ArrayList();

    public List getConcreteApps() {
        return concreteApps;
    }

    public void setConcreteApps(ArrayList concreteApps) {
        this.concreteApps = concreteApps;
    }

    public void setApplicationPortletDescriptor(ApplicationPortletDescriptor portletApp) {
        this.appDescriptor = portletApp;
    }

    public ApplicationPortletDescriptor getApplicationPortletDescriptor() {
        return appDescriptor;
    }

}


