/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class PortletDefinition {

    private Vector PortletAppList = new Vector();

    /**
     * gets the List of PortletApps
     *
     * @return vector of portletapps
     */
    public List getPortletAppList() {
        return PortletAppList;
    }

    /**
     * set the Vector of portletapps
     * (never used, only testing)
     *
     */
    public void setPortletAppList(Vector portletAppList) {
        this.PortletAppList = portletAppList;
    }

    /**
     * adds a portletapp to the List
     * (never used, only testing)
     */
    public void addPortletApp(PortletApplication app) {
        PortletAppList.add(app);
    }
}


