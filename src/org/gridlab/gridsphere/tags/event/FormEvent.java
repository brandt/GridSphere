/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.tags.event;


import org.gridlab.gridsphere.tags.web.element.ElementBean;

import java.util.List;
import java.util.Collection;

public interface FormEvent {

   /**
    * Returns the name of the pressed submit button.
    * @parameter event the actionevent
    * @return name of the button which was pressed
    */
    public String getPressedSubmitButton();

    public String getRadioButton();

    /**
     * Gets the list of selected values of a listbox
     * @param listboxname name of the listbox
     * @return array of stringvalues with the values of the listboxes
     */
    public String[] getSelectedListBoxValues (String listboxname);

    /**
     * Gets the list of values of selected checkboxes in a group (means with the same name), returns null if does not exist
     * @param checkboxgroupname name of the checkboxgroup
     * @return array of stringvalues with the values of the checkboxes
     */
    public String[] getSelectedCheckBoxValues (String checkboxgroupname);

    /**
     * Gets back the bean with the modifications from the userinterface.
     * @param name name of the bean
     * @return updated elementbean
     */
    public Object getElementBean(String name);

}
