/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class SelectElementBean extends BaseNameValueBean implements SelectableBean {

    protected boolean selected = false;

    public SelectElementBean() {
        super();
    }

    public SelectElementBean(String name, String value, boolean selected, boolean disabled) {
        super(name, value, disabled);
        this.selected = selected;
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
        return "<input type='" + type + "' name='" + name + "' value='" + value + "' " + checkDisabled() + " " + checkSelected("checked") +
                "/>";
    }

    public void update(String[] values) {
        if (!values[0].equals("")) {
            selected = true;
        } else {
            selected = false;
        }

    }


}
