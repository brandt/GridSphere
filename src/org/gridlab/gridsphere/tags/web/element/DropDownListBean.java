/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

import org.gridlab.gridsphere.tags.web.model.SelectList;

import java.util.Iterator;


public class DropDownListBean extends BaseListBean implements Updateable {

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
        String result = "<select name='"+getTagName()+name+"' size='"+size+"'>";
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Selectable item = (Selectable)it.next();
            result = result + item.toString();
        }
        result = result +"</select>";
        return result;
    }

    public void update(String[] values) {
        list.unselectAll();
        list.setSelected(values[0], true);
    }

    public String getSelectedValue() {
        Selectable item = list.getSelectedItem();
        return item.getValue();
    }

    public Selectable getSelectedItem() {
        return (Selectable)list.getSelectedItem();
    }


}
