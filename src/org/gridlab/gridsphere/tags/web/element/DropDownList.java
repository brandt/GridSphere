/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

import java.util.ArrayList;

public interface DropDownList extends Updateable {

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

}
