/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 */
package org.gridlab.gridsphere.services.security.credential.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import org.gridlab.gridsphere.core.security.Credential;
import org.gridlab.gridsphere.core.security.CredentialRetrievalException;
import org.gridlab.gridsphere.core.security.impl.GlobusCredential;

import java.util.List;
import java.util.Vector;

import org.globus.myproxy.MyProxy;
import org.globus.myproxy.MyProxyException;
import org.globus.security.GlobusProxy;

public class MyProxyServiceImpl implements PortletServiceProvider, MyProxyService {

    private static PortletLog log = SportletLog.getInstance(MyProxyServiceImpl.class);

    private MyProxy myProxy = null;
    private long lifetime = 0;
    
    public void init(PortletServiceConfig config) {
        this.log.info("Entering init()");
        /** Get init parameters **/
        // Hostname init parameter
        String hostname = config.getInitParameter("hostname");
        if (hostname == null) {
            hostname = "";
        } else if (hostname.equals("")) {
        }
        // Port init parameter
        int port = DEFAULT_PORT;
        try {
            port = (new Integer(config.getInitParameter("port"))).intValue();
        } catch (Exception e) {
            this.log.warn("Port not a valid. Using default value " + port);
        }
        // Lifetime init parameter
        long lifetime = DEFAULT_LIFETIME;
        try {
            lifetime = (new Long(config.getInitParameter("port"))).longValue();
        } catch (Exception e) {
          this.log.warn("Lifetime not a valid. Using default value " + DEFAULT_LIFETIME);
        }
        log.info("MyProxy hostname = " + hostname);
        log.info("MyProxy port = " + port);
        log.info("Credential lifetime = " + lifetime);
        /** Apply init parameters **/
        // MyProxy instance
        this.myProxy = new MyProxy(hostname, port);
        // Default lifetime
        this.lifetime = lifetime;
    }

    public void destroy() {
        log.info("Entering destroy()");
        // Nullify MyProxy instance
        this.myProxy = null;
    }
    
    public String getHostname() {
        return this.myProxy.getHost();
    }

    public void setHostname(String hostname) {
        this.myProxy.setHost(hostname);
    }

    public int getPort() {
        return this.myProxy.getPort();
    }

    public void setPort(int port) {
        this.myProxy.setPort(port);
    }

    public long getDefaultCredentialLifetime() {
        return this.lifetime;
    }

    public void setDefaultCredentialLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public List retrieveCredentials(String username, String passphrase)
        throws CredentialRetrievalException {
        return retrieveCredentials(username, passphrase, this.lifetime);
    }

    public List retrieveCredentials(String username, String passphrase, long lifetime)
        throws CredentialRetrievalException {
        this.log.info("Entering retrieveCredentials(username, passphrase, lifetime)");
        // Retrieve credential from MyProxy
        GlobusCredential credential = myProxyGet(username, passphrase, this.lifetime);
        // Insert credential into a list and return
        List credentials = new Vector();
        credentials.add(credential);
        this.log.info("Exiting retrieveCredentials(username, passphrase, lifetime)");
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
        this.log.info("Entering retrieveCredential(username, passphrase, subject, lifetime)");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Subject = " + subject);
        }
        // Retrieve credential from MyProxy
        GlobusCredential credential = myProxyGet(username, passphrase, lifetime);
        // Throw exception if subject not what we expected
        if (!credential.getSubject().equals(subject)) {
            String m = "Expected credential with subject " + subject 
                     + ", MyProxy returned credential with subject "
                     + credential.getSubject();
            CredentialRetrievalException e = new CredentialRetrievalException(m);
            this.log.error(m, e);
            throw e;
        }
        this.log.info("Exiting retrieveCredential(username, passphrase, subject, lifetime)");
        return credential;
    }

    GlobusCredential myProxyGet(String username, String passphrase, long lifetime) 
        throws CredentialRetrievalException {
        this.log.info("Entering myProxyGet(username, passphrase, lifetime)");
        if (this.log.isDebugEnabled()) {
            this.log.debug("Username = " + username);
            this.log.debug("Lifetime = " + lifetime);
        }
        // Retrieve Globus proxy from MyProxy
        GlobusProxy globusProxy = null;
        try {
            globusProxy = this.myProxy.get(username, passphrase, (int)lifetime);
        } catch (MyProxyException e) {
            String m = "Error retrieving Globus proxy with MyProxy client";
            this.log.error(m, e);
            throw new CredentialRetrievalException(m, e);
        }
        // Instantiate and return credential
        GlobusCredential credential = new GlobusCredential(globusProxy);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Globus credential = " + credential.toString());
        }
        this.log.info("Exiting myProxyGet(username, passphrase, lifetime)");
        return credential;
    }
}
