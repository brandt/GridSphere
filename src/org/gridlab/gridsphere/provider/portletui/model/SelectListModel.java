/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.model;

import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class SelectListModel extends DefaultListModel {

    public void setSelected(int index, boolean flag) {
        ((ListBoxItemBean)list.get(index)).setSelected(flag);
    }

    public void unselectAll() {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ListBoxItemBean)it.next()).setSelected(false);
        }
    }

    public List getSelectedItems() {
        Iterator it = list.iterator();
        List selectedList = new ArrayList();
        while (it.hasNext()) {
            ListBoxItemBean item = (ListBoxItemBean)it.next();
            if (item.isSelected()) {
                selectedList.add(item);
            }
        }
        return selectedList;
    }


}
