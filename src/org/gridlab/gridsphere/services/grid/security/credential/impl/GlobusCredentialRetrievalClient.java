/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 */
package org.gridlab.gridsphere.services.grid.security.credential.impl;

/** GridSphere portlet imports **/
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/** Security imports **/
import org.gridlab.gridsphere.services.grid.security.credential.Credential;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialRetrievalClient;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialRetrievalException;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialException;

/** Globus imports **/
import org.globus.myproxy.MyProxy;
import org.globus.myproxy.MyProxyException;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.gssapi.GlobusGSSException;
import org.ietf.jgss.GSSCredential;
import org.gridforum.jgss.ExtendedGSSManager;

/** JDK imports **/
import java.util.List;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.io.File;
import java.io.FileOutputStream;

public class GlobusCredentialRetrievalClient implements CredentialRetrievalClient {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = MyProxy.DEFAULT_PORT;
    public static final long DEFAULT_LIFETIME = 36000;

    private static PortletLog _log = SportletLog.getInstance(GlobusCredentialRetrievalClient.class);

    private GSSCredential portalProxy = null;
    private String myProxyHost = null;
    private int myProxyPort = DEFAULT_PORT;

    private long myProxyLifetime = DEFAULT_LIFETIME;

    private GlobusCredentialRetrievalClient() {
      // Force explicit setting of hostname and port
    }

    public GlobusCredentialRetrievalClient(String host, int port) {
        _log.info("Entering GlobusCredentialRetrievalClient(host, port)");
        // Set host
        this.myProxyHost = host;
        _log.info("MyProxy host = " + myProxyHost);
        // Set port
        if (port > 0) {
            this.myProxyPort = port;
        }
        _log.info("MyProxy port = " + myProxyPort);
        // Default lifetime
        _log.info("MyProxy lifetime = " + myProxyLifetime);
        _log.info("Exiting GlobusCredentialRetrievalClient(hostname, port)");
    }

    public GlobusCredentialRetrievalClient(String host, int port, long lifetime) {
        _log.info("Entering GlobusCredentialRetrievalClient(host, port, lifetime)");
        // Set host
        this.myProxyHost = host;
        _log.info("MyProxy host = " + myProxyHost);
        // Set port
        if (port > 0) {
            this.myProxyPort = port;
        }
        _log.info("MyProxy port = " + myProxyPort);
        // Set lifetime
        if (lifetime > 0) {
            this.myProxyLifetime = lifetime;
        }
        _log.info("MyProxy lifetime = " + myProxyLifetime);
        _log.info("Exiting GlobusCredentialRetrievalClient(hostname, port, lifetime)");
    }

    public String getProtocol() {
        return MyProxy.MYPROXY_PROTOCOL_VERSION;
    }

    public String getHost() {
        return this.myProxyHost;
    }

    public int getPort() {
        return this.myProxyPort;
    }

    public long getCredentialLifetime() {
        return this.myProxyLifetime;
    }

    public void setCredentialLifetime(long lifetime) {
        this.myProxyLifetime = lifetime;
    }

    private GSSCredential getPortalGlobusProxy()
            throws CredentialException {
        if (this.portalProxy == null) {
            _log.debug("Portal credential has not been set yet");
            try {
                ExtendedGSSManager manager = (ExtendedGSSManager)ExtendedGSSManager.getInstance();
                this.portalProxy = manager.createCredential(GSSCredential.INITIATE_AND_ACCEPT);
            } catch (Exception e) {
                String m = "Error getting portal credential";
                _log.error(m, e);
                throw new CredentialException(m, e);
            }
        }
        return this.portalProxy;
    }

   public void setPortalCredential(String proxyFile)
        throws CredentialException {
        try {
            // Set portal globus proxy
            org.globus.gsi.GlobusCredential globusProxy = new org.globus.gsi.GlobusCredential(proxyFile);
        } catch (org.globus.gsi.GlobusCredentialException e) {
            _log.error("Unable to set portal globus proxy", e);
            throw new CredentialException(e.getMessage());
        }
   }

    public List retrieveCredentials(String username, String passphrase)
        throws CredentialRetrievalException {
        return retrieveCredentials(username, passphrase, this.myProxyLifetime);
    }

    public List retrieveCredentials(String username, String passphrase, long lifetime)
        throws CredentialRetrievalException {
        _log.info("Entering retrieveCredentials(user, passphrase, lifetime)");
        // Retrieve credential from MyProxy
        GlobusCredential credential = myProxyGet(username, passphrase, this.myProxyLifetime);
        // Insert credential into a list and return
        List credentials = new Vector();
        credentials.add(credential);
        _log.info("Exiting retrieveCredentials(user, passphrase, lifetime)");
        return credentials;
    }

    public Credential retrieveCredential(String username, String passphrase, String subject)
        throws CredentialRetrievalException {
        return retrieveCredential(username, passphrase, subject, this.myProxyLifetime);
    }

    public Credential retrieveCredential(String username,
                                         String passphrase,
                                         String subject,
                                         long lifetime)
        throws CredentialRetrievalException {
        _log.info("Entering retrieveCredential(username, passphrase, subject, lifetime)");
        if (_log.isDebugEnabled()) {
            _log.debug("Subject = " + subject);
        }
        // Retrieve credential from MyProxy
        GlobusCredential credential = myProxyGet(username, passphrase, lifetime);
        // Throw exception if subject not what we expected
        if (!credential.getDN().equals(subject)) {
            String m = "Expected credential with identity " + subject
                     + ", MyProxy returned credential with identity "
                     + credential.getDN();
            CredentialRetrievalException e = new CredentialRetrievalException(m);
            _log.error(m, e);
            throw e;
        }
        _log.info("Exiting retrieveCredential(username, passphrase, subject, lifetime)");
        return credential;
    }

    private GlobusCredential myProxyGet(String username, String passphrase, long lifetime)
        throws CredentialRetrievalException {
        _log.info("Entering myProxyGet(username, passphrase, lifetime)");
        if (_log.isDebugEnabled()) {
            _log.debug("Username = " + username);
            _log.debug("Lifetime = " + lifetime);
        }
        // Get portal proxy
        GSSCredential portalProxy = null;
        try {
            portalProxy = getPortalGlobusProxy();
        } catch (CredentialException e) {
            String m = "Error retrieving Globus proxy with MyProxy client";
            _log.error(m, e);
            throw new CredentialRetrievalException(m, e);
        }
        // Instantiate MyProxy client
        MyProxy myProxy = new MyProxy(this.myProxyHost, this.myProxyPort);
        // Retrieve Globus proxy from MyProxy
        GlobusGSSCredentialImpl userProxy = null;
        try {
            userProxy =
                (GlobusGSSCredentialImpl)
                    myProxy.get(portalProxy, username, passphrase, (int)lifetime);
        } catch (MyProxyException e) {
            String m = "Error retrieving Globus proxy with MyProxy client";
            _log.error(m, e);
            throw new CredentialRetrievalException(m, e);
        }
        _log.debug("User proxy dn = " + userProxy.getGlobusCredential().getIdentity());
        // Instantiate and return credential
        GlobusCredential credential = new GlobusCredential(userProxy);
        if (_log.isDebugEnabled()) {
            _log.debug("Globus credential = " + credential.toString());
        }
        _log.info("Exiting myProxyGet(username, passphrase, lifetime)");
        return credential;
    }
}
