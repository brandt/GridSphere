/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class BaseNameValue extends BaseElement implements NameValue {

    protected String value;
    protected String name;
    protected boolean disabled;

    public String checkDisabled() {
        if (disabled) {
            return " disabled='disabled' ";
        } else {
            return "";
        }
    }

    public BaseNameValue() {
        super();
    }

    public BaseNameValue(String name, String value, boolean disabled) {
        super();
        this.name = name;
        this.value = value;
        this.disabled = disabled;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean flag) {
        this.disabled = flag;
    }
}
