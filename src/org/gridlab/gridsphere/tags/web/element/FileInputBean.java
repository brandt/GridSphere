/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public class FileInputBean extends ReadOnlyBaseBean implements Input {

    protected int size;
    protected String inputtype = "file";
    protected int maxlength;

    public FileInputBean() {
        super();
    }

    public FileInputBean(String name, String value, boolean disabled, boolean readonly, int size, int maxlength) {
        //  super(name, value, disabled, readonly);
        super();
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

    public int getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype;
    }

    public String toString() {
        return getCSS("<input type='" + inputtype + "' name='" + getTagName() + name + "' value='" + value + "' size='"
                + size + "' maxlength='" + maxlength +"'"+checkReadonly()+checkDisabled()+"/>");
    }

}
