/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class BaseNameValueBean extends NameBean implements Valueable, Updateable {

    protected String value;
    protected boolean disabled;

    public BaseNameValueBean() {
        super();
    }

    public BaseNameValueBean(String name, String value, boolean disabled) {
        super();
        this.name = name;
        this.value = value;
        this.disabled = disabled;
        this.id = name;
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
     * Sets the value of the bean.
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the bean.
     * @return the value
     */
    public String getValue() {
        return value;
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

    /**
     * Updates the value(s) of the bean.
     * @param values array of strings representaing the updates
     */
    public void update(String[] values) {
        this.value = values[0];
    }
}
