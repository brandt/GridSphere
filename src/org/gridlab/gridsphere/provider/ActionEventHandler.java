/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.event.impl.FormEventImpl;
import org.gridlab.gridsphere.provider.ui.beans.*;

import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class ActionEventHandler {

    protected PortletConfig config = null;
    protected PortletContext context = null;
    protected PortletRequest request = null;
    protected PortletResponse response = null;
    protected PortletLog log = null;
    protected User user = null;
    protected PortletSession session = null;
    protected ActionEvent actionEvent = null;
    protected FormEvent formEvent = null;
    protected String actionEventMethodName = null;
    protected String page = null;
    protected String title = null;
    protected boolean isFormInvalid = false;
    protected String formInvalidMessage = null;

    public ActionEventHandler() {
    }

    public void init(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        this.config = config;
        this.context = config.getContext();
        this.request = request;
        this.response = response;
        this.user = request.getUser();
        this.session = request.getPortletSession();
        this.log = context.getLog();
    }

    public PortletConfig getPortletConfig() {
        return this.config;
    }

    public void setPortletConfig(PortletConfig config) {
        this.config = config;
    }

    public PortletRequest getPortletRequest() {
        return this.request;
    }

    public void setPortletRequest(PortletRequest request) {
        this.request = request;
    }

    public PortletResponse getPortletResponse() {
        return this.response;
    }

    public void setPortletResponse(PortletResponse response) {
        this.response = response;
    }

    public PortletSession getPortletSession() {
        return this.session;
    }

    public User getPortletUser() {
        return this.user;
    }

    public PortletService getPortletService(Class serviceClass)
            throws PortletException {
        PortletService service = (PortletService)this.session.getAttribute(serviceClass.getName());
        if (service == null) {
            service = this.context.getService(serviceClass, this.user);
            this.session.setAttribute(serviceClass.getName(), service);
        }
        return service;
    }

    public PortletLog getPortletLog() {
        return this.log;
    }

    public PortletURI createPortletActionURI(String action) {
        PortletURI actionURI = this.response.createReturnURI();
        actionURI.addAction(new DefaultPortletAction(action));
        return actionURI;
    }

    public ActionEvent getActionPerformed() {
        return this.actionEvent;
    }

    public void setActionPerformed(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
        // If action event not null figure out what kind of
        // action event it is, and then get the appropriate
        // name of action method to perform
        if (this.actionEvent == null) {
            this.log.error("No action event was provided!");
        } else {
            // If a submit button was presssed, the action method
            // we call is the the name of the pressed button
            FormEvent formEvent = new FormEventImpl(this.actionEvent);
            this.actionEventMethodName = formEvent.getSubmitButtonName();
            // If no submit button was pressed, then the action
            // method we call is the name of the portlet action
            if (this.actionEventMethodName == null) {
                this.log.debug("Action performed was an action link event");
                DefaultPortletAction action = (DefaultPortletAction)actionEvent.getAction();
                actionEventMethodName = action.getName();
            } else {
                this.log.debug("Action performed was a form event");
                this.formEvent = formEvent;
            }
        }
    }

    public boolean wasActionPerformed() {
        return (this.actionEvent != null);
    }

    public String getActionPerformedMethodName() {
        return this.actionEventMethodName;
    }

    public String getActionPerformedParameter(String paramName) {
        System.out.println("Normal getParameter(paramName)" + getParameter(paramName));
        System.out.println("Normal getParameter(gstag: paramName)" + getParameter("gstag:" + paramName));
        System.out.println("Normal getParameter(paramName)"
                + actionEvent.getAction().getParameters().get(paramName));
        System.out.println("Normal getParameter(gstag: paramName)"
                + actionEvent.getAction().getParameters().get("gstag:" + paramName));
        if (this.formEvent == null) {
            return getParameter(paramName);
        } else {
            return getParameter("gstag:" + paramName);
        }
        /***
        DefaultPortletAction action = this.actionEvent.getAction();
        Map parameters = action.getParameters();
        this.log.debug("Getting action event parameter " + paramName);
        String paramValue = (String)parameters.get(paramName);
        if (paramValue == null) {
            this.log.debug("Action event parameter not set");
            return "";
        }
        return paramValue;
        ***/
    }

    public TagBean getTagBean(String beanName) {
        // If form event is null, it was a link event
        if (this.formEvent == null) {
            return null;
        } else {
            // Then we return the tag bean with form event
            return (TagBean)this.formEvent.getTagBean(beanName);
        }
    }

    public ParamBean getActionParamBean(String beanName) {
        // Then we return the tag bean with form event
        return (ParamBean)getTagBean(beanName);
    }

    public HiddenFieldBean getHiddenFieldBean(String beanName) {
        // Then we return the tag bean with form event
        return (HiddenFieldBean)getTagBean(beanName);
    }

    public CheckBoxBean getCheckBoxBean(String beanName) {
        // Then we return the tag bean with form event
        return (CheckBoxBean)getTagBean(beanName);
    }

    public TextFieldBean getTextFieldBean(String beanName) {
        // Then we return the tag bean with form event
        return (TextFieldBean)getTagBean(beanName);
    }

    public TextAreaBean getTextAreaBean(String beanName) {
        // Then we return the tag bean with form event
        return (TextAreaBean)getTagBean(beanName);
    }

    public PasswordBean getPasswordBean(String beanName) {
        // Then we return the tag bean with form event
        return (PasswordBean)getTagBean(beanName);
    }

    public DropDownListBean getDropDownListBean(String beanName) {
        // Then we return the tag bean with form event
        return (DropDownListBean)getTagBean(beanName);
    }

    public TextBean createTextBeanAsActionLink(PortletURI portletURI) {
        TextBean textBean = new TextBean();
        String text = portletURI.toString();
        textBean.setText(text);
        return textBean;
    }

    public TableBean createTableBeanWithHeaders(List headerList) {
        TableBean tableBean = new TableBean();
        tableBean.setCssStyle("portlet-frame");
        TableRowBean tableRowBean = new TableRowBean();
        Iterator headerIterator = headerList.iterator();
        while (headerIterator.hasNext()) {
            String header = (String)headerIterator.next();
            TableCellBean tableCellBean = new TableCellBean();
            tableCellBean.setCssStyle("portlet-frame-header");
            tableCellBean.add(new TextBean(header));
            tableRowBean.add(tableCellBean);
        }
        tableBean.add(tableRowBean);
        return tableBean;
    }

    public TableBean createTableBeanWithMessage(String emptyMessage) {
        TableBean tableBean = new TableBean();
        tableBean.setCssStyle("portlet-frame");
        TableRowBean tableRowBean = new TableRowBean();
        TableCellBean tableCellBean = new TableCellBean();
        tableCellBean.setCssStyle("portlet-frame-text");
        tableCellBean.add(new TextBean(emptyMessage));
        tableRowBean.add(tableCellBean);
        tableBean.add(tableRowBean);
        return tableBean;
    }

    public TableCellBean createTableCellBean(TagBean tagBean) {
        TableCellBean cellBean = new TableCellBean();
        cellBean.setCssStyle("portlet-frame-text");
        cellBean.add(tagBean);
        return cellBean;
    }

    public void doConfigAction()
           throws PortletException {
    }

    public void doViewAction()
           throws PortletException {
    }

    public void doEditAction()
           throws PortletException {
    }

    public void doHelpAction()
           throws PortletException {
    }

    public void performAction(ActionEvent actionEvent)
            throws PortletException {
        this.log.debug("Entering doAction(ActionEvent)");
        if (actionEvent == null) {
            this.log.debug("No action provided!");
            doErrorInvalidAction();
        } else {
            // Save action event
            setActionPerformed(actionEvent);
            // Get action method to perform
            String methodName = getActionPerformedMethodName();
            this.log.debug("Performing method " + methodName);
            System.out.println("Performing method " + methodName);
            // Invoke action method
            performAction(methodName);
        }
        this.log.debug("Exiting doAction(ActionEvent)");
    }

    private void performAction(String methodName)
            throws PortletException {
        // Get object and class references
        Object thisObject = (Object)this;
        Class thisClass = this.getClass();
        // Call method specified by action name
        try {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Getting action method " + thisClass.getName() + "." + methodName + "()");
            }
            Method method = thisClass.getMethod(methodName, null);
            this.log.debug("Invoking action method");
            method.invoke(thisObject, null);
        } catch (NoSuchMethodException e) {
            this.log.error("Error invoking action method", e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction();
        } catch (IllegalAccessException e) {
            this.log.error("Error invoking action method", e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction();
        } catch (InvocationTargetException e) {
            this.log.error("Error invoking action method", e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction();
        }
    }

    public void doErrorInvalidAction()
            throws PortletException {
        setTitle("Error: Undefined Action");
        String errorMessage = "Attempt to invoke invalid portlet action "
                            + this.getClass().getName()
                            + "."
                            + getActionPerformedMethodName()
                            + "()";
        this.log.error(errorMessage);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.log.debug("Setting title to " + title);
        this.title = title;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.log.debug("Setting next page to " + page);
        this.page = page;
    }

    public boolean isFormInvalid() {
        return this.isFormInvalid;
    }

    public void setIsFormInvalid(boolean flag) {
        this.isFormInvalid = flag;
    }

    public String getFormInvalidMessage() {
        return this.formInvalidMessage;
    }

    public void setFormInvalidMessage(String message) {
        this.formInvalidMessage = message;
    }

    public Object getPortletRequestAttribute(String name) {
        return this.request.getAttribute(name);
    }

    public void setPortletRequestAttribute(String name, Object value) {
        this.request.setAttribute(name, value);
    }

    public String getParameter(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return "";
        else
            return value;
    }

    public String getParameter(String param, String defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        else
            return value;
    }

    public String[] getParameterValues(String param) {
        String values[] = this.request.getParameterValues(param);

        if (values == null)
            return new String[0];
        else
            return values;
    }

    public int getParameterAsInt(String param) {
        return getParameterAsInt(param, 0);
    }

    public int getParameterAsInt(String param, int defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Integer(value)).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public int[] getParameterValuesAsInt(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new int[0];
        } else {
            int objs[] = new int[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Integer(value)).intValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public long getParameterAsLng(String param) {
        return getParameterAsLng(param, 0);
    }

    public long getParameterAsLng(String param, long defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Long(value)).longValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public long[] getParameterValuesAsLng(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new long[0];
        } else {
            long objs[] = new long[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Long(value)).longValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public float getParameterAsFlt(String param) {
        return getParameterAsFlt(param, (float)0.0);
    }

    public float getParameterAsFlt(String param, float defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Float(value)).floatValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public float[] getParameterValuesAsFlt(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new float[0];
        } else {
            float objs[] = new float[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Float(value)).floatValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public double getParameterAsDbl(String param) {
        return getParameterAsDbl(param, (double)0.0);
    }

    public double getParameterAsDbl(String param, double defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Double(value)).doubleValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public double[] getParameterValuesAsDbl(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new double[0];
        } else {
            double objs[] = new double[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Double(value)).doubleValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public boolean getParameterAsBool(String param) {
        String value = this.request.getParameter(param);
        if (value == null) return false;
        if (value.equals("")) return false;
        if (value.equals("true")) return true;
        return false;
    }

    public boolean[] getParameterValuesAsBool(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new boolean[0];
        } else {
            boolean objs[] = new boolean[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                if (value.equals("")) objs[ii] = false;
                if (value.equals("true")) objs[ii] = true;
                objs[ii] = false;
            }
            return objs;
        }
    }

    public Integer getParameterAsInteger(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return (new Integer(0));
        if (value.equals("")) value = "0";
        try {
            return (new Integer(value));
        } catch (Exception e) {
            return (new Integer(0));
        }
    }

    public Integer[] getParameterValuesAsInteger(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Integer[0];
        } else {
            Integer objs[] = new Integer[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Integer(value));
                } catch (Exception e) {
                    objs[ii] = (new Integer(0));
                }
            }
            return objs;
        }
    }

    public Float getParameterAsFloat(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return (new Float(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Float(value));
        } catch (Exception e) {
            return (new Float(0.0));
        }
    }

    public Float[] getParameterValuesAsFloat(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Float[0];
        } else {
            Float objs[] = new Float[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Float(value));
                } catch (Exception e) {
                    objs[ii] = (new Float(0.0));
                }
            }
            return objs;
        }
    }

    public Double getParameterAsDouble(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return (new Double(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Double(value));
        } catch (Exception e) {
            return (new Double(0.0));
        }
    }

    public Double[] getParameterValuesAsDouble(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Double[0];
        } else {
            Double objs[] = new Double[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Double(value));
                } catch (Exception e) {
                    objs[ii] = (new Double(0.0));
                }
            }
            return objs;
        }
    }

    public Boolean getParameterAsBoolean(String param) {
        String value = this.request.getParameter(param);
        if (value == null) return new Boolean(false);
        if (value.equals("")) return new Boolean(false);
        if (value.equals("true")) return new Boolean(true);
        return new Boolean(false);
    }

    public Boolean[] getParameterValuesAsBoolean(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Boolean[0];
        } else {
            Boolean objs[] = new Boolean[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                if (value.equals("")) objs[ii] = new Boolean(false);
                if (value.equals("true")) objs[ii] = new Boolean(true);
                objs[ii] = new Boolean(false);
            }
            return objs;
        }
    }

    public List getParameterValuesAsList(String param) {
        // Create list for values
        List listValues = new Vector();
        // Get parameter values
        String paramValues[] = getParameterValues(param);
        for (int ii = 0; ii < paramValues.length; ++ii) {
            listValues.add(paramValues[ii]);
        }
        return listValues;
    }

    public Map getParameterValuesAsMap(String param) {
        // Create list for values
        HashMap mapValues = new HashMap();
        // Get parameter values
        String paramValues[] = getParameterValues(param);
        for (int ii = 0; ii < paramValues.length; ++ii) {
            String paramValue = paramValues[ii];
            int index = paramValue.indexOf(":");
            if (index < 0) {
                continue;
            }
            String key = paramValue.substring(0, index);
            String value = paramValue.substring(index+1,paramValue.length());
            mapValues.put(key, value);
        }
        return mapValues;
    }

    public List getParameterCheckBoxList(String param) {
        // Create list for values
        List listValues = new Vector();
        // Get parameter values
        String paramValues[] = getParameterValues(param);
        // If none, then return empty list
        if (paramValues.length == 0) {
            return listValues;
        }
        // Add first entry (See why below)
        listValues.add(paramValues[0]);
        // Return list if list has only one entry
        if (paramValues.length == 1) {
            return listValues;
        }
        // If list greater than 1 then iterate through list
        String firstParamValue = paramValues[0];
        for (int ii = 1; ii < paramValues.length; ++ii) {
            // But user could have selected add all check box
            // So we have to be careful to not try to add
            // the selection with the same value as would
            // be stored in the add all check box
            if (paramValues[ii].equals(firstParamValue)) {
                continue;
            }
            listValues.add(paramValues[ii]);
        }
        return listValues;
    }
}
