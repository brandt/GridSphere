/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class TextBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "tb";

    public static final String TEXT_LABEL_STYLE = "portlet-frame-label";
    public static final String TEXT_PLAIN_STYLE = "portlet-frame-text";
    public static final String TEXT_MESSAGE_STYLE = "portlet-frame-message";
    public static final String TEXT_ERROR_STYLE = "portlet-frame-message-alert";

    public static final String BOLD = "bold";
    public static final String ITALIC = "italic";

    protected String style = "";

    public TextBean() {
        super(NAME);
        this.cssStyle = TEXT_LABEL_STYLE;
    }

    public TextBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssStyle = TEXT_LABEL_STYLE;
    }

    public TextBean(PortletRequest req, String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
        this.cssStyle = TEXT_LABEL_STYLE;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String toStartString() {

        if (style.equalsIgnoreCase("error")) {
            this.cssStyle = TEXT_ERROR_STYLE;
        } else if (style.equalsIgnoreCase("message")) {
            this.cssStyle = TEXT_MESSAGE_STYLE;
        } else if (style.equalsIgnoreCase("label")) {
            this.cssStyle = TEXT_LABEL_STYLE;
        } else if (style.equalsIgnoreCase("plain")) {
            this.cssStyle = TEXT_PLAIN_STYLE;
        }
        return "";
    }

    public String toEndString() {
        if (style.equalsIgnoreCase(BOLD)) {
            value = "<b>" + value + "</b>";
        } else if (style.equalsIgnoreCase(ITALIC)) {
            value = "<i>" + value + "</i>";
        }
        return value;
    }
}
