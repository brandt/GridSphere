/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This interface extends the <code>CredentialManager</code> and <code>PortletService</code>
 * interfaces. All portlets and services that require access to portlet user credentials
 * and related information are expected to use this service. Please see the above interfaces
 * for more details on how to use this service.
 */
package org.gridlab.gridsphere.services.security.credential;

import org.gridlab.gridsphere.core.security.CredentialManager;
import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;

public interface CredentialManagerService extends CredentialManager, PortletService {

}
