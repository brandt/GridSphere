/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: TextFieldTag.java 5032 2006-08-17 18:15:06Z novotny $
 */

package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridsphere.provider.portletui.beans.ValidatorBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>TextFieldTag</code> represents a text field element
 */
public class TextFieldTag extends InputTag {

    protected TextFieldBean textFieldBean = null;
    protected int size = 0;
    protected int maxlength = 0;

    /**
     * Returns the (html) size of the field
     *
     * @return size of the field
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the (html) size of the field
     *
     * @param size size of the field
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the (html) max length of the field
     *
     * @return the max length of the field
     */
    public int getMaxlength() {
        return maxlength;
    }

    /**
     * Sets the (html) max length of the field
     *
     * @param maxlength the max length of the field
     */
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            textFieldBean = (TextFieldBean) getTagBean();
            if (textFieldBean == null) {
                //log.debug("Creating new text field bean");
                textFieldBean = new TextFieldBean();
                this.setBaseComponentBean(textFieldBean);
            } else {
                //log.debug("Using existing text field bean");
                this.updateBaseComponentBean(textFieldBean);
            }
        } else {
            textFieldBean = new TextFieldBean();
            this.setBaseComponentBean(textFieldBean);
        }
        if (maxlength != 0) textFieldBean.setMaxLength(maxlength);
        if (size != 0) textFieldBean.setSize(size);
        if (onFocus != null) textFieldBean.setOnFocus(onFocus);
        if (onClick != null) textFieldBean.setOnClick(onClick);
        if (onChange != null) textFieldBean.setOnChange(onChange);
        if (onBlur != null) textFieldBean.setOnBlur(onBlur);
        if (onSelect != null) textFieldBean.setOnSelect(onBlur);

        if (cssClass != null) textFieldBean.addCssClass(cssClass);
        if (cssStyle != null) textFieldBean.addCssStyle(cssStyle);
        //debug();
        JspWriter out;

        try {
            out = pageContext.getOut();
            out.print(textFieldBean.toStartString());
            // print out validators, represented as hidden fields
            if (!validatorBeans.isEmpty()) {
                ValidatorBean validatorBean = null;
                for (int i = 0; i < validatorBeans.size(); i++) {
                    validatorBean = (ValidatorBean) validatorBeans.get(i);
                    validatorBean.setName(textFieldBean.getEncodedName());
                    out.print(validatorBean.toStartString());
                }
            }
            validatorBeans.clear();
            cssClass = null;
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }

}
