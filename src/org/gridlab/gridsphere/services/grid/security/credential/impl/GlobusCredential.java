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
import org.ietf.jgss.GSSCredential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;

import java.util.Date;
import java.util.StringTokenizer;

public class GlobusCredential implements Credential {

    //private org.globus.gsi.GlobusCredential globusProxy = null;
    private GlobusGSSCredentialImpl gssCredential = null;

    private String dn = null;
    private String subject = null;

    private GlobusCredential() {
    }

    public GlobusCredential(GlobusGSSCredentialImpl globusCredential) {
        this.gssCredential = globusCredential;
        this.dn = globusCredential.getGlobusCredential().getIdentity();
        this.subject = translateSubject(globusCredential.getGlobusCredential().getSubject());
    }

    public GSSCredential getGSSProxy() {
        return this.gssCredential;
    }

    public org.globus.gsi.GlobusCredential getGlobusProxy() {
        return this.gssCredential.getGlobusCredential();
    }

    public String getDN() {
        return this.dn;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getIssuer() {
        return this.gssCredential.getGlobusCredential().getIssuer();
    }

    public Date getTimeExpires() {
        long value = (new Date()).getTime() + this.gssCredential.getGlobusCredential().getTimeLeft();
        return new Date(value);
    }

    public long getTimeLeft() {
        return this.gssCredential.getGlobusCredential().getTimeLeft();
    }
    
    public boolean isExpired() {
        return (this.gssCredential.getGlobusCredential().getTimeLeft() == 0);
    }

    public void destroy() {
        this.gssCredential = null;
    }

    public String toString() {
        return this.gssCredential.toString();
    }

    /**
     * Retrieve DN from given credential subject.
     *  For example, given the following subject:
     *    "/O=Grid/O=Globus/OU=gridsphere.org/CN=Jane Doe/CN=proxy/CN=proxy/CN=proxy"
     *  wer return the following dn:
     *    "/O=Grid/O=Globus/OU=gridsphere.org/CN=Jane Doe"
     */
    public static String retrieveDN(String subject) {
        int index = subject.indexOf("/CN=proxy");
        if (index == -1) {
            return subject;
        }
        return subject.substring(0, index);
    }

    /**
      * Reverses the order of the given subject elements, converts "," to "/",
      * and trims each element to remove unwanted spacing.
      *  For example:
      *    "CN=proxy,CN=proxy,CN=proxy,CN=Jane Doe,OU=gridsphere.org,O=Globus,O=Grid"
      *  translates to:
      *    "/O=Grid/O=Globus/OU=gridsphere.org/CN=Jane Doe/CN=proxy/CN=proxy/CN=proxy"
      */
     public static String translateSubject(String subject) {
         StringBuffer translatedSubjectBuffer = new StringBuffer();
         StringTokenizer tokenizer = new StringTokenizer(subject, ",");
         while (tokenizer.hasMoreTokens()) {
             translatedSubjectBuffer.insert(0, tokenizer.nextToken().trim());
             translatedSubjectBuffer.insert(0, "/");
         }
         String translatedSubject = translatedSubjectBuffer.toString();
         return translatedSubject;
     }
}
