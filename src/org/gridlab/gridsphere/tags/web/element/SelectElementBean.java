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
