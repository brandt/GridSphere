/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class SelectElement extends BaseNameValue implements Selectable {

    protected boolean selected = false;

    public SelectElement(String name, String value, boolean selected, boolean disabled) {
        this.name = name;
        this.value = value;
        this.selected = selected;
        this.disabled = disabled;
    }


    public String checkSelected(String select) {
        if (selected) {
            return " " + select + "='" + select + "' ";
        } else {
            return "";
        }
    }

    public void setSelected(boolean flag) {
        this.selected = flag;
    }

    public boolean isSelected() {
        return selected;
    }

    public String toString(String type) {
        return "<input type='"+type+"' name='"+name+"' value='"+value+"' "+checkDisabled()+" "+checkSelected("checked")+
            "/>";
    }

}
