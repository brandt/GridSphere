/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public class TextFieldBean extends InputBean implements TagBean  {

    public static final String TEXTFIELD_STYLE = "portlet-frame-text";

    public TextFieldBean() {
        super();
        this.inputtype = "text";
        this.cssStyle = TEXTFIELD_STYLE;
    }

    public TextFieldBean(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public TextFieldBean(String name, String value, boolean disabled, boolean readonly, int size, int maxlength) {
        //  super(name, value, disabled, readonly);
        super();
        this.name = name;
        this.value = value;
        this.disabled = disabled;
        this.readonly = readonly;
        this.size = size;
        this.maxlength = maxlength;
    }

    /*
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type='");
        buffer.append(inputType);
        buffer.append("' name='");
        buffer.append(name);
        buffer.append("' value='");
        buffer.append(value);
        buffer.append("' ");
        if (maxlength > 0) {
            buffer.append("maxlength='");
            buffer.append(maxlength);
            buffer.append("' ");
        }
        if (size > 0) {
            buffer.append("size='");
            buffer.append(size);
            buffer.append("' ");
        }
        buffer.append(checkReadonly());
        buffer.append(checkDisabled());
        buffer.append("/>");
        return buffer.toString();
    }
    */

}
