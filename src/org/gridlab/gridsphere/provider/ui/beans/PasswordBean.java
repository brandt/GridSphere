/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

public class PasswordBean extends TextFieldBean {

    public PasswordBean() {
        super();
    }

    public PasswordBean(String name, String value, boolean disabled, boolean readonly, int size, int maxlength) {
        super(name, value, disabled, readonly, size, maxlength);
    }

    public String toString() {
        this.inputtype = "password";
        return super.toString();
    }
}
