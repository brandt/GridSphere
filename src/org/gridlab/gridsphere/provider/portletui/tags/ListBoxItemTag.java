/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.List;
import java.util.Iterator;

/**
 * A <code>ListBoxItemtag</code> represents a list box element
 */
public class ListBoxItemTag extends BaseComponentTag {

    protected ListBoxItemBean listboxitem = null;
    protected boolean selected = false;

    /**
     * Sets the element to be selected
     *
     * @param selected is true if the element is selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Return true if this element is selected
     *
     * @return true if this element is selected, false otherwise
     */
    public boolean getSelected() {
        return selected;
    }

    /**
     * Checks to see if listbox tag already contains this list item
     *
     * @param listboxTag the listbox tag
     * @return true if the listbox tag conatins this list item
     */
    protected boolean itemExists(ListBoxTag listboxTag) {
        ListBoxBean listbox = listboxTag.getListBoxBean();
        List beans = listbox.getBeans();
        Iterator it = beans.iterator();
        while (it.hasNext()) {
            ListBoxItemBean listboxitem = (ListBoxItemBean)it.next();
            if (name != null) {
                if (name.equals(listboxitem.getName())) {
                    listboxitem.setValue(value);
                    return true;
                }
            }
            if (value.equals(listboxitem.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deselects an element if not multiple selection and an existing list item is already selected
     *
     * @param listboxTag the list box tag
     */
    protected void checkSelectedEntries(ListBoxTag listboxTag) {
        if (selected) {
            if (!listboxTag.getMultipleSelection()) {
                ListBoxBean listbox = listboxTag.getListBoxBean();
                List beans = listbox.getBeans();
                Iterator it = beans.iterator();
                while (it.hasNext()) {
                    ListBoxItemBean listboxitem = (ListBoxItemBean)it.next();
                    if (listboxitem.isSelected()) {
                        selected = false;
                    }
                }
            }
        }
    }

    public int doEndTag() throws JspException {

        ListBoxTag listboxTag = (ListBoxTag)getParent();
        if (listboxTag != null) {
            //System.err.println("Setting action param bean: " + name + " " + value);
            ListBoxItemBean listboxitem = new ListBoxItemBean();
            this.setBaseComponentBean(listboxitem);
            // check that item doesn't already exist
            if (!itemExists(listboxTag)) {
                checkSelectedEntries(listboxTag);
                listboxitem.setSelected(selected);
                listboxTag.addTagBean(listboxitem);
            }
        }

        return EVAL_PAGE;
    }

}
