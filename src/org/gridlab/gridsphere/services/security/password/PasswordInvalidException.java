/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 12:09:21 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.password;

public class PasswordInvalidException extends Exception {

    private String explanation = "";

    public PasswordInvalidException() {
        super();
    }

    public PasswordInvalidException(String msg) {
        super(msg);
    }

    public String getExplanation() {
        return this.explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
