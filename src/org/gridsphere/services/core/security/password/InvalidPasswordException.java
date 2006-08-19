/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 12:09:21 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.security.password;

import org.gridsphere.portlet.service.PortletServiceException;

public class InvalidPasswordException extends PortletServiceException {

    private String explanation = "";

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String msg) {
        super(msg);
    }

    public String getExplanation() {
        return this.explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
