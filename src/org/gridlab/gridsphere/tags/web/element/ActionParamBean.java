/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 *
 */
package org.gridlab.gridsphere.tags.web.element;

public class ActionParamBean {

    protected String name = "";
    protected String value = "";

    /**
     * Sets the action
     * @param name the actionlink
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the actionlink string
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the action parameter value
     * @param value the action parameter value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the actionlink string
     */
    public String getValue() {
        return value;
    }

}
