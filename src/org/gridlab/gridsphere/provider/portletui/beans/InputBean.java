/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public abstract class InputBean extends BaseComponentBean implements TagBean {

    protected String inputtype = "";
    protected int size = 0;
    protected int maxlength = 0;

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
        StringBuffer sb = new StringBuffer();
        sb.append("<input ");
        sb.append("type=\"" + inputtype + "\" ");
        sb.append("name=\"" + name + "\" ");
        sb.append("value=\"" + value + "\" ");
        if (size != 0) sb.append("size=\"" + size + "\" ");
        if (maxlength != 0) sb.append("maxlength=\"" + maxlength + "\" ");
        sb.append(checkReadonly());
        sb.append(checkDisabled());
        sb.append("/>");
        return sb.toString();
    }

}
