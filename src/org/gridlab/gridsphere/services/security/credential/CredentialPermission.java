/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This class is used to describe a permission to use credentials with subjects
 * that match a given pattern. For example, given a permission to use credentials
 * with subjects matching the pattern<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;"/O=Grid/O=Globus/OU=gridsphere.org*"<br>
 * then a credential containing the subject<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;"/O=Grid/O=Globus/OU=gridsphere.org/CN=John Doe"<br>
 * would be permitted for use with GridSphere.
 */
package org.gridlab.gridsphere.services.security.credential;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public interface CredentialPermission {

    /**
     */
    public String getPermittedSubjects();

    /**
     */
    public String getDescription();

    /**
     */
    public void setDescription(String description);

    /**
     */
    public boolean isCredentialPermitted(String subject);

}
