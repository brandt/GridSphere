/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 14, 2003
 * Time: 10:25:56 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.provider.validator;

import org.gridlab.gridsphere.tags.ui.BaseTag;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class StringValidator extends TagSupport implements Validator {

    private int min = 0;
    private int max = 30;
    private String value = null;

    public void setMinLength(int min) {
        this.min = min;
    }

    public int getMinLength() {
        return min;
    }

    public void setMaxLength(int max) {
        this.max = max;
    }

    public int getMaxLength() {
        return max;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int doStartTag() throws JspException {

        return EVAL_BODY_INCLUDE;
    }

    public boolean isValid() {
        BaseTag input = (BaseTag)getParent();
        String name = input.getName();

        return true;
    }

}
