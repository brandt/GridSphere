/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This interface extends <code>org.gridlab.gridsphere.services.CredentialRetrievalService</code>.
 * and describes the interface to the MyProxy service.
 */
package org.gridlab.gridsphere.services.security.credential.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.security.credential.CredentialRetrievalService;


import org.globus.myproxy.MyProxy;

public interface MyProxyService extends CredentialRetrievalService {
    public static final String PROTOCOL_VERSION = MyProxy.MYPROXY_PROTOCOL_VERSION;
    public static final int DEFAULT_PORT = MyProxy.DEFAULT_PORT;
    public static final long DEFAULT_LIFETIME = 36000;
}
