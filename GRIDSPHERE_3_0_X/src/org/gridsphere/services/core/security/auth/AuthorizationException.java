/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 14, 2003
 * Time: 3:15:19 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.security.auth;

import java.util.Map;
import java.util.TreeMap;

public class AuthorizationException extends RuntimeException {

    public Map invalidParameters = new TreeMap();

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String msg) {
        super(msg);
    }

    public Map getInvalidParameters() {
        return this.invalidParameters;
    }

    public void putInvalidParameter(String name, String explanation) {
        this.invalidParameters.put(name, explanation);
    }
}
