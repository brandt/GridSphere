/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.tags.ui.BaseTag;
import org.gridlab.gridsphere.provider.ui.beans.TextBean;

import javax.servlet.jsp.JspException;

public class TextTag extends BaseTag {

    protected String text = new String();
    private TextBean textelement = new TextBean();


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            System.err.println("Bean is empty");
            this.htmlelement = new TextBean(text);
        }
        System.err.println("Bean is empty");
        return super.doStartTag();
    }

}
