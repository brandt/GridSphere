/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public abstract class SelectElementBean extends BaseComponentBean implements TagBean {

    protected boolean selected = false;

    public SelectElementBean() {
        super();
    }

    public SelectElementBean(String vbName) {
        super(vbName);
    }

    protected String checkSelected(String select) {
        if (selected) {
            return " " + select + "='" + select + "' ";
        } else {
            return "";
        }
    }

    /**
     * Sets the selected status of the bean.
     * @param flag status of the bean
     */
    public void setSelected(boolean flag) {
        this.selected = flag;
    }

    /**
     * Returns the selected status of the bean
     * @return selected status
     */
    public boolean isSelected() {
        return selected;
    }

    public String toString(String type) {


        String pname = (name == null) ? "" : name;
        String sname = pname;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        }
        return "<input type='" + type + "' name='" + sname + "' value='" + value + "' " + checkDisabled() + " " + checkSelected("checked") +
                "/>";
    }

}
