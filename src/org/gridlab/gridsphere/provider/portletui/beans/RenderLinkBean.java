/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * An <code>RenderLinkBean</code> is a visual bean that represents a render link
 */
public class RenderLinkBean extends BaseComponentBean implements TagBean {

    private String renderURI = "";
    /**
     * Constructs a default render link bean
     */
    public RenderLinkBean() {
    }

    /**
     * Constructs a render link bean from a portlet request and supplied bean identifier
     */
    public RenderLinkBean(PortletRequest req, String beanId) {
        this.request = req;
        this.beanId = beanId;
    }

    /**
     * Sets the render URI ready for markup
     *
     * @param renderURI the URI
     */
    public void setRenderURI(String renderURI) {
        this.renderURI = renderURI;
    }

    /**
     * Returns the render URI ready for markup
     *
     * @return the render URI
     */
    public String getRenderURI() {
        return renderURI;
    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        return "<a href=\"" + renderURI + "\"" + " onClick=\"this.href='" + renderURI + "&JavaScript=enabled'\"/>" + value + "</a>";
    }

}
