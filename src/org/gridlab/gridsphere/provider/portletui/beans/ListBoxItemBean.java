/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import javax.servlet.http.HttpServletRequest;

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
     * @param req    the portlet request
     * @param beanId the bean identifier
     */
    public ListBoxItemBean(HttpServletRequest req, String beanId) {
        super(NAME, req);
        this.beanId = beanId;
    }

    public String toStartString() {
        String pval = (value == null) ? "" : value;
        pval = (name == null) ? pval : name;
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
