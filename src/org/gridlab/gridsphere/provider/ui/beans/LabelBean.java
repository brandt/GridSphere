/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

public class LabelBean extends NameBean implements Label {

    protected String value = new String();

    public LabelBean() {
        super();
    }

    public LabelBean(String label) {
        super();
        this.value = label;
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
     * Updates the values of the class.
     * @param values array of stringvalues with updated values for this class
     */
    public void update(String[] values) {}

    public String toString() {
        return getCSS(value);
    }

}
