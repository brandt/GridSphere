/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class PortletCollection {

    public PortletCollection() {}

    private Vector PortletDefList = new Vector();

    /**
     * gets the List of PortletApps
     *
     * @return vector of portletapps
     */
    public Vector getPortletDefList() {
        return PortletDefList;
    }

    /**
     * set the Vector of portletapps
     * (never used, only testing)
     *
     */
    public void setPortletDefList(Vector portletDefList) {
        this.PortletDefList = portletDefList;
    }
}
