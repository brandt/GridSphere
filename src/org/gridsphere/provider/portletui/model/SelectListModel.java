/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SelectListModel.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.provider.portletui.model;

import org.gridsphere.provider.portletui.beans.ListBoxItemBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A <code>SelectListModel</code> is a list model that tags elements of the list as selected
 */
public class SelectListModel extends DefaultListModel {

    /**
     * Set an item in the list to be selected
     *
     * @param index the items location in the list
     * @param flag  is true if the list item is selected, false otherwise
     */
    public void setSelected(int index, boolean flag) {
        ((ListBoxItemBean) list.get(index)).setSelected(flag);
    }

    /**
     * Deselects all items in the list
     */
    public void unselectAll() {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ListBoxItemBean) it.next()).setSelected(false);
        }
    }

    /**
     * Return a list of selected items
     *
     * @return a list of selected items
     */
    public List getSelectedItems() {
        Iterator it = list.iterator();
        List selectedList = new ArrayList();
        while (it.hasNext()) {
            ListBoxItemBean item = (ListBoxItemBean) it.next();
            if (item.isSelected()) {
                selectedList.add(item);
            }
        }
        return selectedList;
    }


}
