/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import javax.servlet.http.HttpServletRequest;

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

    public static final String MSG_ITALIC = "italic";
    public static final String MSG_BOLD = "bold";
    public static final String MSG_UNDERLINE = "underline";

    protected String style = "info";

    /**
     * Constructs a default text bean
     */
    public TextBean() {
        super(NAME);
        this.cssClass = MSG_INFO;
    }

    /**
     * Constructs a text bean using a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public TextBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssClass = MSG_INFO;
    }

    /**
     * Constructs a text bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public TextBean(HttpServletRequest req, String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
        this.cssClass = MSG_INFO;
    }

    /**
     * Returns the style of the text: Available styles are
     * <ul>
     * <li>nostyle - plain text</li>
     * <li>error - error text</li>
     * <li>info - default info text</li>
     * <li>status - status text</li>
     * <li>alert - alert text</li>
     * <li>success - success text</li>
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
        String text = "";
        if (value == null) return "";
        if (style.equalsIgnoreCase("error") || (style.equalsIgnoreCase("err"))) {
            this.cssClass = MSG_ERROR;
        } else if (style.equalsIgnoreCase("status")) {
            this.cssClass = MSG_STATUS;
        } else if (style.equalsIgnoreCase("info")) {
            this.cssClass = MSG_INFO;
        } else if (style.equalsIgnoreCase("alert")) {
            this.cssClass = MSG_ALERT;
        } else if (style.equalsIgnoreCase("success")) {
            this.cssClass = MSG_SUCCESS;
        } else if (style.equalsIgnoreCase("nostyle")) {
            return value;
        } else if (style.equalsIgnoreCase(TextBean.MSG_BOLD)) {
            return "<b>" + value + "</b>";
        } else if (style.equalsIgnoreCase(TextBean.MSG_ITALIC)) {
            return "<i>" + value + "</i>";
        } else if (style.equalsIgnoreCase(TextBean.MSG_UNDERLINE)) {
            return "<u>" + value + "</u>";
        }
        text = "<span " + getFormattedCss();
        text += ">" + value + "</span>";
        return text;
    }
}
