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

/** Globus imports **/
import org.globus.myproxy.MyProxy;
import org.globus.myproxy.MyProxyException;
import org.globus.security.GlobusProxy;
import org.globus.security.GlobusProxyException;

/** JDK imports **/
import java.util.List;
import java.util.Vector;

public class GlobusCredentialRetrievalClient implements CredentialRetrievalClient {

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = MyProxy.DEFAULT_PORT;
    public static final long DEFAULT_LIFETIME = 36000;

    private static PortletLog _log = SportletLog.getInstance(GlobusCredentialRetrievalClient.class);

    private MyProxy myProxy = null;
    private long lifetime = 0;

    private GlobusCredentialRetrievalClient() {
      // Force explicit setting of hostname and port
    }
    
    public GlobusCredentialRetrievalClient(String host, int port) {
        _log.info("Entering GlobusCredentialRetrievalClient(host, port)");
        _log.info("MyProxy host = " + host);
        _log.info("MyProxy port = " + port);
        long lifetime = DEFAULT_LIFETIME;
        _log.info("MyProxy lifetime = " + lifetime);
        /** Apply init parameters **/
        // MyProxy instance
        this.myProxy = new MyProxy(host, port);
        // Default lifetime
        this.lifetime = lifetime;
        _log.info("Exiting GlobusCredentialRetrievalClient(hostname, port)");
    }

    public GlobusCredentialRetrievalClient(String host, int port, long lifetime) {
        _log.info("Entering GlobusCredentialRetrievalClient(host, port, lifetime)");
        _log.info("MyProxy host = " + host);
        _log.info("MyProxy port = " + port);
        _log.info("MyProxy lifetime = " + lifetime);
        /** Apply init parameters **/
        // MyProxy instance
        this.myProxy = new MyProxy(host, port);
        // Default lifetime
        this.lifetime = lifetime;
        _log.info("Exiting GlobusCredentialRetrievalClient(hostname, port, lifetime)");
    }

    public String getProtocol() {
        return MyProxy.MYPROXY_PROTOCOL_VERSION;
    }

    public String getHost() {
        return this.myProxy.getHost();
    }

    public int getPort() {
        return this.myProxy.getPort();
    }

    public long getCredentialLifetime() {
        return this.lifetime;
    }

    public void setCredentialLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public List retrieveCredentials(String username, String passphrase)
        throws CredentialRetrievalException {
        return retrieveCredentials(username, passphrase, this.lifetime);
    }

    public List retrieveCredentials(String username, String passphrase, long lifetime)
        throws CredentialRetrievalException {
        _log.info("Entering retrieveCredentials(user, passphrase, lifetime)");
        // Retrieve credential from MyProxy
        GlobusCredential credential = myProxyGet(username, passphrase, this.lifetime);
        // Insert credential into a list and return
        List credentials = new Vector();
        credentials.add(credential);
        _log.info("Exiting retrieveCredentials(user, passphrase, lifetime)");
        return credentials;
    }

    public Credential retrieveCredential(String username, String passphrase, String subject)
        throws CredentialRetrievalException {
        return retrieveCredential(username, passphrase, subject, this.lifetime);
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
        if (!credential.getSubject().equals(subject)) {
            String m = "Expected credential with subject " + subject 
                     + ", MyProxy returned credential with subject "
                     + credential.getSubject();
            CredentialRetrievalException e = new CredentialRetrievalException(m);
            _log.error(m, e);
            throw e;
        }
        _log.info("Exiting retrieveCredential(username, passphrase, subject, lifetime)");
        return credential;
    }

    GlobusCredential myProxyGet(String username, String passphrase, long lifetime)
        throws CredentialRetrievalException {
        _log.info("Entering myProxyGet(username, passphrase, lifetime)");
        if (_log.isDebugEnabled()) {
            _log.debug("Username = " + username);
            _log.debug("Lifetime = " + lifetime);
        }
        // Retrieve Globus proxy from MyProxy
        GlobusProxy userProxy = null;
        try {
            GlobusProxy gridProxy = getGridSphereProxy();
            userProxy = this.myProxy.get(gridProxy, username, passphrase, (int)lifetime);
        } catch (GlobusProxyException e) {
            String m = "Error retrieving Globus proxy with MyProxy client";
            _log.error(m, e);
            throw new CredentialRetrievalException(m, e);
        } catch (MyProxyException e) {
            String m = "Error retrieving Globus proxy with MyProxy client";
            _log.error(m, e);
            throw new CredentialRetrievalException(m, e);
        }
        // Instantiate and return credential
        GlobusCredential credential = new GlobusCredential(userProxy);
        if (_log.isDebugEnabled()) {
            _log.debug("Globus credential = " + credential.toString());
        }
        _log.info("Exiting myProxyGet(username, passphrase, lifetime)");
        return credential;
    }

    GlobusProxy getGridSphereProxy()
        throws GlobusProxyException {
        _log.info("Entering getGridSphereProxy()");
        GlobusProxy proxy = GlobusProxy.getDefaultUserProxy();
        _log.debug("GridSphere using proxy [" + proxy.getSubject() + "]");
        return proxy;
    }
}
