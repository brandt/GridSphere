package org.gridsphere.provider.portletui.beans;

public class PortletBean extends DialogBean {

    protected String theme = "default";
    protected String renderKit = "brush";
    protected String portletId = "";

    public PortletBean() {}

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getRenderKit() {
        return renderKit;
    }

    public void setRenderKit(String renderKit) {
        this.renderKit = renderKit;
    }

    public String getPortletId() {
        return portletId;
    }

    public void setPortletId(String portletId) {
        this.portletId = portletId;
    }

    public String toStartString() {
        onClick = "GridSphereAjaxHandler2.startRequest('" + portletId + "', '" + name + "'); YAHOO." + name + ".panel.show();";
        return super.toStartString();
    }

    public String toEndString() {
        return super.toEndString();
    }


}
