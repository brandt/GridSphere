/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Page.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.classic;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletPage;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import java.awt.*;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class Page extends BaseRender implements Render {

    /**
     * Constructs an instance of PortletPage
     */
    public Page() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent component) {

        PortletRequest req = event.getPortletRequest();

        String theme = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_THEME);
        StringBuffer page = new StringBuffer();

        PortletPage portletPage = (PortletPage)component;
        // page header
        Locale locale = req.getLocale();
        ComponentOrientation orientation = ComponentOrientation.getOrientation(locale);
        if (orientation.isLeftToRight()) {
            page.append("<html>");
        } else {
            page.append("<html dir=\"rtl\">");
        }

        page.append("\n\t<head>");
        page.append("\n\t<title>" + portletPage.getTitle() + "</title>");
        page.append("\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        page.append("\n\t<meta name=\"keywords\" content=\"" + portletPage.getKeywords() + "\"/>");
        page.append("\n\t<meta http-equiv=\"Pragma\" content=\"no-cache\"/>");
        if (portletPage.getRefresh() > 0) page.append("\n\t<meta http-equiv=\"refresh\" content=\"" + portletPage.getRefresh() + "\"/>");
        page.append("\n\t<link type=\"text/css\" href=\"" +
                req.getContextPath() + "/themes/" + portletPage.getRenderKit() + "/" + theme + "/css" +
                    "/default.css\" rel=\"stylesheet\"/>");
        page.append("\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + req.getContextPath() + "/css/SimpleTextEditor.css\"/>");
        // Add portlet defined stylesheet if defined
        Map props = (Map)req.getAttribute(SportletProperties.PORTAL_PROPERTIES);
        if (props != null) {
            Object cssHrefObj = props.get("CSS_HREF");
            if ((cssHrefObj != null) && (cssHrefObj instanceof java.util.List)) {
                java.util.List cssHref = (java.util.List)cssHrefObj;
                Iterator it = cssHref.iterator();
                while (it.hasNext()) {
                    String cssLink = (String)it.next();
                    if (cssLink != null) {
                        page.append("\n\t<link type=\"text/css\" href=\"" + cssLink + "\" rel=\"stylesheet\"/>");
                    }
                }
            }
        }
        page.append("\n\t<link rel=\"icon\" href=\"" + portletPage.getIcon() + "\" type=\"imge/x-icon\"/>");
        page.append("\n\t<link rel=\"shortcut icon\" href=\"" + req.getContextPath() + "/" + portletPage.getIcon() + "\" type=\"image/x-icon\"/>");
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/gridsphere.js\"></script>");
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/SimpleTextEditor.js\"></script>");
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/validation.js\"></script>");        
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/yahoo.js\"></script>");
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/connection.js\"></script>");
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/gridsphere_ajax.js\"></script>");
                
        if (props != null) {
            Object jsObj = props.get("JAVASCRIPT_SRC");
            if ((jsObj != null) && (jsObj instanceof java.util.List)) {
                java.util.List jsSrc = (java.util.List)jsObj;
                Iterator it = jsSrc.iterator();
                while (it.hasNext()) {
                    String jsLink = (String)it.next();
                    if (jsLink != null) {
                        page.append("\n\t<script type=\"text/javascript\" src=\"" + jsLink + "\"></script>");
                    }
                }
            }
        }
        page.append("\n\t</head>\n\t");
        page.append("<body");
        if (props != null) {
        Object bodyOnLoadObj = props.get("BODY_ONLOAD");
            if ((bodyOnLoadObj != null) && (bodyOnLoadObj instanceof java.util.List)) {
                java.util.List onLoad = (java.util.List)bodyOnLoadObj;
                Iterator it = onLoad.iterator();
                page.append(" onload=");
                while (it.hasNext()) {
                    String onLoadFunc = (String)it.next();
                    if (onLoadFunc != null) {
                        page.append(onLoadFunc);
                    }
                }
            }
        }
        page.append(">\n<div id=\"page\">");
        return page;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer("\n</div></body></html>");
    }
}

