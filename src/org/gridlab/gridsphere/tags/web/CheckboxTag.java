package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.model.CheckBoxItem;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public class CheckboxTag extends InputTag {

    public int doStartTag() throws JspException {

        try {
            CheckBoxItem item = new CheckBoxItem(name, value, isChecked);
            HTMLElementRenderer.getInstance().renderCheckBoxWithLabel(
                    pageContext.getOut(), item, name);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

}
