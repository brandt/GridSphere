package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.ValidatorBean;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.List;

/**
 * A <code>TextFieldTag</code> represents a text field element
 */
public abstract class InputTag extends BaseComponentTag {

    protected List validatorBeans = new ArrayList();

    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

    public void addValidatorBean(ValidatorBean validatorBean) {
        validatorBeans.add(validatorBean);
    }

}