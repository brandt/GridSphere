/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class PasswordBean extends TextFieldBean {

    public static final String NAME = "pb";

    public PasswordBean() {
        super(NAME);
    }

    public PasswordBean(PortletRequest req, String beanId) {
        super(NAME, beanId);
        this.request = req;
    }

    public PasswordBean(String beanId) {
        super(NAME, beanId);
    }

    public String toString() {
        this.inputtype = "password";
        return super.toString();
    }
}
