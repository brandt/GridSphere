/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public abstract class ReadOnlyBaseBean extends BaseNameValueBean implements ReadOnlyBean {

    protected boolean readonly;

    public ReadOnlyBaseBean() {
        super();
    }

    public ReadOnlyBaseBean(String name, String value, boolean disabled, boolean readonly) {
        super(name, value, disabled);
        this.readonly = readonly;
    }

    public void setReadonly(boolean flag) {
        this.readonly = flag;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public String checkReadonly() {
        if (readonly) {
            return " disabled='disabled' ";
        } else {
            return "";
        }
    }

}
