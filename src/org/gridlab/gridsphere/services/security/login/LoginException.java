/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Dec 18, 2002
 * Time: 2:11:40 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.login;

import java.util.Map;
import java.util.TreeMap;

public class LoginException extends Exception {

    private Map invalidParameters = new TreeMap();

    public LoginException() {
        super();
    }

    public LoginException(String msg) {
        super(msg);
    }

    public Map getInvalidParameters() {
        return this.invalidParameters;
    }

    public void putInvalidParameter(String name, String explanation) {
        this.invalidParameters.put(name, explanation);
    }
}
