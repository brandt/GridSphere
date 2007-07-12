package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.DialogBean;

import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * The <code>DialogTag</code> is a wrapper for the
 * <a href="http://developer.yahoo.com/yui/container/panel/">Yahoo UI Panel</a> javascript widget
 */
public class DialogTag extends BaseComponentTag {

    protected String body = "";
    protected String titleColor = null;
    protected String header = "";
    protected String footer = "";
    protected String width = "";
    protected String height = "";
    protected Boolean isModal = false;
    protected Boolean isClose = true;
    protected Boolean isDraggable = true;
    protected Boolean isResizable = false;
    protected String onClick = null;
    protected Boolean isLink = false;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
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

    public void release() {
        super.release();
        body = "";
        titleColor = null;
        header = "";
        footer = "";
        width = "";
        height = "";
        isModal = false;
        isClose = true;
        isDraggable = true;
        isResizable = false;
        onClick = null;
        isLink = false;
    }

    protected void setProperties(DialogBean dialog) {
        if (key != null) value = getLocalizedText(key);
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        dialog.setRenderResponse(res);
        dialog.setId(id);
        dialog.setWidth(width);
        dialog.setHeight(height);
        dialog.setHeader(header);
        dialog.setBody(body);
        dialog.setOnClick(onClick);
        dialog.setFooter(footer);
        dialog.setClose(isClose);
        dialog.setModal(isModal);
        dialog.setDraggable(isDraggable);
        dialog.setResizable(isResizable);
        dialog.setTitleColor(titleColor);
        dialog.setLink(isLink);
        dialog.setValue(value);
        dialog.setName(name);
    }

    public int doStartTag() throws JspException {

        DialogBean dialog = new DialogBean();
        onClick = "YAHOO." + id + ".panel.show();";
        setProperties(dialog);

        JspWriter out;
        try {
            out = pageContext.getOut();
            out.print(dialog.toStartString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        release();
        return EVAL_PAGE;
    }
}
