/**
 * @author <a href="novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>ListBoxItemBean</code> defines the elements contained within a <code>ListBoxBean</code>
 */
public class ListBoxItemBean extends SelectElementBean {

    public static final String NAME = "li";

    /**
     * Constructs a default listbox item bean
     */
    public ListBoxItemBean() {
        super(NAME);
    }

    /**
     * Constructs a listbox item bean with a supplied bean identifier
     *
     * @param beanId the listbox item bean identifier
     */
    public ListBoxItemBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
    }

    /**
     * Constructs a listbox item bean with a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public ListBoxItemBean(PortletRequest req, String beanId) {
        super(NAME);
        this.request = req;
        this.beanId = beanId;
    }

    public String toStartString() {
        String pval = (value == null) ? "" : value;
        String sname = pval;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pval;
        }
        return "<option value='" + sname + "' " + checkDisabled() + " " + checkSelected("selected") + ">" + value + "</option>";
    }

    public String toEndString() {
        return "";
    }
}
