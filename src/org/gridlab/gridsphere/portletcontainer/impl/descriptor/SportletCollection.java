/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl.descriptor;

import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * A <code>SportletCollection</code> is a container for
 * a collection of <code>SportletDefinition</code>s.
 */
public class SportletCollection {

    private List portletDefList = new ArrayList();
    private String groupOwnerName = GridSphereConfig.getProperty(GridSphereConfigProperties.CORE_GROUP_NAME);

    /**
     * Constructs an instance of SportletCollection
     */
    public SportletCollection() {
    }

    /**
     * Returns the list of portlet definitions
     *
     * @return a list of portlet definitions
     */
    public List getPortletDefinitionList() {
        return portletDefList;
    }

    /**
     * Sets the list of portlet definitions
     *
     * @param portletDefList an <code>ArrayList</code> containing the portlet definitions
     */
    public void setPortletDefinitionList(ArrayList portletDefList) {
        this.portletDefList = portletDefList;
    }

    /**
     * Sets the group owner name of this portlet collection
     *
     * @param groupOwnerName the group owner of this porlet collection
     */
    public void setGroupOwnerName(String groupOwnerName) {
        this.groupOwnerName = groupOwnerName;
    }

    /**
     * Returns the group owner name of this portlet collection
     *
     * @return the group owner name of this portlet collection
     */
    public String getGroupOwnerName() {
        return groupOwnerName;
    }
}

