/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

import java.util.Iterator;

public  class MultipleSelectListModel extends SelectListModel {

    protected boolean multipleselection = false;


    /**
     * Sets all items to the selected state
     *
     */
    private void setAllSelected(boolean flag) {
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            ((ListSelectItem)it.next()).setSelected(flag);
        }
    }

    /**
     * Marks all items selected, also marks the list as multiple selection capable
     */
    public void selectAll() {
        multipleselection = true;
        setAllSelected(true);
    }

    /**
     * unselects all items
     */
    public void unselectAll() {
        setAllSelected(false);
    }

    /**
     * Reverses the selection of the items, set multiple selection capable
     */
    public void invertSelect() {
        Iterator it = elements.iterator();
        multipleselection = true;
        while (it.hasNext()) {
            ListSelectItem item = (ListSelectItem)it.next();
            item.setSelected(!item.isSelected());
        }
    }


}
