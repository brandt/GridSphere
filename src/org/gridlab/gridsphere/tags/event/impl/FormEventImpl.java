/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.event.impl ;

import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.tags.web.element.ElementBean;
import org.gridlab.gridsphere.tags.web.element.BaseNameValueBean;


import javax.servlet.http.HttpSession;
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




    public Object getElementBean(String name) {
        PortletRequest request = event.getPortletRequest();
        HttpSession session = request.getSession();
        System.out.println("GETELEMENTBEAN VALUE:"+((BaseNameValueBean)session.getAttribute(name)).getValue());
        return session.getAttribute(name);
    }

    public Object getUpdatedElementBean(String name) {
        PortletRequest request = event.getPortletRequest();
        HttpSession session = request.getSession();
        BaseNameValueBean bean = (BaseNameValueBean)session.getAttribute(name);

        System.out.println("BEANORIGINAL:"+bean.getValue());
        String[] values = request.getParameterValues(bean.getName());
        bean.setValue(values[0]);
        return bean;
    }
}
