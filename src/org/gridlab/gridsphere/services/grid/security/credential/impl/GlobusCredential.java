/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * Implements a Globus credential by wrapping a Globus proxy object. It also 
 * provides a method for retrieving the contained Globus proxy for using 
 * Globus services.
 */
package org.gridlab.gridsphere.services.grid.security.credential.impl;

import org.gridlab.gridsphere.services.grid.security.credential.Credential;

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
    
    public Date getTimeExpires() {
        long value = (new Date()).getTime() + this.globusProxy.getTimeLeft();
        return new Date(value);
    }

    public long getTimeLeft() {
        return this.globusProxy.getTimeLeft();
    }
    
    public boolean isExpired() {
        return (this.globusProxy.getTimeLeft() == 0);
    }

    public void destroy() {
        this.globusProxy.release();
        this.globusProxy = null;
    }

    public String toString() {
        return this.globusProxy.toString();
    }
}
