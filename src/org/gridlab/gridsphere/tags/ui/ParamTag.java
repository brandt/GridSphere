/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.provider.ui.beans.ActionLinkBean;
import org.gridlab.gridsphere.provider.ui.beans.ParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class ParamTag extends BaseTag {

    private String name = "noname";
    private String value = "novalue";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int doStartTag() throws JspException {
        DefaultPortletAction action = (DefaultPortletAction) pageContext.getAttribute("_action");
        if (bean.equals("")) {
            this.htmlelement = new ParamBean(name, value);
        }
        if (action != null) action.addParameter(name, value);
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        return EVAL_PAGE;
    }

}
