/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public abstract class ReadOnlyBaseBean extends BaseNameValueBean implements ReadOnly {

    protected boolean readonly;

    public ReadOnlyBaseBean() {
        super();
    }

    public ReadOnlyBaseBean(String name, String value, boolean disabled, boolean readonly) {
        super(name, value, disabled);
        this.readonly = readonly;
    }

    /**
     * Sets the bean to readonly.
     * @param flag status of the bean
     */
    public void setReadonly(boolean flag) {
        this.readonly = flag;
    }

    /**
     * Returns the readonly status of the bean
     * @return readonly status
     */
    public boolean isReadonly() {
        return readonly;
    }


    protected String checkReadonly() {
        if (readonly) {
            return " disabled='disabled' ";
        } else {
            return "";
        }
    }

}
