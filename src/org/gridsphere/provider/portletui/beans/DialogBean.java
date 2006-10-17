package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.portlet.RenderResponse;

public class DialogBean extends BaseComponentBean {

    public String name = null;
    public String key = null;
    public String value = "";
    public String id = null;
    public String header = "";
    public String body = "";
    public String footer = "";
    public String width = "";
    public Boolean isModal = false;
    public Boolean isClose = true;
    public Boolean isDraggable = true;
    public Boolean isResizable = false;
    public Boolean isLink = false;
    public RenderResponse renderResponse;

    /**
     * Constructs a default table row bean
     */
    public DialogBean() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getLink() {
        return isLink;
    }

    public void setLink(Boolean link) {
        isLink = link;
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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        if (key != null) value = getLocalizedText(key);
        value = value.replaceAll("\n", "<br>");
        PortalConfigService configService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        // deal with ROOT context case
        String contextPath = configService.getProperty("gridsphere.deploy");
        if (!contextPath.equals("")) contextPath = "/" + contextPath;
        renderResponse.setProperty("CSS_HREF", contextPath + "/css/yahoo/container.css");
        if (isResizable) renderResponse.setProperty("CSS_HREF", contextPath + "/css/yahoo/ResizePanel.css");
        if (isResizable) renderResponse.setProperty("CSS_HREF", contextPath + "/css/yahoo/example.css");
        
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/event.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/dom.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/fonts.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/container.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/animation.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/dragdrop.js");
        if (isResizable) renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/ResizePanel.js");
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("YAHOO.namespace(\"" + name + "\");\n");
        sb.append("function init() {\n");
        if (!width.endsWith("px")) width = width + "px";
        String resizable = "";
        if (isResizable.booleanValue()) resizable = "Resize";
        sb.append("YAHOO." + name + ".panel  = new YAHOO.widget." + resizable + "Panel(\"" + name + "\", { width:\"" + width + "\", fixedcenter: true, constraintoviewport: true, underlay:\"shadow\", close:" + isClose + ", modal:" + isModal + ", visible:false, draggable:" + isDraggable + "} );\n");
        sb.append("YAHOO." + name + ".panel.render();\n");
        sb.append("YAHOO." + name + ".panel.setHeader(\"" + header + "\");\n");
        sb.append("YAHOO." + name + ".panel.setBody(\"" + body + "\");\n");
        sb.append("YAHOO." + name + ".panel.setFooter(\"" + footer + "\");\n");
        sb.append("}\n");
        sb.append("YAHOO.util.Event.addListener(window, \"load\", init);");
        sb.append("</script>");
        sb.append("<div id=\"" + name + "\"><div class=\"hd\"></div><div class=\"bd\"></div><div class=\"ft\"></div></div>");
        if (isLink) {
            sb.append("<a href=\"#\" onclick=\"YAHOO." + name + ".panel.show();\">" + value + "</a>\n");
        } else {
            sb.append("<button type=\"button\" onclick=\"YAHOO." + name + ".panel.show();\">" + value + "</button>\n");
        }
        return sb.toString();
    }

    public String toEndString() {
        return "";
    }

}
