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
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class NewFormEventImpl implements FormEvent {

    protected transient static PortletLog log = SportletLog.getInstance(NewFormEventImpl.class);

    protected ActionEvent event;
    protected PortletRequest request;
    protected PortletResponse response;
    protected Map tagBeans = null;

    public NewFormEventImpl(PortletRequest request, PortletResponse response, Map tagBeans) {
        this.request = request;
        this.response = response;
        this.tagBeans = tagBeans;
        // Unless tagBeans is null, don't recreate them
        if (tagBeans == null) {
            tagBeans = new HashMap();
            createTagBeans(request);
        }
    }

    public NewFormEventImpl(ActionEvent evt) {
        event = evt;
        request = evt.getPortletRequest();

        // Only create tag beans from request when initialized with action event
        createTagBeans(evt.getPortletRequest());
    }

    public PortletRequest getPortletRequest() {
        return request;
    }

    public Map getTagBeans() {
        return tagBeans;
    }

    public PortletResponse getPortletResponse() {
        return response;
    }

    public  DefaultPortletAction getAction() {
        return event.getAction();
    }

    public CheckBoxBean getCheckBoxBean(String beanId) {
        System.err.println("retrieving a bean from tagBeans with name: " + beanId);
        if (tagBeans.containsKey(beanId)) {
            System.err.println("found it");
            return (CheckBoxBean)tagBeans.get(beanId);
        }
        System.err.println("did not find it-- creating a new one--");
        CheckBoxBean cb = new CheckBoxBean(request, beanId);
        tagBeans.put(beanId, cb);
        return cb;
    }

    public TextFieldBean getTextFieldBean(String name) {
        if (tagBeans.containsKey(name)) {
            return (TextFieldBean)tagBeans.get(name);
        }
        TextFieldBean tf = new TextFieldBean(request, name);
        tagBeans.put(name, tf);
        return tf;
    }

    public PasswordBean getPasswordBean(String name) {
        if (tagBeans.containsKey(name)) {
            return (PasswordBean)tagBeans.get(name);
        }
        PasswordBean pb = new PasswordBean(request, name);
        tagBeans.put(name, pb);
        return pb;
    }

    public FrameBean getFrameBean(String beanId) {
        if (tagBeans.containsKey(beanId)) {
            return (FrameBean)tagBeans.get(beanId);
        }
        FrameBean fb = new FrameBean(request, beanId);
        tagBeans.put(beanId, fb);
        return fb;
    }

    public ErrorFrameBean getErrorFrameBean(String beanId) {
        if (tagBeans.containsKey(beanId)) {
            return (ErrorFrameBean)tagBeans.get(beanId);
        }
        ErrorFrameBean fb = new ErrorFrameBean(request, beanId);
        tagBeans.put(beanId, fb);
        return fb;
    }

    public TextBean getTextBean(String beanId) {
        if (tagBeans.containsKey(beanId)) {
            return (TextBean)tagBeans.get(beanId);
        }
        TextBean tb = new TextBean(request, beanId);
        tagBeans.put(beanId, tb);
        return tb;
    }

    public TagBean getNewTagBean(String beanId) {
        return (TagBean)tagBeans.get(beanId);
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


    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.  Returns null
     * if none object was found.
     * @param name name of the bean
     * @return updated elementbean
     */
    public Object getTagBean(String name) {
        return null;
    }


    /**
     * Prints out all request parameters (debug)
     */
    public void printRequestParameter() {
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

    /**
     * Parses all request parameters for visual beans.
     * A visual bean parameter has the following encoding:
     * ui_<visual bean element>_name
     * where <visual bean element> can be one of the following:
     *
     * tf - TextFieldBean
     * rb - RadioButtonBean
     * cb - CheckBoxBean
     * tb - TextBean
     *
     * @param req
     */
    protected void createTagBeans(PortletRequest req) {
        log.debug("in createTagBeans");


        if (tagBeans == null) tagBeans = new HashMap();
        printRequestParameter();

        Enumeration enum = request.getParameterNames();
        while (enum.hasMoreElements()) {

            String uiname = (String) enum.nextElement();
            String vb = "";
            String name = "";
            String beanId = "";

            if (!uiname.startsWith("ui")) continue;
            log.debug("found a tag bean: " + uiname);

            String vbname = uiname.substring(3);

            int idx = vbname.indexOf("_");

            if (idx > 0) {
                vb = vbname.substring(0, idx);
                System.out.println("vb type :" + vb);
            }

            vbname = vbname.substring(idx+1);
            idx = vbname.indexOf("_");

            if (idx > 0) {
                beanId = vbname.substring(0, idx);
                System.out.println("beanId :" + beanId);
            }

            name = vbname.substring(idx+1);
            System.out.println("name :" + name);

            String[] vals = request.getParameterValues(uiname);
            for (int i = 0; i < vals.length; i++) {
                System.err.println("vals[" + i +"] = " + vals[i]);
            }

            if (vb.equals(TextFieldBean.NAME)) {
                TextFieldBean bean = new TextFieldBean(req, name);
                bean.setValue(vals[0]);
                bean.setName(name);
                System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanId, bean);
            } else if (vb.equals(CheckBoxBean.NAME)) {
                log.debug("Creating a checkbox bean with id:" + beanId);
                CheckBoxBean bean = new CheckBoxBean(req, beanId);
                bean.setSelected(true);
                bean.setValue(vals[0]);
                bean.setName(name);
                System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanId, bean);
            } else if (vb.equals(RadioButtonBean.NAME)) {
                log.debug("Creating a radiobutton bean with id:" + beanId);
                RadioButtonBean bean = new RadioButtonBean(req, beanId);
                bean.setSelected(true);
                bean.setValue(vals[0]);
                bean.setName(name);
                System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanId, bean);
            } else if (vb.equals(PasswordBean.NAME)) {
                log.debug("Creating a passwordbean bean with id:" + beanId);
                PasswordBean bean = new PasswordBean(req, beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanId, bean);
            } else if (vb.equals(TextAreaBean.NAME)) {
                TextAreaBean bean = new TextAreaBean(req, name);
                bean.setValue(vals[0]);
                bean.setName(name);
                System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanId, bean);
            } else {
                log.error("unable to find suitable bean type for : " + uiname);
            }

            /*
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
            */

        }
    }

    public void store() {
        Iterator it = tagBeans.values().iterator();
        while (it.hasNext()) {
            TagBean tagBean = (TagBean)it.next();
            log.debug("storing bean id: " + tagBean.getBeanId());
            tagBean.store();
        }
    }


}