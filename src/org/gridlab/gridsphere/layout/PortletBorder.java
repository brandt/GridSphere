/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.IOException;

public class PortletBorder implements PortletRender {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletBorder.class);

    private UserPortletManager userManager = UserPortletManager.getInstance();
    private String title = "";
    private String titleColor = "#FFFFFF";
    private String font = "Arial, Helvetica, sans-serif";
    private String lineColor = "#336699";
    private String thickness = "1";
    private Boolean configMode = Boolean.TRUE;
    private Boolean windowMode = Boolean.TRUE;

    public PortletBorder() {}

    public PortletBorder(String title, String titleColor, String font, String lineColor, String thickness) {
        if (title != null) this.title = title;
        if (titleColor != null) this.titleColor = titleColor;
        if (font != null) this.font = font;
        if (lineColor != null) this.lineColor = lineColor;
        if (thickness != null) this.thickness= thickness;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public String getTitleFont() {
        return font;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleColor(String color) {
        this.titleColor = titleColor;
    }

    public void setTitleFont(String font) {
        this.font = font;
    }

    public void setLineColor(String color) {
        this.lineColor = lineColor;
    }

    public String getLineColor() {
        return lineColor;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void setConfigMode(boolean configMode) {
        this.configMode = new Boolean(configMode);
    }

    public void setWindowMode(boolean windowMode) {
        this.windowMode = new Boolean(windowMode);
    }

    public boolean getConfigMode() {
        return configMode.booleanValue();
    }

    public boolean getWindowMode() {
        return windowMode.booleanValue();
    }

    public void doRender(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRender()");
        try {
            req.setAttribute(LayoutProperties.TITLE, title);
            req.setAttribute(LayoutProperties.THICKNESS, thickness);
            req.setAttribute(LayoutProperties.LINECOLOR, lineColor);
            req.setAttribute(LayoutProperties.FONT, font);
            req.setAttribute(LayoutProperties.TITLECOLOR, titleColor);
            req.setAttribute(LayoutProperties.PORTLETMODE, configMode.toString());
            req.setAttribute(LayoutProperties.WINDOWSTATE, windowMode.toString());
            RequestDispatcher rd = ctx.getRequestDispatcher("/WEB-INF/conf/layout/portlet-border.jsp");
            rd.include(req, res);

            //ctx.include("/WEB-INF/conf/layout/portlet-border.jsp", req, res);

        } catch (ServletException e) {
            log.error("Unable to include component JSP", e);
            throw new PortletLayoutException("Unable to include component JSP", e);
        }
    }

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRenderFirst()");
        PrintWriter out = res.getWriter();
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" bgcolor=\"" +  lineColor + "\"");
        out.println("<tr><td><img src=\"images/help.gif\" align=left></td><td height=\"20\" align=left valign=middle>");
    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRenderLast()");
        PrintWriter out = res.getWriter();
        out.println("<font color=\"#FFFFFF\" face=\"" + font + "\">&nbsp;" + title);
        out.println("</font></td><td><img src=\"images/window.gif\" align=right></td></tr></table>");
    }


}
