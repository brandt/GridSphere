/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ActionLinkBean;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;
import org.gridlab.gridsphere.provider.portletui.beans.RenderLinkBean;
import org.gridlab.gridsphere.portlet.PortletResponse;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The <code>RenderLinkTag</code> provides a hyperlink element that creates a hyperlink to any portlet
 * component identified by a label
 */
public class RenderLinkTag extends BaseComponentTag {

    protected RenderLinkBean renderlink = null;
    protected String label = null;

    /**
     * Sets the label identified with the portlet component to link to
     *
     * @param label the action link key
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the label identified with the portlet component to link to
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Simply creates a render URI using <code>PortletURI</code>
     *
     * @return the URI ready for markup
     */
    protected String createRenderURI() {
        // Builds a URI containing the actin and associated params
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
        // action is a required attribute except for FormTag
        return res.createURI(label).toString();
    }


    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            renderlink = (RenderLinkBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (renderlink == null) {
                renderlink = new RenderLinkBean();
                renderlink.setAction(createRenderURI());
                this.setBaseComponentBean(renderlink);
            }
        } else {
            renderlink = new RenderLinkBean();
            renderlink.setAction(createRenderURI());
            this.setBaseComponentBean(renderlink);
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(renderlink.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }

}
