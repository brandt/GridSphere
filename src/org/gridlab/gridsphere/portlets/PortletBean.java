/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.tags.event.FormEvent;
import org.gridlab.gridsphere.tags.event.impl.FormEventImpl;

import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class PortletBean {

    protected PortletConfig config = null;
    protected PortletContext context = null;
    protected PortletRequest request = null;
    protected PortletResponse response = null;
    protected PortletLog log = null;
    protected User user = null;
    protected PortletSession session = null;
    protected ActionEvent actionEvent = null;
    protected PortletAction actionPerformed = null;
    protected String page = null;
    protected String title = null;
    protected boolean isFormInvalid = false;
    protected String formInvalidMessage = null;

    public PortletBean() {
    }

    public PortletBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        init(config, request, response);
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

    public String getPortletUserID() {
        return this.user.getID();
    }

    public String getPortletUserName() {
        return this.user.getUserName();
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

    public PortletURI getPortletActionURI(String action) {
        PortletURI actionURI = this.response.createReturnURI();
        actionURI.addAction(new DefaultPortletAction(action));
        return actionURI;
    }

    public PortletLog getPortletLog() {
        return this.log;
    }

    public ActionEvent getActionEvent() {
        return this.actionEvent;
    }

    public void setActionEvent(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
        PortletAction action = actionEvent.getAction();
    }

    public String getActionMethodName() {
        String actionMethodName = null;
        // If action event not set, return null
        if (this.actionEvent == null) {
            return null;
        }
        // If a submit button was presssed, the action method
        // we call is the the name of the pressed button
        FormEvent form = new FormEventImpl(this.actionEvent);
        actionMethodName = form.getSubmitButtonName();
        // If no submit button was pressed, then the action
        // method we call is the name of the portlet action
        if (actionMethodName == null) {
            DefaultPortletAction action = (DefaultPortletAction)actionEvent.getAction();
            actionMethodName = action.getName();
        }
        return actionMethodName;
    }

    public PortletAction getActionPerformed() {
        return this.actionPerformed;
    }

    public void setActionPerformed(PortletAction action) {
        if (this.log.isDebugEnabled()) {
            if (action instanceof DefaultPortletAction) {
                this.log.debug("Setting actionPerformed to " + ((DefaultPortletAction)action).getName());
            }
        }
        this.actionPerformed = action;
    }

    public String getActionPerformedName() {
        if (this.actionPerformed != null && this.actionPerformed instanceof DefaultPortletAction) {
            return ((DefaultPortletAction)this.actionPerformed).getName();
        }
        return "";
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

    public void doViewAction(ActionEvent actionEvent)
            throws PortletException {
        this.log.debug("Entering doAction(ActionEvent)");
        if (actionEvent == null) {
            this.log.debug("No action provided!");
            doErrorUndefinedAction();
        } else {
            // Save action event
            setActionEvent(actionEvent);
            // Get action method to perform
            String methodName = getActionMethodName();
            this.log.debug("Performing method " + methodName);
            System.out.println("Performing method " + methodName);
            // Invoke action method
            invokeActionMethod(methodName);
        }
        this.log.debug("Exiting doAction(ActionEvent)");
    }

    public void doViewAction(PortletAction action)
            throws PortletException {
        this.log.debug("Entering doAction(PortletAction)");
        // If action is not specified do error undefined action
        if (action == null) {
            this.log.debug("No action provided!");
            doErrorUndefinedAction();
        } else {
            // Set action performed (even if it fails below)
            setActionPerformed(action);
            // Get name of action peformed
            String actionName = getActionPerformedName();
            // Invoke action
            invokeActionMethod(actionName);
        }
        this.log.debug("Exiting doAction(PortletAction)");
    }

    private void invokeActionMethod(String methodName)
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
            doErrorUndefinedAction();
        } catch (IllegalAccessException e) {
            this.log.error("Error invoking action method", e);
            // If action is not illegal do error undefined action
            doErrorUndefinedAction();
        } catch (InvocationTargetException e) {
            this.log.error("Error invoking action method", e);
            // If action is not illegal do error undefined action
            doErrorUndefinedAction();
        }
    }

    public void doDefaultViewAction()
            throws PortletException {
    }

    public void doErrorUndefinedAction()
            throws PortletException {
        setTitle("Error: Undefined Action");
        String errorMessage = "Attempt to access undefined portlet action "
                            + this.getClass().getName()
                            + "."
                            + getActionMethodName()
                            + "()";
        this.log.error(errorMessage);
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
