/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public class InputFieldBean extends ReadOnlyBaseBean implements InputBean {

    protected int size;
    protected String inputtype = "text";
    protected int maxlength;

    public InputFieldBean() {
        super();
    }

    public InputFieldBean(String name, String value, boolean disabled, boolean readonly, int size, int maxlength) {
      //  super(name, value, disabled, readonly);
        this.name = name;
        this.value = value;
        this.disabled = disabled;
        this.readonly = readonly;
        this.size = size;
        this.maxlength = maxlength;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype;
    }

    public int getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }


    public String toString() {
        return "<input type='" + inputtype + "' name='" + name + "' value='" + value + "' size='"
                + size + "' maxlength='" + maxlength + "'/>";
    }

}
