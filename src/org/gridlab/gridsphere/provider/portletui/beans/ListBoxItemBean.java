/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public class ListBoxItemBean extends SelectElementBean {

    public static final String NAME = "li";

    public ListBoxItemBean() {
        super(NAME);
    }

    public ListBoxItemBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
    }

    public ListBoxItemBean(PortletRequest req, String beanId) {
        super(NAME);
        this.request = req;
        this.beanId = beanId;
    }

    public String toStartString() {
        String pval = (value == null) ? "" : value;
        String sname = (name == null) ? "" : name;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pval;
        }
        return "<option value='" + sname + "' " + checkDisabled() + " " + checkSelected("selected") + ">" + value + "</option>";
    }
}
