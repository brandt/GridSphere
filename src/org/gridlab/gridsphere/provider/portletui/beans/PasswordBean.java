/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>PasswordBean</code> represents a password input element
 */
public class PasswordBean extends TextFieldBean {

    public static final String NAME = "pb";

    /**
     * Constructs a default password bean
     */
    public PasswordBean() {
        super(NAME);
    }

    /**
     * Constructs a password bean using a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public PasswordBean(PortletRequest req, String beanId) {
        super(NAME, beanId);
        this.request = req;
    }

    public String toStartString() {
        this.inputtype = "password";
        return super.toStartString();
    }
}
