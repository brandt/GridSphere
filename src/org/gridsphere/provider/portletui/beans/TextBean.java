/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TextBean.java 4884 2006-06-26 23:52:48Z novotny $
 */

package org.gridsphere.provider.portletui.beans;

import java.awt.*;

/**
 * The <code>TextBean</code> represents text to be displayed
 */
public class TextBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "tb";

    protected String style = "info";

    /* @deprecated -- use MessageStyle instead */
    // CSS definitions according to Portlet API spec. PLT.C
    public static final String MSG_STATUS = "portlet-msg-status";
    public static final String MSG_INFO = "portlet-msg-info";
    public static final String MSG_ERROR = "portlet-msg-error";
    public static final String MSG_ALERT = "portlet-msg-alert";
    public static final String MSG_SUCCESS = "portlet-msg-success";
    public static final String MSG_ITALIC = "italic";
    public static final String MSG_BOLD = "bold";
    public static final String MSG_UNDERLINE = "underline";

    /**
     * Constructs a default text bean
     */
    public TextBean() {
        super(NAME);
        this.cssClass = MessageStyle.MSG_INFO;
    }

    /**
     * Constructs a text bean using a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public TextBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssClass = MessageStyle.MSG_INFO;
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
        String dir = "ltr";
        if (locale != null) {
            ComponentOrientation orientation = ComponentOrientation.getOrientation(locale);
            if (!orientation.isLeftToRight()) {
                dir = "rtl";
            }
        }
        if (value == null) return "";
        if (style.equalsIgnoreCase("error") || (style.equalsIgnoreCase("err"))) {
            this.cssClass = MessageStyle.MSG_ERROR;
        } else if (style.equalsIgnoreCase("status")) {
            this.cssClass = MessageStyle.MSG_STATUS;
        } else if (style.equalsIgnoreCase("info")) {
            this.cssClass = MessageStyle.MSG_INFO;
        } else if (style.equalsIgnoreCase("alert")) {
            this.cssClass = MessageStyle.MSG_ALERT;
        } else if (style.equalsIgnoreCase("success")) {
            this.cssClass = MessageStyle.MSG_SUCCESS;
        } else if (style.equalsIgnoreCase("nostyle")) {
            return value;
        } else if (style.equalsIgnoreCase(MessageStyle.MSG_BOLD)) {
            return "<b dir=\"" + dir + "\" >" + value + "</b>";
        } else if (style.equalsIgnoreCase(MessageStyle.MSG_ITALIC)) {
            return "<i dir=\"" + dir + "\" >" + value + "</i>";
        } else if (style.equalsIgnoreCase(MessageStyle.MSG_UNDERLINE)) {
            return "<u dir=\"" + dir + "\" >" + value + "</u>";
        }

        text = "<span dir=\"" + dir + "\" " + getFormattedCss();

        text += ">" + value + "</span>";
        
        return text;
    }
}
