/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public abstract class InputBean extends BaseComponentBean implements TagBean {

    protected String inputtype = "";
    protected int size = 0;
    protected int maxlength = 0;

    public InputBean() {}

    public InputBean(String name) {
        super(name);
    }

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

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<input ");
        sb.append("type=\"" + inputtype + "\" ");


        String pname = (name == null) ? "" : name;
        String sname = pname;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        }

        sb.append("name=\"" + sname + "\" ");
        if (value != null) sb.append("value=\"" + value + "\" ");
        if (size != 0) sb.append("size=\"" + size + "\" ");
        if (maxlength != 0) sb.append("maxlength=\"" + maxlength + "\" ");
        sb.append(checkReadonly());
        sb.append(checkDisabled());
        sb.append("/>");
        return sb.toString();
    }

}
