/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TitleBar.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.classic;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletTitleBar;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import java.util.Iterator;
import java.util.List;

public class TitleBar extends BaseRender implements Render {

    /**
     * Constructs an instance of PortletTitleBar
     */
    public TitleBar() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        PortletTitleBar titleBar = (PortletTitleBar)comp;
        PortletRequest req = event.getPortletRequest();
        String theme = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_THEME);
        String renderkit = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_RENDERKIT);
        StringBuffer titleBuffer = new StringBuffer();
        if (titleBar.isActive()) {
            titleBuffer.append("<tr><td class=\"window-title-active\">");
        } else {
            titleBuffer.append("<tr><td class=\"window-title-inactive\">");
        }
        titleBar.setActive(false);

        titleBuffer.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr>");
        // Output portlet mode icons
        List modeLinks = titleBar.getModeLinks();
        if (modeLinks != null) {
            Iterator modesIt = modeLinks.iterator();
            titleBuffer.append("<td class=\"window-icon-left\">");
            PortletTitleBar.PortletModeLink mode;
            while (modesIt.hasNext()) {
                mode = (PortletTitleBar.PortletModeLink) modesIt.next();
                titleBuffer.append("<a href=\"" + mode.getHref() + "\"><img border=\"0\" src=\"" + req.getContextPath() + "/themes/" + renderkit + "/" + theme + "/" + mode.getImageSrc() + "\" title=\"" + mode.getAltTag() + "\" alt=\"" + mode.getAltTag() + "\" /></a>"); /// Removed File.separator(s)
            }
            titleBuffer.append("</td>");
        }
        titleBuffer.append("<td class=\"window-title-name\">");
        return titleBuffer;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        PortletTitleBar titleBar = (PortletTitleBar)comp;
        PortletRequest req = event.getPortletRequest();
        String theme = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_THEME);
        String renderkit = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_RENDERKIT);
        StringBuffer titleBuffer = new StringBuffer();
        titleBuffer.append("</td>");
        // Output window state icons
        List windowLinks = titleBar.getWindowLinks();
        if (windowLinks != null) {
            Iterator windowsIt = windowLinks.iterator();
            PortletTitleBar.PortletStateLink state;

            titleBuffer.append("<td class=\"window-icon-right\">");
            while (windowsIt.hasNext()) {
                state = (PortletTitleBar.PortletStateLink) windowsIt.next();
                titleBuffer.append("<a href=\"" + state.getHref() + "\"><img border=\"0\" src=\"" + req.getContextPath() + "/themes/" + renderkit + "/" + theme + "/" + state.getImageSrc() + "\" title=\"" + state.getAltTag() + "\" alt=\"" + state.getAltTag() + "\" /></a>"); /// Removed File.separator(s)
            }
            titleBuffer.append("</td>");
        }
        titleBuffer.append("</tr></table>");
        titleBuffer.append("</td></tr>");
        return titleBuffer;
    }


}

