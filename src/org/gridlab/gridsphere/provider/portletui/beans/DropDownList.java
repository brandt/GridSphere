/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.ArrayList;

public interface DropDownList  {

    /**
     * Returns the selected values of the list.
     * @return selected values oof the list
     */
    public ArrayList getSelectedValues();

    /**
     * Returns the selected items of the list
     * @return  the selected item of the list
     */
    public ArrayList getSelectedItems();

    /**
     * Adds a selectable item to the list
     * @param item selectable item to be added
     */
    public void add(Selectable item);

    /**
     * Adds an entry to the dropdownlist.
     * @param name name of the entry
     * @param value value of the entry
     */
    public void add(String name, String value);

    /**
     * Adds an entry to the dropdownlist.
     * @param label label of the entry
     * @param value value of the entry
     * @param selected marks if the element should be selected or not
     */
    public void add(String label, String value, boolean selected);

    /**
     * Sets the selected flag on an entry.
     * @param index index of the element
     * @param flag true/false representing the selected status
     */
    public void setSelected(int index, boolean flag);


}
