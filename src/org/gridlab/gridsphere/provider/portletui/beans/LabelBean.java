/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class LabelBean extends NameBean implements Label, TagBean {

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

    public String toString() {
        return value;
    }

}
