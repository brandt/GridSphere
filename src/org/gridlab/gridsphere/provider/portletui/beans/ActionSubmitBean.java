/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * An <code>ActionSubmitBean</code> is a visual bean that represents an HTML button and
 * has an associated <code>DefaultPortletAction</code>
 */
public class ActionSubmitBean extends ActionBean implements TagBean {

    public static final String SUBMIT_STYLE = "portlet-form-button";
    public static final String NAME = "as";

    /**
     * Constructs a default action submit bean
     */
    public ActionSubmitBean() {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
    }

    /**
     * Constructs an action submit bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public ActionSubmitBean(PortletRequest req, String beanId) {
        super(NAME);
        this.cssStyle = SUBMIT_STYLE;
        this.request = req;
        this.beanId = beanId;
    }

    public String toStartString() {
        return "<input type=\"submit\" ";
    }

    public String toEndString() {
        String pname = (name == null) ? "" : name;
        String sname = pname;
        //System.err.println("pname=" + pname+createSubmitName());
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        } else {
            sname = action;
        }
        return "name=\"" + sname + "\" value=\"" + value + "\">";
    }

}
