/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.gs;

import org.gridlab.gridsphere.provider.portletui.tags.ComponentTag;
import org.gridlab.gridsphere.provider.portletui.tags.PanelTag;
import org.gridlab.gridsphere.provider.portletui.tags.BaseComponentTag;

import org.gridlab.gridsphere.portlet.PortletRequest;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The abstract <code>BaseComponentTag</code> is used by all UI tags to provide CSS support and general
 * name, value attributes
 */
public abstract class BaseComponentTagImpl extends BaseComponentTag implements ComponentTag {

    protected String getLocalizedText(String key) {
        return getLocalizedText(key, "Portlet");
    }

    protected String getLocalizedText(String key, String base) {
        PortletRequest req = (PortletRequest)pageContext.getAttribute("portletRequest");
        Locale locale = req.getLocale();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(base, locale);
            return bundle.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    public int doStartTag() throws JspException {
        Tag parent = getParent();
        if (parent instanceof PanelTag) {
            PanelTag panelTag = (PanelTag)parent;

            int numCols = panelTag.getNumCols();

            int thiscol = panelTag.getColumnCounter();
            try {
                JspWriter out = pageContext.getOut();
                if ((thiscol % numCols) == 0) {
                    out.println("<tr>");
                }
                out.println("<td width=\"" + "100%" + "\">");
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        Tag parent = getParent();
        if (parent instanceof PanelTag) {
            PanelTag panelTag = (PanelTag)parent;

            int numCols = panelTag.getNumCols();
            int thiscol = panelTag.getColumnCounter();
            thiscol++;
            panelTag.setColumnCounter(thiscol);
            try {
                JspWriter out = pageContext.getOut();
                out.println("</td>");
                if ((thiscol % numCols) == 0) {
                    out.println("</tr>");
                }
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return super.doEndTag();
    }
}
