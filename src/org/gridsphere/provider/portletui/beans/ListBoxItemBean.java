/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ListBoxItemBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.provider.portletui.beans;

/**
 * A <code>ListBoxItemBean</code> defines the elements contained within a <code>ListBoxBean</code>
 */
public class ListBoxItemBean extends SelectElementBean {

    /**
     * Constructs a default listbox item bean
     */
    public ListBoxItemBean() {
        super(TagBean.LISTBOXITEM_NAME);
    }

    /**
     * Constructs a listbox item bean with a supplied bean identifier
     *
     * @param beanId the listbox item bean identifier
     */
    public ListBoxItemBean(String beanId) {
        this();
        this.beanId = beanId;
    }

    public String toStartString() {
        String pval = (value == null) ? "" : value;
        pval = (name == null) ? pval : name;
        String sname = pval;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pval;
        }
        // 'selected' replaced by 'selected="selected"' for XHTML 1.0 Strict compliance
        return "<option " + getFormattedCss() + " value='" + sname + "' " + checkDisabled() + " " + checkSelected("selected=\"selected\"") + ">" + value + "</option>";
    }

    public String toEndString() {
        return "";
    }
}
