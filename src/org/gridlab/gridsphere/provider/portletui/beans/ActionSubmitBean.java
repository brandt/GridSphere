/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletURI;

import java.util.List;
import java.util.ArrayList;

public class ActionSubmitBean extends ActionBean implements TagBean {

    public static final String SUBMIT_STYLE = "portlet-frame-text";
    public static final String NAME = "as";

    public ActionSubmitBean() {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
    }

    public ActionSubmitBean(PortletRequest req, String beanId) {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
        this.request = req;
        this.beanId = beanId;
    }

    public String toStartString() {

        String pname = (name == null) ? "" : name;
        String sname = pname;
        //System.err.println("pname=" + pname+createSubmitName());
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        } else {
            sname = SportletProperties.DEFAULT_PORTLET_ACTION + "=" + action;
        }
        return "<input type=\"submit\" name=\"" + sname + "\" value=\"" + value + "\">";
    }
}
