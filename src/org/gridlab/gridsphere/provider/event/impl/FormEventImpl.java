/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 *
 * @version $Id$
 */

/*
 * <code>FormEventImpl</code> provides methods for getting data out of a portletrequest.
 */
package org.gridlab.gridsphere.provider.event.impl;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.ui.beans.*;
import org.gridlab.gridsphere.provider.ui.beans.TableBean;
import org.gridlab.gridsphere.provider.ui.beans.TableCellBean;
import org.gridlab.gridsphere.provider.ui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.ui.beans.TagBean;
import org.gridlab.gridsphere.provider.portletui.beans.ErrorFrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.PasswordBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;
import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.HiddenFieldBean;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

public class FormEventImpl implements FormEvent {

    protected transient static PortletLog log = SportletLog.getInstance(FormEventImpl.class);

    protected ActionEvent event;
    protected PortletRequest request;
    protected PortletResponse response;

    public FormEventImpl(PortletRequest request) {
        this.request = request;
    }

    public FormEventImpl(PortletRequest request, PortletResponse response) {
        this.request = request;
        this.response = response;
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

    public  String getActionString() {
        return event.getActionString();
    }

    public org.gridlab.gridsphere.provider.portletui.beans.TagBean getNewTagBean(String name) {
        return null;
    }


    /**
     * Returns the name of the pressed submit button. To use this for form has to follow the convention
     * that the names of all submit-type buttons start with 'submit:' (and only those and no other elements)
     * @return name of the button which was pressed
     */
    public String getSubmitButtonName() {
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

    private TagBean getBean(String name) {
        HttpSession session = request.getSession();
        log.debug("Try to get bean "+SportletProperties.PORTLETID+":"+request.getAttribute(SportletProperties.PORTLETID)+":"+name+" from session.");
        NameBean bean = (NameBean) session.getAttribute(SportletProperties.PORTLETID+":"+request.getAttribute(SportletProperties.PORTLETID)+":"+name);
        if (bean==null) {
            log.info("does not exists :"+SportletProperties.PORTLETID+":"+request.getAttribute(SportletProperties.PORTLETID)+":"+name);
        } else{
            log.info("Check the bean content:"+SportletProperties.PORTLETID+":"+request.getAttribute(SportletProperties.PORTLETID)+":"+name+" ->"+bean.toString());
            log.info("\n\n\n========"+bean.getName());
        }
        return bean;
    }

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.  Returns null
     * if none object was found.
     * @param name name of the bean
     * @return updated elementbean
     */
    public Object getTagBean(String name) {
        HttpSession session = request.getSession();
        NameBean bean = (NameBean) getBean(name);
        if (bean==null) {
            log.info("This bean does not exists in the session ("+name+")");
            return null;
        } else {
            //System.out.println("Getting Bean " + name + " from Session");
            //if (checkParameterName("gstag:"+bean.getName())) {
            String beanKey = SportletProperties.PORTLETID+":"+request.getAttribute(SportletProperties.PORTLETID)+":"+name;
            log.debug("Getting Bean '" + beanKey + "' from Session");
            if (bean instanceof TableBean) {
                // has to be that hack since the individal components of a table
                // are not stored by itself in a session, kind of different
                // later to modify all the methods to do recursion
                TableBean tbean = (TableBean)bean;
                Iterator it = tbean.iterator();
                while (it.hasNext()) {
                    TableRowBean trb = (TableRowBean)it.next();
                    Iterator rowit = trb.iterator();
                    while (rowit.hasNext()) {
                        TableCellBean tcb = (TableCellBean)rowit.next();
                        Iterator cellit = tcb.iterator();
                        while (cellit.hasNext()) {
                            BaseElementBean basebean = (BaseElementBean)cellit.next();
                            // check if we actually can update this (could be textbean which is not updatable at all)
                            // @TODO instanceof updatable
                            if (basebean instanceof NameBean) {
                                bean = (NameBean)basebean;
                                String[] values = request.getParameterValues("gstag:" + bean.getName());
                                log.debug("Updating table bean: " + bean.getName());
                                bean.update(values);
                            }
                        }
                    }
                }
                session.setAttribute(beanKey, tbean);
                return tbean;
            } else {
               // session.setAttribute(SportletProperties.PORTLETID+":"+request.getAttribute(SportletProperties.PORTLETID)+":"+name, bean);
                String paramval = "gstag:"+SportletProperties.COMPONENT_ID+":"+request.getAttribute(SportletProperties.COMPONENT_ID).toString()+":"+ bean.getName();
                String[] values = request.getParameterValues(paramval);
                log.debug("Updating bean: " + " name req: "+name+" bean name: "+bean.getName()+"("+paramval+")");
                bean.update(values);
                session.setAttribute(beanKey, bean);
                return bean;
            }
        }
    }

    public CheckBoxBean getCheckBox(String name) {
        CheckBoxBean checkbox = (CheckBoxBean) getBean(name);
        if (checkParameterName("gstag:" + name)) {
            checkbox.setSelected(true);
        } else {
            checkbox.setSelected(false);
        }
        return checkbox;
    }

    /**
     * Prints out all request parameters (debug)
     */
    public void printRequestParameters() {
        System.out.println("\n\n show request params\n--------------------\n");
        Enumeration enum = request.getParameterNames();
        while (enum.hasMoreElements()) {
            String name = (String) enum.nextElement();
            System.out.println("name :" + name);
            String values[] = request.getParameterValues(name);
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

    public void store() {}

    public CheckBoxBean getCheckBoxBean(String beanId) {
        return null;
    }

    public TextFieldBean getTextFieldBean(String beanId) {
        return null;
    }

    public PasswordBean getPasswordBean(String beanId) {
        return null;
    }

    public TextBean getTextBean(String name) {
        return null;
    }

    public FrameBean getFrameBean(String beanID) {
        return null;
    }

    public ErrorFrameBean getErrorFrameBean(String beanID) {
        return null;
    }

    public TextAreaBean getTextAreaBean(String beanId) {
        return null;
    }

    public FileInputBean getFileInputBean(String beanId) throws IOException {
        return null;
    }

    public ListBoxBean getListBoxBean(String beanId) {
        return null;
    }

    public HiddenFieldBean getHiddenFieldBean(String beanId) {
        return null;
    }

    public Map getTagBeans() {
        return null;
    }
}