/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.ArrayList;
import java.util.List;

public class PortletCollection {

    public PortletCollection() {}

    private List PortletDefList = new ArrayList();

    /**
     * gets the List of PortletApps
     *
     * @return ArrayList of portletapps
     */
    public List getPortletDefList() {
        return PortletDefList;
    }

    /**
     * set the ArrayList of portletapps
     * (never used, only testing)
     *
     */
    public void setPortletDefList(ArrayList portletDefList) {
        this.PortletDefList = portletDefList;
    }

}

