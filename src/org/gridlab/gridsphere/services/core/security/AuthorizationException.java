/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 14, 2003
 * Time: 3:15:19 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String msg) {
        super(msg);
    }
}
