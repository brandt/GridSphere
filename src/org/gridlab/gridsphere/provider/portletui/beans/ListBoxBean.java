/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;


public class ListBoxBean extends BaseComponentBean implements TagBean {

    protected List list = new ArrayList();

    protected transient static PortletLog log = SportletLog.getInstance(ListBoxBean.class);

    protected int size = 1;
    protected boolean isMultiple = false;

    public ListBoxBean() {

    }

    public ListBoxBean(String beanId) {
        this.beanId = beanId;
    }

    public ListBoxBean(PortletRequest request, String beanId) {
        this.request = request;
        this.beanId = beanId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMultipleSelection(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public boolean getMultipleSelection() {
        return isMultiple;
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
    public void add(ListBoxItemBean item) {
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
            ListBoxItemBean item = (ListBoxItemBean)it.next();
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
            ListBoxItemBean item = (ListBoxItemBean)it.next();
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
            ListBoxItemBean item = (ListBoxItemBean)it.next();
            if (item.isSelected()) {
                result.add(item);
            }
        }
        return result;
    }

}
