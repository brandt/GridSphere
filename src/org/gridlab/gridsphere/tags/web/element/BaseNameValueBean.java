/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class BaseNameValueBean extends BaseElementBean implements NameValueBean {

    protected String value;
    protected String name;
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

    public String checkDisabled() {
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
     * Sets the name of the bean.
     * @param name the name of the bean
     */
    public void setName(String name) {
        this.name = name;
        this.id = name;
    }

    /**
     * Gets the name of the bean.
     * @return the name of the bean
     */
    public String getName() {
        return name;
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
