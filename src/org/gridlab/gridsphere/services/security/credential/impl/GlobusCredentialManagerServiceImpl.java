/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This class is used for managing Globus credentials on behalf of portlet users.
 */
package org.gridlab.gridsphere.services.security.credential.impl;

/** GridSphere portlet imports **/
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/** GridSphere services imports **/
import org.gridlab.gridsphere.services.security.credential.CredentialManagerService;

/** GridSphere security imports **/
import org.gridlab.gridsphere.core.security.*;
import org.gridlab.gridsphere.core.security.impl.GlobusCredential;
import org.gridlab.gridsphere.core.security.impl.GlobusCredentialRetrievalClient;

/** Globus imports **/
import org.globus.security.GlobusProxy;
import org.globus.security.GlobusProxyException;

/** JDK imports **/
import java.util.List;

public class GlobusCredentialManagerServiceImpl extends AbstractCredentialManager
    implements PortletServiceProvider, CredentialManagerService {

    private static PortletLog _log = SportletLog.getInstance(GlobusCredentialManagerServiceImpl.class);

    /****** PORTLET SERVICE METHODS *******/

    public void init(PortletServiceConfig config) {
        _log.info("Entering init()");
        setCredentialRetrievalClient(config);
        _log.info("Exiting init()");
    }

    public void destroy() {
        destroyAllCredentials();
    }

    private void setCredentialRetrievalClient(PortletServiceConfig config) {
        // Hostname init parameter
        String host = config.getInitParameter("CredentialManagerService.retrievalHost");
        if (host == null) {
            host = "";
        }
        if (host.equals("")) {
            host = GlobusCredentialRetrievalClient.DEFAULT_HOST;
            _log.warn("Credential retrieval host not set. Using default value " + host);
        }
        // Port init parameter
        int port = GlobusCredentialRetrievalClient.DEFAULT_PORT;
        try {
            port = (new Integer(config.getInitParameter("CredentialManagerService.retrievalPort"))).intValue();
        } catch (Exception e) {
            _log.warn("Credential retrieval port not valid. Using default value " + port);
        }
        // Lifetime init parameter
        long lifetime =  GlobusCredentialRetrievalClient.DEFAULT_LIFETIME;
        try {
            lifetime = (new Long(config.getInitParameter("CredentialManagerService.retrievalLifetime"))).longValue();
        } catch (Exception e) {
          _log.warn("Credential retrieval lifetime not valid. Using default value " + lifetime);
        }
        _log.info("Credential retrieval hostname = " + host);
        _log.info("Credential retrieval port = " + port);
        _log.info("Credential default lifetime = " + lifetime);
        // Apply init parameters
        GlobusCredentialRetrievalClient client =
                new GlobusCredentialRetrievalClient(host, port, lifetime);
        // Save credential retrieval client
        setCredentialRetrievalClient(client);
    }
}
