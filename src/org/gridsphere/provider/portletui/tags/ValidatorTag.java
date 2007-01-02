package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.ValidatorBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * A <code>TextTag</code> represents text to be displayed
 */
public class ValidatorTag extends BaseComponentTag {

    protected ValidatorBean validatorBean = null;
    protected String type = "";

    /**
     * Sets the validator type
     *
     * @param type the validator type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the validator type
     *
     * @return the validator type
     */
    public String getType() {
        return type;
    }

    public int doStartTag() throws JspException {
        Tag parent = this.getParent();
        if (parent instanceof TextFieldTag) {
            TextFieldTag textField = (TextFieldTag) parent;
            if (key != null) value = this.getLocalizedText(key);
            String tfClass = type;
            textField.addCssClass(tfClass + "#");
            ValidatorBean validatorBean = new ValidatorBean();
            validatorBean.setValue(value);
            validatorBean.setType(type);
            textField.addValidatorBean(validatorBean);
        }
        super.release();
        return SKIP_BODY;
    }

}
