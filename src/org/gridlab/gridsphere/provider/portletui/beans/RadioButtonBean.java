/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class RadioButtonBean extends SelectElementBean {

    public static final String RADIO_STYLE = "portlet-frame-text";

    public static final String NAME = "rb";

    public RadioButtonBean() {
        super(NAME);
        this.cssStyle = RADIO_STYLE;
    }

    public RadioButtonBean(PortletRequest request, String id) {
        super(NAME);
        this.cssStyle = RADIO_STYLE;
        this.request = request;
        this.beanId = id;
    }

    public String toString() {
        return super.toString("radio");
    }
}
