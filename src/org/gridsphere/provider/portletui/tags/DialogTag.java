package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.DialogBean;
import org.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class DialogTag extends BaseComponentTag {

    protected String name = null;
    protected String key = null;
    protected String value = "";
    protected String id = null;
    protected String body = "";
    protected String header = "";
    protected String footer = "";
    protected String width = "";
    protected Boolean isModal = false;
    protected Boolean isClose = true;
    protected Boolean isDraggable = true;
    protected Boolean isResizable = false;
    protected Boolean isLink = false;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getModal() {
        return isModal;
    }

    public void setModal(Boolean modal) {
        isModal = modal;
    }

    public Boolean getClose() {
        return isClose;
    }

    public void setClose(Boolean close) {
        isClose = close;
    }

    public Boolean getResizable() {
        return isResizable;
    }

    public void setResizable(Boolean resizable) {
        isResizable = resizable;
    }

    public Boolean getDraggable() {
        return isDraggable;
    }

    public void setDraggable(Boolean draggable) {
        isDraggable = draggable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void release() {
        super.release();
    }

    public int doStartTag() throws JspException {
        JspWriter out;
        if (key != null) value = getLocalizedText(key);
        try {
            out = pageContext.getOut();
            DialogBean dialog = new DialogBean();
            RenderResponse res = (RenderResponse)pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
            dialog.setRenderResponse(res);
            dialog.setId(id);
            dialog.setWidth(width);
            dialog.setHeader(header);
            dialog.setBody(body);
            dialog.setFooter(footer);
            dialog.setClose(isClose);
            dialog.setModal(isModal);
            dialog.setDraggable(isDraggable);
            dialog.setResizable(isResizable);
            dialog.setName(name);
            dialog.setLink(isLink);
            dialog.setValue(value);
            out.print(dialog.toStartString());
            this.updatePageEndBuffer(dialog.getDialogPopup());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
