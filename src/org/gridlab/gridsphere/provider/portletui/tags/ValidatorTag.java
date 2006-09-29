package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.MessageStyle;
import org.gridlab.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridlab.gridsphere.provider.portletui.beans.ValidatorBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.util.Locale;

/**
 * A <code>TextTag</code> represents text to be displayed
 */
public class ValidatorTag extends BaseComponentTag {

    protected ValidatorBean validatorBean = null;
    protected String key = null;
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

    /**
     * Returns the key used to identify localized text
     *
     * @return the key used to identify localized text
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key used to identify localized text
     *
     * @param key the key used to identify localized text
     */
    public void setKey(String key) {
        this.key = key;
    }

    public int doStartTag() throws JspException {
        Tag parent = this.getParent();
        if (parent instanceof TextFieldTag) {
            TextFieldTag textField = (TextFieldTag)parent;
            if (key != null) value = this.getLocalizedText(key);
            String tfClass = type;
            textField.addCssClass(tfClass + "#");
            ValidatorBean validatorBean = new ValidatorBean();
            validatorBean.setValue(value);
            validatorBean.setType(type);
            textField.addValidatorBean(validatorBean);
        }
        return SKIP_BODY;
    }

}
