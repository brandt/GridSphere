/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class CheckBoxBean extends SelectElementBean {

    public static final String CHECKBOX_STYLE = "portlet-frame-text";

    public static final String NAME = "cb";

    public CheckBoxBean() {
        super(NAME);
        this.cssStyle = CHECKBOX_STYLE;
    }

    public CheckBoxBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssStyle = CHECKBOX_STYLE;
    }


    public CheckBoxBean(PortletRequest request, String beanId) {
        super(NAME);
        this.request = request;
        this.beanId = beanId;
        this.cssStyle = CHECKBOX_STYLE;
    }

    public String toString() {
        return super.toString("checkbox");
    }

}
