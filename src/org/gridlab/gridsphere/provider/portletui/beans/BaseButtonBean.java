/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * The <code>BaseButtonBean</code> provides basic functionality for ButtonBeans.
 */
public class BaseButtonBean extends NameValueDisableBean implements Button {

    protected String type = new String();

    public BaseButtonBean() {
        super();
    }

    public BaseButtonBean(String name, String value) {
        super(name, value, false);
    }

    /**
     * Sets the type of the button.
     * @param type type of the button
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the type of the button.
     * @return type of the button
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the tagname of the button.
     * @return returns the name of the tag
     */
    public String getTagName() {
        return "gssubmit:";
    }

    public String toString() {
        return "<input type='" + type + "' name='" + getTagName() + name + "' value='" + value + "'/>";
    }
}
