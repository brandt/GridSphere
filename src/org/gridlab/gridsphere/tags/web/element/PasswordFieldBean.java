/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class PasswordFieldBean extends InputFieldBean {

    public PasswordFieldBean() {
        super();
    }

    public PasswordFieldBean(String name, String value, boolean disabled, boolean readonly, int size, int maxlength) {
        super(name, value, disabled, readonly, size, maxlength);
    }

    public String toString() {
        this.inputtype = "password";
        return super.toString();
    }
}
