/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.event.impl ;

import org.gridlab.gridsphere.event.FormEvent;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

import org.gridlab.gridsphere.tags.web.model.CheckBoxItem;
import org.gridlab.gridsphere.tags.web.model.CheckBoxModel;
import org.gridlab.gridsphere.tags.web.model.ListBoxModel;

import java.util.*;

public class FormEventImpl implements FormEvent {

    protected ActionEvent event;

    public FormEventImpl(ActionEvent evt) {
        event = evt;
    }

    /**
     * Returns the name of the pressed submit button. To use this for form has to follow the convention
     * that the names of all submit-type buttons start with 'submit:' (and only those and no other elements)
     * @parameter event the actionevent
     * @return name of the button which was pressed
     */
    public String getPressedSubmitButton() {
        String result = new String();

        PortletRequest req = event.getPortletRequest();
        Enumeration enum = req.getParameterNames();
        while(enum.hasMoreElements()) {
            String name = (String)enum.nextElement();
            if (name.startsWith("submit:")) {
                String button = req.getParameter(name);
                if (button!=null) {
                    result = name.substring(7);
                }
            }
        }
        return result;
    }

    public String getRadioButton() {
        return null;
    }

    private String[] getSelectedItem(String groupname) {

        PortletRequest req = event.getPortletRequest();
        Enumeration enum = req.getParameterNames();
        while(enum.hasMoreElements()) {
            String name = (String)enum.nextElement();
            if (name.equals(groupname)) {
                return req.getParameterValues(name);
            }
        }
        return null;

    }

    /**
     * Gets the list of selected values of a listbox , return null if it does not exist
     * @param listboxname name of the listbox
     * @return array of stringvalues with the values of the listboxes
     */
    public String[] getSelectedListBoxValues (String listboxname) {
        return getSelectedItem(listboxname);
    }

    /**
     * Gets the list of values of selected checkboxes in a group (means with the same name), returns null if does not exist
     * @param checkboxgroupname name of the checkboxgroup
     * @return array of stringvalues with the values of the checkboxes
     */
    public String[] getSelectedCheckBoxValues (String checkboxgroupname) {
        PortletRequest req = event.getPortletRequest();
        return req.getParameterValues(checkboxgroupname);
    }

    /**
     * Modifies the passed in checkboxmodel to reflect the changes on the checkboxes
     * @model the model
     */
    public CheckBoxModel adjustCheckBoxModel(CheckBoxModel model) {

        model.unselectAll();

        /*CheckBoxModel model = new CheckBoxModel();


        String result[] = getSelectedCheckBoxValues(modelname);

        model.unselectAll();
        for (int i=0;i<result.length;i++) {
            ((CheckBoxItem)model.getItem(result[i])).setSelected(true);
        } */

        return model;
    }

    public ListBoxModel adjustListBoxModel(ListBoxModel model) {
        //model.unselectAll();
        return model;
    }

}
