package org.gridsphere.filters.impl.descriptor;

import java.util.List;
import java.util.Vector;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class PortalFilterCollection {

    private List portalFilterList = new Vector();

    public List getPortalFilterList() {
        return portalFilterList;
    }

    public void setPortalFilterList(List portalFilterList) {
        this.portalFilterList = portalFilterList;
    }

}