/**
 * @version $Id: JaasCallbackHandler.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.security.auth.modules.impl;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;

public class JaasCallbackHandler implements CallbackHandler {

    protected String username = null;
    protected String password = null;

    private PortletLog log = SportletLog.getInstance(JaasCallbackHandler.class);

    public JaasCallbackHandler(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public void handle(Callback callbacks[])
        throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                if (log.isDebugEnabled()) log.debug("responding to NameCallback");
                ((NameCallback) callbacks[i]).setName(username);
            } else if (callbacks[i] instanceof PasswordCallback) {
                if (log.isDebugEnabled()) log.debug("responding to PasswordCallback");
                ((PasswordCallback) callbacks[i]).setPassword(password != null ? password.toCharArray() : new char[0]);
            } else {
                if (log.isDebugEnabled()) log.debug("unsupported callback: " + callbacks[i].getClass());
                throw new UnsupportedCallbackException(callbacks[i]);
            }
        }
    }

}
