/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class SelectElementBean extends BaseComponentBean implements TagBean {

    protected boolean selected = false;
    protected List results = new ArrayList();

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

    /**
     * Returns the selected values of the list.
     * @return selected values of the list
     */
    public List getSelectedValues() {
        return results;
    }

    public void addSelectedValue(String value) {
        results.add(value);
    }

    public String toStartString(String type) {


        String pname = (name == null) ? "" : name;
        String sname = pname;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        }
        return "<input type='"
                + type
                + "' name='"
                + sname
                + "' value='"
                + value + "' "
                + checkDisabled()
                + " "
                + checkSelected("checked")
                + "/>";
    }

}
