/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.TextBean;

import javax.servlet.jsp.JspException;

public class TextTag extends BaseTag {

    private String text = new String();
    private TextBean textelement = new TextBean();


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new TextBean(text);
        }
        return super.doStartTag();
    }

}
