/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

public class FrameBean extends TableBean implements TagBean {

    public static final String TABLE_FRAME_STYLE = "portlet-frame";
    public static final String TABLE_FRAME_WIDTH = "100%";
    public static final String TABLE_FRAME_SPACING = "1";

    public static final String ERROR_TYPE = "error";
    public static final String MESSAGE_TYPE = "message";

    protected String style = TextBean.TEXT_LABEL_STYLE;

    public FrameBean() {
        super(TABLE_FRAME_STYLE);
        this.width = TABLE_FRAME_WIDTH;
        this.cellSpacing = TABLE_FRAME_SPACING;
    }

    public FrameBean(String beanId) {
        super(TABLE_FRAME_STYLE);
        this.beanId = beanId;
        this.width = TABLE_FRAME_WIDTH;
        this.cellSpacing = TABLE_FRAME_SPACING;
    }

    public FrameBean(PortletRequest req, String beanId) {
        super(TABLE_FRAME_STYLE);
        this.width = TABLE_FRAME_WIDTH;
        this.cellSpacing = TABLE_FRAME_SPACING;
        this.beanId = beanId;
        this.request = req;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    protected void makeMessage() {
        defaultModel = new DefaultTableModel();
        TableRowBean tr = new TableRowBean();
        TableCellBean tc = new TableCellBean();
        TextBean text = new TextBean();
        text.setStyle(style);
        if (key != null) {
            text.setKey(key);
        }
        if (value != null) {
            text.setValue(value);
        }
        tc.addBean(text);
        tc.setCssStyle(style);
        tr.addBean(tc);
        defaultModel.addTableRowBean(tr);
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();

        if ((key != null) || (value != null)) makeMessage();
        sb.append("<table class=\"" + TABLE_FRAME_STYLE + "\" ");
        sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        sb.append(" width=\"" + width + "\" >");
        if (defaultModel != null) sb.append(defaultModel.toStartString());
        return sb.toString();
    }

    public String toEndString() {
        return ("</table>");
    }
}
