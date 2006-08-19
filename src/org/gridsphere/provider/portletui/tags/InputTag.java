package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridsphere.provider.portletui.beans.ValidatorBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import java.util.List;
import java.util.ArrayList;

/**
 * A <code>TextFieldTag</code> represents a text field element
 */
public abstract class InputTag extends BaseComponentTag {

    protected List validatorBeans = new ArrayList();

    private transient static PortletLog log = SportletLog.getInstance(InputTag.class);

    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

    public void addValidatorBean(ValidatorBean validatorBean) {
        validatorBeans.add(validatorBean);
    }
    
}