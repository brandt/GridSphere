/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.security.role.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class PortletRoleDescriptor {

    private PortletRoleCollection roleCollection = null;
    private static String roleMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/role-mapping.xml");
    private List portletRoles;

    /**
     * Constructor disallows non-argument instantiation
     */
    private PortletRoleDescriptor() {
    }

    public PortletRoleDescriptor(String descriptorFile) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = PersistenceManagerFactory.createPersistenceManagerXml(descriptorFile, roleMappingFile);
        roleCollection = (PortletRoleCollection) pmXML.load();
        portletRoles = new ArrayList();
        List roles = roleCollection.getPortletRolesList();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            PortletRoleDescription roleDesc = (PortletRoleDescription)it.next();
            String roleName = roleDesc.getRoleName();
            PortletRole role = new PortletRole(roleName);
            role.setDescription(roleDesc.getRoleDescription());
            portletRoles.add(role);
        }
    }

    /**
     * Returns the collection of portlet role descriptions
     *
     * @return the collection of portlet role descriptions
     */
    public PortletRoleCollection getPortletRoleCollection() {
        return roleCollection;
    }

    public List getPortletRoles() {
        return portletRoles;
    }

}
