package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.portlet.RenderResponse;

public class DialogBean extends BaseComponentBean {

    protected String header = "";
    protected String body = "";
    protected String footer = "";
    protected String width = "";
    protected String titleColor = null;
    protected Boolean isModal = false;
    protected Boolean isClose = true;
    protected Boolean isDraggable = true;
    protected Boolean isResizable = false;
    protected Boolean isLink = false;
    protected RenderResponse renderResponse;
    protected String onClick = "";

    /**
     * Constructs a default table row bean
     */
    public DialogBean() {
        super();
    }

    public RenderResponse getRenderResponse() {
        return renderResponse;
    }

    public void setRenderResponse(RenderResponse renderResponse) {
        this.renderResponse = renderResponse;
    }

    public Boolean getClose() {
        return isClose;
    }

    public void setClose(Boolean close) {
        isClose = close;
    }

    public Boolean getModal() {
        return isModal;
    }

    public void setModal(Boolean modal) {
        isModal = modal;
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


    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public Boolean getLink() {
        return isLink;
    }

    public void setLink(Boolean link) {
        isLink = link;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }


    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        if (key != null) value = getLocalizedText(key);
        value = value.replaceAll("\n", "<br>");
        PortalConfigService configService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        // deal with ROOT context case
        String contextPath = configService.getProperty("gridsphere.deploy");
        if (!contextPath.equals("")) contextPath = "/" + contextPath;
        renderResponse.addProperty("CSS_HREF", contextPath + "/css/yahoo/container.css");
        if (isResizable) renderResponse.addProperty("CSS_HREF", contextPath + "/css/yahoo/ResizePanel.css");

        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/event.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/dom.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/fonts.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/container.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/animation.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/dragdrop.js");
        if (isResizable) renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/ResizePanel.js");
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("YAHOO.namespace(\"").append(name).append("\");\n");
        sb.append("function init() {\n");
        if (!width.endsWith("px")) width = width + "px";
        String resizable = "";
        if (isResizable.booleanValue()) resizable = "Resize";
        sb.append("YAHOO." + name + ".panel  = new YAHOO.widget." + resizable + "Panel(\"" + name + "\", { width:\"" + width + "\", fixedcenter: true, constraintoviewport: true, iframe: true, underlay:\"shadow\", close:" + isClose + ", modal:" + isModal + ", visible:false, draggable:" + isDraggable + "} );\n");
        sb.append("YAHOO." + name + ".panel.render( document.body );\n");
        // sb.append("YAHOO." + name + ".panel.setHeader(\"" + header + "\");\n");
        // sb.append("YAHOO." + name + ".panel.setBody(\"" + body + "\");\n");
        // sb.append("YAHOO." + name + ".panel.setFooter(\"" + footer + "\");\n");
        sb.append("}\n");
        sb.append("YAHOO.util.Event.addListener(window, \"load\", init);");
        sb.append("</script>");
        if (isLink) {
            sb.append("<a href=\"#\" ");
            if (onClick != null) sb.append("onclick=\"" + onClick);
            sb.append("\">" + value + "</a>\n");
        } else {
            sb.append("<button type=\"button\" onclick=\"" + onClick + "\">" + value + "</button>\n");
        }
        sb.append("<div id=\"").append(name).append("\">");
        sb.append("<div ");
        if (titleColor != null) sb.append("style=\"background-color: ").append(titleColor).append(";\" ");
        sb.append("class=\"hd\">").append(header).append("</div>");
        sb.append("<div class=\"bd\">").append(body).append("</div>");
        sb.append("<div class=\"ft\">").append(footer).append("</div></div>");
        return sb.toString();
    }

    public String toEndString() {
        return "";
    }

}
