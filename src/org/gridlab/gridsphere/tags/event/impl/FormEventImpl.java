/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 *
 * The Class provides methods for getting data out of a portletrequest.
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.event.impl;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.tags.web.element.CheckBoxBean;
import org.gridlab.gridsphere.tags.web.element.NameBean;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class FormEventImpl implements FormEvent {

    protected ActionEvent event;
    protected PortletRequest request;
    protected PortletResponse response;

    public FormEventImpl(PortletRequest request) {
        this.request = request;
    }

    public FormEventImpl(ActionEvent evt) {
        event = evt;
        request = evt.getPortletRequest();
    }

    public PortletRequest getPortletRequest() {
        return request;
    }

    public PortletResponse getPortletResponse() {
        return response;
    }

    public  DefaultPortletAction getAction() {
        return event.getAction();
    }

    /**
     * Returns the name of the pressed submit button. To use this for form has to follow the convention
     * that the names of all submit-type buttons start with 'submit:' (and only those and no other elements)
     * @return name of the button which was pressed
     */
    public String getPressedSubmitButton() {
        String result = null;

        PortletRequest req = event.getPortletRequest();
        Enumeration enum = req.getParameterNames();
        while (enum.hasMoreElements()) {
            String name = (String) enum.nextElement();
            if (name.startsWith("gssubmit:")) {
                String button = req.getParameter(name);
                if (button != null) {
                    result = name.substring(9);
                }
            }
        }
        return result;
    }


    private boolean checkParameterName(String name) {
        Enumeration enum = request.getParameterNames();
        while (enum.hasMoreElements()) {
            if (((String) enum.nextElement()).equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Object getBean(String name, PortletRequest request) {
        HttpSession session = request.getSession();
        NameBean bean = (NameBean) session.getAttribute(name);
        return bean;
    }

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.
     * @param name name of the bean
     * @param request requestobject where the bean was stored (in the session of the request)
     * @return updated elementbean
     */
    public Object getElementBean(String name, PortletRequest request) {
        HttpSession session = request.getSession();
        NameBean bean = (NameBean) getBean(name, request);
        System.out.println("Getting Bean " + name + " from Session");
        //if (checkParameterName("gstag:"+bean.getName())) {
        String[] values = request.getParameterValues("gstag:" + bean.getName());
        //if (values.length>0) {
        System.out.println("Updated bean: " + bean.getName());
        bean.update(values);
        session.setAttribute(name, bean);
        //}
        //}
        return bean;
    }

    public CheckBoxBean getCheckBox(String name) {
        CheckBoxBean checkbox = (CheckBoxBean) getBean(name, request);
        if (checkParameterName("gstag:" + name)) {
            checkbox.setSelected(true);
        } else {
            checkbox.setSelected(false);
        }
        return checkbox;
    }

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.
     * @param name name of the bean
     * @return updated elementbean
     */
    public Object getElementBean(String name) {
        PortletRequest request = event.getPortletRequest();
        return getElementBean(name, request);
    }

    /**
     * Prints out all request parameters (debug)
     * @param req paremter of this request will be printed out
     */
    public void printRequestParameter(PortletRequest req) {
        System.out.println("\n\n show request params\n--------------------\n");
        Enumeration enum = req.getParameterNames();
        while (enum.hasMoreElements()) {
            String name = (String) enum.nextElement();
            System.out.println("name :" + name);
            String values[] = req.getParameterValues(name);
            if (values.length == 1) {
                String pval = values[0];
                if (pval.length() == 0) {
                    pval = "no value";
                }
                System.out.println(" value : " + pval);
            } else {
                System.out.println(" value :");
                for (int i = 0; i < values.length; i++) {
                    System.out.println("            - " + values[i]);
                }

            }
        }
        System.out.println("--------------------\n");
    }
}
