/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.tags.web.element.Text;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

public class TextTag extends TagSupport {

    private String text = new String();
    private Text textelement = new Text();


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int doStartTag() throws JspTagException {
        textelement.setLabel(text);
        try {
            JspWriter out = pageContext.getOut();
            out.println(textelement);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

}
