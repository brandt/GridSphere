/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.ParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class ParamTag extends TagSupport {

    protected String name = "";
    protected String value = "";
    protected ParamBean paramBean = null;

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

    public void setParamBean(ParamBean paramBean) {
        this.paramBean = paramBean;
    }

    public ParamBean getParamBean() {
        return paramBean;
    }

    public int doStartTag() throws JspException {
        ActionTag actionTag = (ActionTag)getParent();
        if (actionTag != null) {
            paramBean = new ParamBean(name, value);
            //actionTag.addParamBean(paramBean);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        return EVAL_PAGE;
    }

}
