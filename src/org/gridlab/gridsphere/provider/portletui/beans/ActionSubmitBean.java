/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class ActionSubmitBean extends BaseBean implements TagBean {

    protected String name = "";
    protected String value = "";
    protected String action = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (request != null) {
            System.err.println("saving into session");
            store(this);
        }
    }

    public String getValue() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
        if (request != null) {
            System.err.println("saving into session");
            store(this);
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
        if (request != null) {
            System.err.println("saving into session");
            store(this);
        }
    }

    public String toString() {
        return "<input type=\"submit\" name=\"" + name + "\" value=\"" + value + "\">";
    }
}
