package org.gridsphere.services.core.security.auth.modules.impl;

import java.util.Map;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public class AuthenticationParameters  {
    protected String username = null;
    protected String password = null;
    protected Map parameters = null;
    protected Object request = null;

    public AuthenticationParameters() {
    }

    public AuthenticationParameters(String username, String password, Map parameters, Object request) {
        this.username = username;
        this.password = password;
        this.parameters = parameters;
        this.request = request;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }
}

