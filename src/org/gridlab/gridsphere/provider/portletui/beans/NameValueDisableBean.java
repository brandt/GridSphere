/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public abstract class NameValueDisableBean extends LabelBean implements Disableable  {

    protected boolean disabled;

    public NameValueDisableBean() {
        super();
    }

    public NameValueDisableBean(String name, String value, boolean disabled) {
        super();
        this.name = name;
        this.value = value;
        this.disabled = disabled;
        //this.id = name;
    }

    /**
     * Returns disabled String if bean is disabled
     * @return String depending if bean is disabled
     */
    protected String checkDisabled() {
        if (disabled) {
            return " disabled='disabled' ";
        } else {
            return "";
        }
    }


    /**
     * Returns true if bean is in disabled state.
     * @return state
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled attribute of the bean to be 'flag' state.
     * @param flag status
     */
    public void setDisabled(boolean flag) {
        this.disabled = flag;
    }

}
