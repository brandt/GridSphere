package org.gridsphere.filters.impl.descriptor;

import java.util.List;
import java.util.Vector;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class PortalFilterCollection {

    private List<PortalFilterDefinition> portalFilterList = new Vector<PortalFilterDefinition>();

    public List<PortalFilterDefinition> getPortalFilterList() {
        return portalFilterList;
    }

    public void setPortalFilterList(List<PortalFilterDefinition> portalFilterList) {
        this.portalFilterList = portalFilterList;
    }

}
