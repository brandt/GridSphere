/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;

public class ActionSubmitBean extends BaseComponentBean implements TagBean {

    protected String key = "";
    protected String action = "";

    public ActionSubmitBean() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String toString() {
        return "<input type=\"submit\" name=\"" + SportletProperties.DEFAULT_PORTLET_ACTION + "=" + action +  "\" value=\"" + value + "\">";
    }
}
