/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public abstract class InputBean extends BaseComponentBean implements TagBean {

    protected int size;
    protected String inputtype = "";
    protected int maxlength;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMaxLength() {
        return maxlength;
    }

    public void setMaxLength(int maxlength) {
        this.maxlength = maxlength;
    }

    public String toString() {
        return "<input type='" + inputtype + "' name='" + name + "' value='" + value + "' size='"
                + size + "' maxlength='" + maxlength +"'"+checkReadonly()+checkDisabled()+"/>";
    }

}
