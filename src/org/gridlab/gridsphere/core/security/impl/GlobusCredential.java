/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 */
package org.gridlab.gridsphere.core.security.impl;

import org.gridlab.gridsphere.core.security.Credential;

import org.globus.security.GlobusProxy;
import org.globus.security.GlobusProxyException;

import java.util.Date;

public class GlobusCredential implements Credential {

    private GlobusProxy globusProxy = null;
    
    public GlobusCredential(GlobusProxy globusProxy) {
        this.globusProxy = globusProxy;
    }

    public GlobusProxy getGlobusProxy() {
        return this.globusProxy;
    }    

    public String getSubject() {
        return this.globusProxy.getSubject();
    }

    public String getIssuer() {
        return this.globusProxy.getSubject();
    }
    
    public long getTimeLeft() {
        return this.globusProxy.getTimeLeft();
    }
    
    public Date getTimeExpires() {
        return new Date();
    }

    public void destroy() {
        this.globusProxy.release();
        this.globusProxy = null;
    }

    public String toString() {
        return this.globusProxy.toString();
    }
}
