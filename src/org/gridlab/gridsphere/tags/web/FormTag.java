package org.gridlab.gridsphere.tags.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 10, 2003
 * Time: 11:11:04 AM
 * To change this template use Options | File Templates.
 */
public class FormTag extends TagSupport {

    private String action;
    private String method;

    public void setAction(String action) {
        this.action =action;
    }

    public String getAction() {
        return action;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

}
