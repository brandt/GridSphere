/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.ParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The <code>ParamTag</code> is used to specify action paramters (name value pairs) inside of an
 * <code>ActionLinkTag</code>, <code>RenderLinkTag</code> or an <code>ActionSubmitTag</code>, <code>RenderSubmitTag</code>
 */
public class ParamTag extends TagSupport {

    protected String name = "";
    protected String value = "";
    protected ParamBean paramBean = null;
    protected String beanId = "";

    /**
     * Returns the bean identifier
     *
     * @return the bean identifier
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     * Sets the bean identifier
     *
     * @param beanId the bean identifier
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * Sets the action parameter name
     *
     * @param name the action parameter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the action paramter name
     *
     * @return the action parameter name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the action parameter value
     *
     * @param value the action paramter value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the action parameter value
     *
     * @return the action parameter value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the action parameter bean
     *
     * @param paramBean the action parameter bean
     */
    public void setParamBean(ParamBean paramBean) {
        this.paramBean = paramBean;
    }

    /**
     * Returns the action parameter bean
     *
     * @return the action parameter bean
     */
    public ParamBean getParamBean() {
        return paramBean;
    }

    public int doStartTag() throws JspException {
        Tag tag = getParent();
        if (tag instanceof ActionTag) {
            ActionTag actionTag = (ActionTag) tag;
            paramBean = new ParamBean(name, value);
            actionTag.addParamBean(paramBean);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        return EVAL_PAGE;
    }

}
