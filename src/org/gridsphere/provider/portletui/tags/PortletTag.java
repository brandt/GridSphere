/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletTag.java 4992 2006-08-04 10:03:27Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.layout.PortletFrame;
import org.gridsphere.layout.PortletFrameRegistry;
import org.gridsphere.portlet.PortletContext;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.portletcontainer.impl.GridSphereEventImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * The abstract <code>BaseBeanTag</code> is a base class used by all UI tags that provides support for
 * a bean identifier and a flag for indicating whether JavaScript is enabled.
 */
public class PortletTag extends BodyTagSupport {

    protected String label = "";
    protected String portletId = "";
    protected String width = "";
    protected boolean transparent = false;
    protected String innerPadding = "";
    protected String outerPadding = "";
    protected String theme = "default";
    protected String renderKit = "standard";
    protected String role = "";

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public String getInnerPadding() {
        return innerPadding;
    }

    public void setInnerPadding(String innerPadding) {
        this.innerPadding = innerPadding;
    }

    public String getOuterPadding() {
        return outerPadding;
    }

    public void setOuterPadding(String outerPadding) {
        this.outerPadding = outerPadding;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRenderKit() {
        return renderKit;
    }

    public void setRenderKit(String renderKit) {
        this.renderKit = renderKit;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Sets the portlet component width
     *
     * @param width the portlet component width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the portlet component width
     *
     * @return the portlet component width
     */
    public String getWidth() {
        return width;
    }


    public String getPortletId() {
        return portletId;
    }

    public void setPortletId(String portletId) {
        this.portletId = portletId;
    }

    public int doStartTag() throws JspException {
        PortletFrameRegistry registry = PortletFrameRegistry.getInstance();
        PortletContext context = (PortletContext)pageContext.getAttribute("portletContext");
        PortletRequest request = (PortletRequest)pageContext.getAttribute("portletRequest");
        PortletResponse response = (PortletResponse)pageContext.getAttribute("portletResponse");
        GridSphereEvent event = new GridSphereEventImpl(context, request, response);
      
        // role check
        if ((!role.equals("")) && !request.isUserInRole(role)) return SKIP_BODY;

        PortletFrame frame = registry.getPortletFrame(label, portletId, event);
        frame.setInnerPadding(innerPadding);
        frame.setOuterPadding(outerPadding);
        frame.setTransparent(transparent);
        request.getPortletSession().setAttribute(SportletProperties.LAYOUT_THEME, theme);
        request.getPortletSession().setAttribute(SportletProperties.LAYOUT_RENDERKIT, renderKit);
        frame.setRequiredRole(role);
        JspWriter out;
        try {
            //if (event.hasAction())
                frame.actionPerformed(event);
            out = pageContext.getOut();
            frame.doRender(event);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException(e);
        }
        try {
            out.print(frame.getBufferedOutput(request));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

}
