/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class HiddenFieldBean extends TextFieldBean {

    public static final String NAME = "hf";

    public HiddenFieldBean() {
        super(NAME);
    }

    public HiddenFieldBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
    }

    public HiddenFieldBean(PortletRequest req, String beanId) {
        super(NAME);
        this.request = req;
        this.beanId = beanId;
    }

    public String toStartString() {
        this.inputtype = "hidden";
        return super.toStartString();
    }

}
