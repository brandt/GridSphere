/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public class ActionLinkBean extends ActionBean implements TagBean {

    public static final String ACTION_STYLE = "portlet-frame-text";

    public ActionLinkBean() {
        this.cssStyle = ACTION_STYLE;
    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        //if (value == null) createLink();
        //return "<a href=\"" + action + "\"/>" + value + "</a>";
        return "<a href=\"" + action + "\"" + " onClick=\"this.href='" + action + "&JavaScript=enabled'\"/>" + value + "</a>";
    }

}
