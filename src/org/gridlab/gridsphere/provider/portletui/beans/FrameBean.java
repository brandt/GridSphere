/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

import javax.servlet.http.HttpServletRequest;

/**
 *  The <code>FrameBean</code> extends <code>TableBean</code> to provide a stylized table that can also
 * be used to render text messages.
 */
public class FrameBean extends TableBean implements TagBean {

    public static final String FRAME_TABLE = "portlet-frame";
    public static final String FRAME_INFO_MESSAGE = "portlet-frame-info";
    public static final String FRAME_ALERT_MESSAGE = "portlet-frame-alert";
    public static final String FRAME_ERROR_MESSAGE = "portlet-frame-error";

    public static final String ERROR_TYPE = "error";
    public static final String MESSAGE_TYPE = "message";

    protected String textStyle = TextBean.MSG_INFO;

    /**
     * Constructs a default frame bean
     */
    public FrameBean() {
        super(FRAME_TABLE);
    }

    /**
     * Constructs a frame bean from a bean identifier
     *
     * @param beanId the frame bean identifier
     */
    public FrameBean(String beanId) {
        super(FRAME_TABLE);
        this.beanId = beanId;
    }

    /**
     * Constructs a frame bean from a portlet request and bena identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public FrameBean(HttpServletRequest req, String beanId) {
        super(FRAME_TABLE);
        this.beanId = beanId;
        this.request = req;
    }

    /**
     * Sets the text style
     *
     * @param style the text style
     */
    public void setStyle(String style) {
        this.textStyle = style;
    }

    /**
     * Returns the text style
     *
     * @return the text style
     */
    public String getStyle() {
        return textStyle;
    }

    /**
     * Creates a frame to display a text message
     */
    protected void createMessage() {
        defaultModel = new DefaultTableModel();
        TableRowBean tr = new TableRowBean();
        TableCellBean tc = new TableCellBean();
        TextBean text = new TextBean();
        text.setCssClass(textStyle);
        if (key != null) {
            text.setKey(key);
        }
        if (value != null) {
            text.setValue(value);
        }
        tc.addBean(text);
        tc.setCssClass(textStyle);
        tr.addBean(tc);
        defaultModel.addTableRowBean(tr);
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        if ((key != null) || (value != null)) createMessage();
        sb.append("<table " + getFormattedCss() + " ");
        if (cellSpacing != null) sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        if (cellPadding != null) sb.append(" cellpadding=\"" + cellPadding + "\" ");
        if (border != null) sb.append(" border=\"" + border + "\" ");
        if (width != null) sb.append(" width=\"" + width + "\" ");
        sb.append(">");
        if (defaultModel != null) sb.append(defaultModel.toStartString());
        return sb.toString();
    }

    public String toEndString() {
        return ("</table>");
    }
}
