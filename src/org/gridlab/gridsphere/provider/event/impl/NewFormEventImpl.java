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
        printTagBeans();
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

    public String getActionString() {
        return event.getActionString();
    }

    public CheckBoxBean getCheckBoxBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (CheckBoxBean)tagBeans.get(beanKey);
        }
        CheckBoxBean cb = new CheckBoxBean(request, beanId);
        tagBeans.put(beanKey, cb);
        return cb;
    }

    public RadioButtonBean getRadioButtonBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (RadioButtonBean)tagBeans.get(beanKey);
        }
        RadioButtonBean rb = new RadioButtonBean(request, beanId);
        tagBeans.put(beanKey, rb);
        return rb;
    }

    public TextFieldBean getTextFieldBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (TextFieldBean)tagBeans.get(beanKey);
        }
        TextFieldBean tf = new TextFieldBean(request, beanId);
        tagBeans.put(beanKey, tf);
        return tf;
    }

    public PasswordBean getPasswordBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (PasswordBean)tagBeans.get(beanKey);
        }
        PasswordBean pb = new PasswordBean(request, beanId);
        tagBeans.put(beanKey, pb);
        return pb;
    }

    public TextAreaBean getTextAreaBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (TextAreaBean)tagBeans.get(beanKey);
        }
        TextAreaBean ta = new TextAreaBean(request, beanId);
        tagBeans.put(beanKey, ta);
        return ta;
    }

    public FrameBean getFrameBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (FrameBean)tagBeans.get(beanKey);
        }
        FrameBean fb = new FrameBean(request, beanId);
        tagBeans.put(beanKey, fb);
        return fb;
    }

    public ErrorFrameBean getErrorFrameBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (ErrorFrameBean)tagBeans.get(beanKey);
        }
        ErrorFrameBean fb = new ErrorFrameBean(request, beanId);
        tagBeans.put(beanKey, fb);
        return fb;
    }

    public TextBean getTextBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        if (tagBeans.containsKey(beanKey)) {
            return (TextBean)tagBeans.get(beanKey);
        }
        TextBean tb = new TextBean(request, beanId);
        tagBeans.put(beanKey, tb);
        return tb;
    }

    public TagBean getNewTagBean(String beanId) {
        String beanKey = getBeanKey(beanId);
        return (TagBean)tagBeans.get(beanKey);
    }

    /**
     * Returns the name of the pressed submit button. To use this for form has to follow the convention
     * that the names of all submit-type buttons start with 'submit:' (and only those and no other elements)
     * @return name of the button which was pressed
     */
    public String getSubmitButtonName() {
        return null;
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

    public void printRequestAttributes() {
        System.out.println("\n\n show request attributes\n--------------------\n");
        Enumeration enum = request.getAttributeNames();
        while (enum.hasMoreElements()) {
            String name = (String) enum.nextElement();
            System.out.println("name :" + name);
        }
        System.out.println("--------------------\n");
    }

    /**
     * Parses all request parameters for visual beans.
     * A visual bean parameter has the following encoding:
     * ui_<visual bean element>_<bean Id>_name
     * where <visual bean element> can be one of the following:
     *
     * tf - TextFieldBean
     * rb - RadioButtonBean
     * cb - CheckBoxBean
     * tb - TextBean
     * ta - TextAreaBean
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
                //System.out.println("vb type :" + vb);
            }

            vbname = vbname.substring(idx+1);
            idx = vbname.indexOf("_");

            if (idx > 0) {
                beanId = vbname.substring(0, idx);
                //System.out.println("beanId :" + beanId);
            }

            name = vbname.substring(idx+1);
            System.out.println("name :" + name);

            String[] vals = request.getParameterValues(uiname);
            /*for (int i = 0; i < vals.length; i++) {
                System.err.println("vals[" + i +"] = " + vals[i]);
            }*/

            String beanKey = getBeanKey(beanId);
            if (vb.equals(TextFieldBean.NAME)) {
                log.debug("Creating a textfieldbean bean with id:" + beanId);
                TextFieldBean bean = new TextFieldBean(req, beanId);
                bean.setValue(vals[0]);
                log.debug("setting new value" + vals[0]);
                bean.setName(name);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(CheckBoxBean.NAME)) {
                log.debug("Creating a checkbox bean with id:" + beanId);
                CheckBoxBean bean = new CheckBoxBean(req, beanId);
                bean.setSelected(true);
                bean.setValue(vals[0]);
                bean.setName(name);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(RadioButtonBean.NAME)) {
                log.debug("Creating a radiobutton bean with id:" + beanId);
                RadioButtonBean bean = new RadioButtonBean(req, beanId);
                bean.setSelected(true);
                bean.setValue(vals[0]);
                bean.setName(name);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(PasswordBean.NAME)) {
                log.debug("Creating a passwordbean bean with id:" + beanId);
                PasswordBean bean = new PasswordBean(req, beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
            } else if (vb.equals(TextAreaBean.NAME)) {
                log.debug("Creating a textareabean bean with id:" + beanId);
                TextAreaBean bean = new TextAreaBean(req, beanId);
                bean.setValue(vals[0]);
                bean.setName(name);
                //System.err.println("putting a bean: " + beanId + "into tagBeans with name: " + name);
                tagBeans.put(beanKey, bean);
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

        printTagBeans();
    }

    protected String getBeanKey(String beanId) {
        String compId = (String)request.getAttribute(SportletProperties.COMPONENT_ID);
        return beanId + "_" + compId;
    }

    public void store() {
        Iterator it = tagBeans.values().iterator();
        while (it.hasNext()) {
            TagBean tagBean = (TagBean)it.next();
            //log.debug("storing bean id: " + tagBean.getBeanId());
            tagBean.store();
        }
        //printRequestAttributes();

    }

    public void printTagBeans() {
        log.debug("in print tag beans:");
        Iterator it = tagBeans.values().iterator();
        while (it.hasNext()) {
            TagBean tagBean = (TagBean)it.next();
            log.debug("tag bean id: " + tagBean.getBeanId());
        }
    }

}