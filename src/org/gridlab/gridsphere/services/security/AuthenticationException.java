/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:25:15 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security;

import java.util.Map;
import java.util.TreeMap;

public class AuthenticationException extends Exception {

    private Map invalidParameters = new TreeMap();

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String msg) {
        super(msg);
    }

    public Map getInvalidParameters() {
        return this.invalidParameters;
    }

    public void putInvalidParameter(String name, String explanation) {
        this.invalidParameters.put(name, explanation);
    }
}
