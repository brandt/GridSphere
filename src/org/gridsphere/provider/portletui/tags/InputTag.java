package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.ValidatorBean;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.List;

/**
 * A <code>TextFieldTag</code> represents a text field element
 */
public abstract class InputTag extends BaseComponentTag {

    protected List<ValidatorBean> validatorBeans = new ArrayList<ValidatorBean>();

    protected String onFocus = null;
    protected String onClick = null;
    protected String onChange = null;
    protected String onBlur = null;
    protected String onSelect = null;

    /**
     * Sets the onFocus JavaScript function
     *
     * @param onFocus the onFocus JavaScript function
     */
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }

    /**
     * Returns the onFocus JavaScript function
     *
     * @return onFocus JavaScript function
     */
    public String getOnFocus() {
        return onFocus;
    }

    /**
     * Returns the onClick javascript function
     *
     * @return the onClick javascript function
     */
    public String getOnClick() {
        return onClick;
    }

    /**
     * Sets the onClick JavaScript function
     *
     * @param onClick the onFocus JavaScript function
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * Returns the onChange JavaScript function
     *
     * @return onChange JavaScript function
     */
    public String getOnChange() {
        return onChange;
    }

    /**
     * Sets the onChange JavaScript function
     *
     * @param onChange the onChange JavaScript function
     */
    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    /**
     * Returns the onBlur JavaScript function
     *
     * @return onBlur JavaScript function
     */
    public String getOnBlur() {
        return onBlur;
    }

    /**
     * Sets the onBlur JavaScript function
     *
     * @param onBlur the onBlur JavaScript function
     */
    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }

    /**
     * Returns the onSelect JavaScript function
     *
     * @return onSelect JavaScript function
     */
    public String getOnSelect() {
        return onSelect;
    }

    /**
     * Sets the onSelect JavaScript function
     *
     * @param onSelect the onSelect JavaScript function
     */
    public void setOnSelect(String onSelect) {
        this.onSelect = onSelect;
    }


    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

    public void addValidatorBean(ValidatorBean validatorBean) {
        validatorBeans.add(validatorBean);
    }

}