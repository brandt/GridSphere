/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The abstract <code>SelectElementBean</code> provides a base selectable element that is subclassed by
 * <code>RadioButtonBean</code>, <code>CheckBoxBean</code> and <code>ListBoxItemBean</code>
 */
public abstract class SelectElementBean extends BaseComponentBean implements TagBean {

    protected boolean selected = false;
    protected List results = new ArrayList();

    /**
     * Constructs a default select element bean
     */
    public SelectElementBean() {
        super();
    }

    /**
     * Constructs a select element bean from a supplied visual bean type
     *
     * @param vbName the visual bean type
     */
    public SelectElementBean(String vbName) {
        super(vbName);
    }

    /**
     * Returns a String used in the final markup indicating if this bean is selected or not
     *
     * @param select the selected String
     * @return the String used in the final markup indicating if this bean is selected or not
     */
    protected String checkSelected(String select) {
        if (selected) {
            return " " + select + "='" + select + "' ";
        } else {
            return "";
        }
    }

    /**
     * Sets the selected status of the bean
     *
     * @param flag true if the bean is selected, false otherwise
     */
    public void setSelected(boolean flag) {
        this.selected = flag;
    }

    /**
     * Returns the selected status of the bean
     *
     * @return true if the bean is selected, false otherwise
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Returns the selected values as a list
     *
     * @return selected values as a the list
     */
    public List getSelectedValues() {
        return results;
    }

    /**
     * Adds a selected value to this bean
     *
     * @param value a selected value
     */
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

    public String toEndString() {
        return "";
    }
}
