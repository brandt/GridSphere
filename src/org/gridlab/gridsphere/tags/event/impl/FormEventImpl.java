/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.event.impl ;

import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.tags.web.element.Element;
import org.gridlab.gridsphere.tags.web.element.BaseNameValueBean;
import org.gridlab.gridsphere.tags.web.element.CheckBoxBean;


import javax.servlet.http.HttpSession;
import java.util.*;

public class FormEventImpl implements FormEvent {

    protected ActionEvent event;
    protected PortletRequest request;

    public FormEventImpl(PortletRequest request) {
        this.request = request;
    }

    public FormEventImpl(ActionEvent evt) {
        event = evt;
        request = evt.getPortletRequest();
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

    private boolean checkParameterName(String name) {
        Enumeration enum = request.getParameterNames();
        while (enum.hasMoreElements()) {
            if ( ((String)enum.nextElement()).equals(name) ) {
                return true;
            }
        }
        return false;
    }

    public Object getElementBean(String name, PortletRequest request) {
        HttpSession session = request.getSession();
        BaseNameValueBean bean = (BaseNameValueBean)session.getAttribute(name);

        if (checkParameterName("gstag:"+bean.getName())) {
            String[] values = request.getParameterValues("gstag:"+bean.getName());
            if (values.length>0) {
                bean.update(values);
            }
        }
        return bean;
    }

    public Object getElementBean(String name) {
        printRequestParameter(request);
        PortletRequest request = event.getPortletRequest();
        return getElementBean(name, request);
    }

    private void printRequestParameter(PortletRequest req) {
        System.out.println("\n\n show request params\n----------------\n");
        Enumeration enum = req.getParameterNames();
        while (enum.hasMoreElements()) {
            String name = (String)enum.nextElement();
            System.out.println("name :"+name);
            String values[] = req.getParameterValues(name);
            if (values.length == 1) {
                String pval = values[0];
                if (pval.length() == 0) {
                    pval = "no value";
                }
                System.out.println(" value : "+pval);
            } else {
                System.out.println(" value :");
                for (int i=0;i<values.length;i++) {
                    System.out.println("            - "+values[i]);
                }

            }
        }
    }
}
