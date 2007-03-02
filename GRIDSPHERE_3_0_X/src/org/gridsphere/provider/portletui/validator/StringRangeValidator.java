/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: StringRangeValidator.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.validator;

public class StringRangeValidator extends BaseValidator {

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

    public boolean isValid() {
        /*
        BaseTag input = (BaseTag)getParent();
        String name = input.getName();
        */
        return true;
    }

}
