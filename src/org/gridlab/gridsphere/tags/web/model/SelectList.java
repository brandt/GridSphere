/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

import org.gridlab.gridsphere.tags.web.element.Selectable;

import java.util.Iterator;

public class SelectList extends DefaultList implements SelectListModel {

    public void add(int index, Selectable item) {
        list.add(index, item);
    }

    public void addElement(Selectable item) {
        list.add(item);
    }

    public void setSelected(int index, boolean flag) {
        ((Selectable)list.get(index)).setSelected(flag);
    }

    public void unselectAll() {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((Selectable)it.next()).setSelected(false);
        }
    }

    private Selectable getSelectedItem() {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Selectable item = (Selectable)it.next();
            if (item.isSelected()) {
                return item;
            }
        }
        return null;
    }

    /**
     *
     */
    public void setSelected(String value, boolean flag) {
        Selectable item = getSelectedItem(value);
        item.setSelected(flag);
    }

    private Selectable getSelectedItem(String value) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Selectable item = (Selectable)it.next();
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
