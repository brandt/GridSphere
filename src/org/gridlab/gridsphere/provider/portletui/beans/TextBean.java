/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * The <code>TextBean</code> represents text to be displayed
 */
public class TextBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "tb";

    // CSS definitions according to Portlet API spec. PLT.C
    public static final String MSG_STATUS = "portlet-msg-status";
    public static final String MSG_INFO = "portlet-msg-info";
    public static final String MSG_ERROR = "portlet-msg-error";
    public static final String MSG_ALERT = "portlet-msg-alert";
    public static final String MSG_SUCCESS = "portlet-msg-success";

    protected String style = "info";

    /**
     * Constructs a default text bean
     */
    public TextBean() {
        super(NAME);
        this.cssStyle = MSG_INFO;
    }

    /**
     * Constructs a text bean using a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public TextBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssStyle = MSG_INFO;
    }

    /**
     * Constructs a text bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public TextBean(PortletRequest req, String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
        this.cssStyle = MSG_INFO;
    }

    /**
     * Returns the style of the text: Available styles are
     * <ul>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @return the text style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style of the text: Available styles are
     * <ul>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @param style the text style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        if (style.equalsIgnoreCase("error") || (style.equalsIgnoreCase("err"))) {
            this.cssStyle = MSG_ERROR;
        } else if (style.equalsIgnoreCase("status")) {
            this.cssStyle = MSG_STATUS;
        } else if (style.equalsIgnoreCase("info")) {
            this.cssStyle = MSG_INFO;
        } else if (style.equalsIgnoreCase("alert")) {
            this.cssStyle = MSG_ALERT;
        } else if (style.equalsIgnoreCase("success")) {
            this.cssStyle = MSG_SUCCESS;
        }
        return "<div class=\"" + cssStyle + "\">" + value + "</div>";
    }
}
