/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

import org.gridlab.gridsphere.provider.ui.model.SelectList;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;


public class DropDownListBean extends BaseListBean implements DropDownList {

    SelectList list = new SelectList();

    protected transient static PortletLog log = SportletLog.getInstance(DropDownListBean.class);

    protected int size = 1;
    protected boolean multiple = false;

    public DropDownListBean(String name) {
        super();
        this.name = name;
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
        list.addElement(item);
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
        list.addElement(item);
    }


    /**
     * Sets the selected flag on an entry.
     * @param index index of the element
     * @param selected true/false representing the selected status
     */
    public void setSelected(int index, boolean selected) {
        list.setSelected(index, selected);
    }

    /**
     * Adds a selectable item to the list
     * @param item selectable item to be added
     */
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
            item.setCID(this.cid);
            result = result + item.toString();
        }
        result = result +"</select>";
        return result;
    }

    public void update(String[] values) {
        //@todo FIXME NPE
        if (values==null)  {
            // nothing
            log.debug("Updated values are null?! ("+this.getName()+")");
        } else {
            //if (!multiple) {
                list.unselectAll();
            //}
            log.debug("\n\n\n\n\n\nUdating List "+this.getName()+" with "+values.toString());
            for (int i=0;i<values.length;i++) {
                list.setSelected(values[i], true);
            }
       // } catch (NullPointerException e) {
            // ok was empty, nothing selected
        }
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
