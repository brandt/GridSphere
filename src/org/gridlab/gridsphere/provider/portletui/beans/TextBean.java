/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class TextBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "tb";

    public static final String TEXT_STYLE = "portlet-frame-label";
    public static final String TEXT_ERROR_STYLE = "portlet-frame-message-alert";

    protected boolean isError = false;

    public TextBean() {
        super(NAME);
        this.cssStyle = TEXT_STYLE;
    }

    public TextBean(PortletRequest req, String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
        this.cssStyle = TEXT_STYLE;
    }

    public boolean getError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
        if (isError) this.cssStyle = TEXT_ERROR_STYLE;
    }

    public String toString() {
        return value;
    }
}
