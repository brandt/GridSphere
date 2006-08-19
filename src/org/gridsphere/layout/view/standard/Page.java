/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Page.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.standard;

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
        // causes IE to go into quirks mode
        //page.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        page.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " );
        page.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        if (orientation.isLeftToRight()) {
            page.append("<html");
        } else {
            page.append("<html dir=\"rtl\"");
        }
        page.append(" xmlns=\"http://www.w3.org/1999/xhtml\">");

        page.append("\n\t<head>");
        page.append("\n\t<title>");
        page.append(portletPage.getTitle());
        page.append("</title>");
        ///page.append("\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        page.append("\n\t<meta name='keywords' content='");
        page.append(portletPage.getKeywords());
        page.append("' />");
        page.append("\n\t<meta http-equiv=\"Pragma\" content=\"no-cache\" />");
        if (portletPage.getRefresh() > 0) page.append("\n\t<meta http-equiv=\"refresh\" content=\"" + portletPage.getRefresh() + "\"/>");
        page.append("\n\t<link type=\"text/css\" href=\"");
        page.append(req.getContextPath());
        page.append("/themes/");
        page.append(portletPage.getRenderKit());
        page.append("/");
        page.append(theme);
        page.append("/css/default.css\" rel=\"stylesheet\"/>");
        page.append("\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
        page.append(req.getContextPath());
        page.append("/css/SimpleTextEditor.css\"/>");
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
                        page.append("\n\t<link type=\"text/css\" href=\"");
                        page.append(cssLink);
                        page.append("\" rel=\"stylesheet\"/>");
                    }
                }
            }
        }
        page.append("\n\t<link rel=\"icon\" href=\"");
        page.append(portletPage.getIcon());
        page.append("\" type=\"imge/x-icon\"/>");
        page.append("\n\t<link rel=\"shortcut icon\" href=\"");
        page.append(req.getContextPath());
        page.append("/");
        page.append(portletPage.getIcon());
        page.append("\" type=\"image/x-icon\"/>");

        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/gridsphere.js\"></script>");
        // add editor
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/SimpleTextEditor.js\"></script>");
        // add calendar
        page.append("\n\t<script type=\"text/javascript\" src=\"" + req.getContextPath() + "/javascript/scw.js\"></script>");
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
                        page.append("\n\t<script type=\"text/javascript\" src=\"");
                        page.append(jsLink);
                        page.append("\"></script>");
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
        return new StringBuffer("</div>\n</body></html>");
    }
}

