/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBeanContainer;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletSession;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpSession;

public class TextTag extends BaseBeanTag {

    protected String text = new String();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int doStartTag() throws JspException {

        TextBean tb = (TextBean)pageContext.getSession().getAttribute(getBeanKey());
        if (tb != null) {
            System.err.println("found a text bean in the session");
            text = tb.getText();
        } else {
            System.err.println("dmmit-- cant findd a text bean in the attributes");
            tb = new TextBean(text);
        }

        store(getBeanKey(), tb);

        debug();

        TagBeanContainer tc = (TagBeanContainer)pageContext.getAttribute("_container");
        if (tc != null) {
            System.err.println("in text tag-- adding myself to container");
            tc.addTagBean(tb);
            pageContext.setAttribute("_container", tc);
        } else {

            try {
                JspWriter out = pageContext.getOut();
                out.print(text);
            } catch (Exception e) {
                throw new JspTagException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
