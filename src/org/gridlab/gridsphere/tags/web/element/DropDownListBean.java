/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

import org.gridlab.gridsphere.tags.web.model.SelectList;

import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;


public class DropDownListBean extends BaseListBean implements DropDownList {

    SelectList list = new SelectList();

    protected int size = 1;
    protected boolean multiple = false;

    public DropDownListBean(String name) {
        super();
        this.name = name;
    }

    public void add(String name, String value) {
        ListBoxItemBean item = new ListBoxItemBean();
        item.setName(name);
        item.setValue(value);
        list.addElement(item);
    }

    public void add(Selectable item) {
        list.addElement(item);
    }

    public String toString() {
        String result = "<select name='"+getTagName()+name+"' size='"+size+"'";
        if (multiple) {
            result = result + " multiple='multiple'" ;
        }
        result = result +">";
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Selectable item = (Selectable)it.next();
            result = result + item.toString();
        }
        result = result +"</select>";
        return result;
    }

    public void update(String[] values) {
        try {
            if (!multiple) {
                list.unselectAll();
            }
            for (int i=0;i<values.length;i++) {
                list.setSelected(values[i], true);
            }
        } catch (NullPointerException e) {
            // ok was empty, nothing selected
        }
    }

    public ArrayList getSelectedValues() {
        ArrayList result = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Selectable item = (Selectable)it.next();
            if (item.isSelected()) {
                result.add(item.getValue());
            }
        }
        return result;
    }

    public ArrayList getSelectedItems() {
        ArrayList result = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Selectable item = (Selectable)it.next();
            if (item.isSelected()) {
                result.add(item);
            }
        }
        return result;
    }

}
