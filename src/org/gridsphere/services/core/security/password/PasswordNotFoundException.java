/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 12:08:01 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.security.password;

public class PasswordNotFoundException extends Exception {

    public PasswordNotFoundException() {
        super();
    }

    public PasswordNotFoundException(String msg) {
        super(msg);
    }

}
