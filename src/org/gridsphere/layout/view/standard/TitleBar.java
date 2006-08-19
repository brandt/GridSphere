/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TitleBar.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.standard;

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
            titleBuffer.append("<div class=\"window-title-active\">");
        } else {
            titleBuffer.append("<div class=\"window-title-inactive\">");
        }
        titleBar.setActive(false);
        List modeLinks = titleBar.getModeLinks();
        titleBuffer.append("<div class=\"window-icon-left\">");        
        if (modeLinks != null) {
            Iterator modesIt = modeLinks.iterator();
            PortletTitleBar.PortletModeLink mode;
            if (!modesIt.hasNext()) {
                titleBuffer.append("&nbsp;");
            }
            StringBuffer tmp = new StringBuffer();
            while (modesIt.hasNext()) {
                mode = (PortletTitleBar.PortletModeLink) modesIt.next();
                tmp.append("<a href=\"");
                tmp.append(mode.getHref());
                tmp.append("\"><img src=\"");
                tmp.append(req.getContextPath());
                tmp.append("/themes/");
                tmp.append(renderkit);
                tmp.append("/");
                tmp.append(theme);
                tmp.append("/");
                tmp.append(mode.getImageSrc());
                tmp.append("\" title=\"");
                tmp.append(mode.getAltTag());
                tmp.append("\" alt=\"");
                tmp.append(mode.getAltTag());
                tmp.append("\"");
                if (!mode.getCursor().equals("")) {
                    tmp.append(" style=\"cursor: ");
                    tmp.append(mode.getCursor());
                    tmp.append(";\"");
                }
                tmp.append("\" /></a>");
            }
            titleBuffer.append(tmp);
        }
        titleBuffer.append("</div>");
        titleBuffer.append("<div class=\"window-title-name\">");
        return titleBuffer;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        PortletTitleBar titleBar = (PortletTitleBar)comp;
        PortletRequest req = event.getPortletRequest();
        String theme = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_THEME);
        String renderkit = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_RENDERKIT);
        StringBuffer titleBuffer = new StringBuffer();
        titleBuffer.append("</div>");
        // Output window state icons
        List windowLinks = titleBar.getWindowLinks();
        titleBuffer.append("<div class=\"window-icon-right\">");        
        String tmp = "";
        if (windowLinks != null) {
            Iterator windowsIt = windowLinks.iterator();
            PortletTitleBar.PortletStateLink state;
            while (windowsIt.hasNext()) {
                state = (PortletTitleBar.PortletStateLink) windowsIt.next();
                tmp += "<a href=\"" + state.getHref() + "\"><img src=\"" + req.getContextPath() +"/themes/" + renderkit + "/" + theme + "/" + state.getImageSrc() + "\" title=\"" + state.getAltTag() + "\" alt=\"" + state.getAltTag() + "\" /></a>";
            }
        }
        titleBuffer.append(tmp);
        titleBuffer.append("</div>");        
        titleBuffer.append("</div>");
        tmp = null;
        return titleBuffer;
    }


}

