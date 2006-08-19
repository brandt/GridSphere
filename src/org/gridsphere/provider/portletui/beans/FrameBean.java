/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: FrameBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.beans;

import org.gridsphere.provider.portletui.model.DefaultTableModel;

/**
 * The <code>FrameBean</code> extends <code>TableBean</code> to provide a stylized table that can also
 * be used to render text messages.
 */
public class FrameBean extends TableBean implements TagBean {

    public static final String FRAME_TABLE = "portlet-frame";
    public static final String FRAME_INFO_MESSAGE = "portlet-frame-info";
    public static final String FRAME_ALERT_MESSAGE = "portlet-frame-alert";
    public static final String FRAME_ERROR_MESSAGE = "portlet-frame-error";

    public static final String ERROR_TYPE = "error";
    public static final String MESSAGE_TYPE = "message";

    protected String textStyle = MessageStyle.MSG_INFO;

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
        if ((key != null) || (value != null)) createMessage();
        return super.toStartString();
    }

    public String toEndString() {
        return super.toEndString();
    }
}
