/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;


public class DropDownListBean extends NameBean implements DropDownList {

    protected List list = new ArrayList();

    protected transient static PortletLog log = SportletLog.getInstance(DropDownListBean.class);

    protected int size = 1;
    protected boolean isMultiple = false;

    public DropDownListBean(String name) {
        super(name);
    }

    /**
     * Adds an entry to the dropdownlist.
     * @param label label of the entry
     * @param value value of the entry
     */
    public void add(String label, String value) {
        ListBoxItemBean item = new ListBoxItemBean();
        item.setName(label);
        item.setValue(value);
        list.add(item);
    }

    /**
     * Adds an entry to the dropdownlist.
     * @param label label of the entry
     * @param value value of the entry
     * @param selected marks if the element should be selected or not
     */
    public void add(String label, String value, boolean selected) {
        ListBoxItemBean item = new ListBoxItemBean();
        item.setName(label);
        item.setValue(value);
        item.setSelected(selected);
        list.add(item);
    }


    /**
     * Sets the selected flag on an entry.
     * @param index index of the element
     * @param selected true/false representing the selected status
     */
    public void setSelected(int index, boolean selected) {
        ListBoxItemBean item = (ListBoxItemBean)list.get(index);
        item.setSelected(selected);
    }

    /**
     * Adds a selectable item to the list
     * @param item selectable item to be added
     */
    public void add(Selectable item) {
        list.add(item);
    }

    /**
     * Clears items in list
     */
    public void clear() {
        list.clear();
    }

    public String toString() {
        String result = "<select name='"+name+"' size='"+size+"'";
        if (isMultiple) {
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

    /**
     * Returns the selected values of the list.
     * @return selected values of the list
     */
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

    /**
     * Returns the selected items of the list
     * @return  the selected item of the list
     */
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
