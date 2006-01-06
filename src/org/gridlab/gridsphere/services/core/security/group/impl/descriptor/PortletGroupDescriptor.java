/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.security.group.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import java.io.IOException;

public class PortletGroupDescriptor {

    private PortletGroupDescription groupDescription = null;
    private PersistenceManagerXml pmXML = null;
    private static String groupMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/group-mapping.xml");

    /**
     * Constructor disallows non-argument instantiation
     */
    private PortletGroupDescriptor() {
    }

    public PortletGroupDescriptor(String descriptorFile) throws IOException, PersistenceManagerException {
        pmXML = PersistenceManagerFactory.createPersistenceManagerXml(descriptorFile, groupMappingFile);
        groupDescription = (PortletGroupDescription) pmXML.load();
    }

    /**
     * Returns the collection of portlet service definitions
     *
     * @return the collection of portlet service definitions
     */
    public PortletGroupDescription getPortletGroupDescription() {
        return groupDescription;
    }

    public PortletGroup getPortletGroup() {
        PortletGroup group = new PortletGroup();
        group.setName(groupDescription.getGroupName().toLowerCase());
        group.setDescription(groupDescription.getGroupDescription());
        //boolean ispublic = true;
        String visibility = groupDescription.getGroupVisibility();

        PortletGroup.Type groupType = PortletGroup.Type.getType(visibility);
        group.setType(groupType);

        boolean iscore = false;
        String core = groupDescription.getCore();
        if ("yes".equalsIgnoreCase(core) || "true".equalsIgnoreCase(core)) {
            iscore = true;
        }
        //group.setPublic(ispublic);
        group.setCore(iscore);
        group.setPortletRoleList(groupDescription.getPortletRoleInfo());
        return group;
    }

}
