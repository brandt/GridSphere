/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.security.acl.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
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

    public SportletGroup getPortletGroup() {
        SportletGroup group = new SportletGroup();
        group.setName(groupDescription.getGroupName().toLowerCase());
        group.setDescription(groupDescription.getGroupDescription());
        //boolean ispublic = true;
        String visibility = groupDescription.getGroupVisibility();
        if ("PRIVATE".equalsIgnoreCase(visibility)) {
            group.setGroupType(PortletGroup.PRIVATE.getType());
        }
        if ("PUBLIC".equalsIgnoreCase(visibility)) {
            group.setGroupType(PortletGroup.PUBLIC.getType());
        }
        if ("HIDDEN".equalsIgnoreCase(visibility)) {
            group.setGroupType(PortletGroup.HIDDEN.getType());
        }
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
