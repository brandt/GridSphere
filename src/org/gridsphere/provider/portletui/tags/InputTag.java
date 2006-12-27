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
    protected String onDblClick = null;
    protected String onChange = null;
    protected String onBlur = null;
    protected String onSelect = null;

    protected String onmousedown = null;
    protected String onmousemove = null;
    protected String onmouseout = null;
    protected String onmouseover = null;
    protected String onmouseup = null;

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
     * @param onClick the onClick JavaScript function
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * Returns the onDblClick javascript function
     *
     * @return the onDblClick javascript function
     */
    public String getOnDblClick() {
        return onDblClick;
    }

    /**
     * Sets the onDblClick JavaScript function
     *
     * @param onDblClick the onDblClick JavaScript function
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
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

    /**
     * Returns the onmousedown event
     *
     * @return the onmousedown function
     */
    public String getOnMouseDown() {
        return onmousedown;
    }

    /**
     * Sets the onmousedown event
     *
     * @param onmousedown the onmousedown function
     */
    public void setOnMouseDown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    /**
     * Returns the onmousemove function
     *
     * @return the onmousemove function
     */
    public String getOnMouseMove() {
        return onmousemove;
    }

    /**
     * Sets the onmousemove function
     *
     * @param onmousemove the onmousemove function
     */
    public void setOnMouseMove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    /**
     * Returns the onmouseout function
     *
     * @return the onmouseout function
     */
    public String getOnMouseOut() {
        return onmouseout;
    }

    /**
     * Sets the onmouseout function
     *
     * @param onmouseout the onmouseout function
     */
    public void setOnMouseOut(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    /**
     * Returns the onmouseover function
     *
     * @return the onmouseover function
     */
    public String getOnMouseOver() {
        return onmouseover;
    }

    /**
     * Sets the onmouseover javascript function
     *
     * @param onmouseover the onmouseover function
     */
    public void setOnMouseOver(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    /**
     * Returns the onMouseUp javascript function
     *
     * @return the onmouseup event
     */
    public String getOnMouseUp() {
        return onmouseup;
    }

    /**
     * Sets the onMouseUp javascript function
     *
     * @param onmouseup a mouseup event
     */
    public void setOnMouseUp(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

    public void addValidatorBean(ValidatorBean validatorBean) {
        validatorBeans.add(validatorBean);
    }

}