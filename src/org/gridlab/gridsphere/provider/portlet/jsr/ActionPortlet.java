/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portlet.jsr;

import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.impl.ActionFormEventImpl;
import org.gridlab.gridsphere.provider.event.jsr.impl.RenderFormEventImpl;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletResponse;
import javax.portlet.ActionResponse;
import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * An <code>ActionPortlet</code> provides an abstraction on top of
 * <code>AbstractPortlet</code> to develop portlets under the action provider model.
 */
public class ActionPortlet extends GenericPortlet {

    public static PortletLog log = SportletLog.getInstance(ActionPortlet.class);
    public static String ACTION_PORTLET_ERROR = "org.gridlab.gridsphere.provider.ACTION_PORTLET_ERROR";
    public static String ACTION_PORTLET_PAGE = "org.gridlab.gridsphere.provider.ACTION_PORTLET_PAGE";

    // Default error page
    protected String ERROR_PAGE = "doError";

    // Default VIEW mode
    protected String DEFAULT_VIEW_PAGE = "view.jsp";

    // Default EDIT mode
    protected String DEFAULT_EDIT_PAGE = "edit.jsp";

    // Default HELP mode
    protected String DEFAULT_HELP_PAGE = "help.jsp";

    // Default CONFIGURE mode
    protected String DEFAULT_CONFIGURE_PAGE = "configure.jsp";

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
    }

    protected void setFileDownloadEvent(PortletRequest req, String fileName, String path) {
        req.setAttribute(SportletProperties.FILE_DOWNLOAD_NAME, fileName);
        req.setAttribute(SportletProperties.FILE_DOWNLOAD_PATH, path);
    }
    /**
     * Sets the next display state. The state specified may be either a JSP or it can
     * be another method name to invoke.
     *
     * @param request the <code>Portletrequest</code>
     * @param state the next display state
     */
    protected void setNextState(PortletRequest request, String state) {
        String id = getUniqueId();
        request.getPortletSession(true).setAttribute(id + ".state", state);
        log.debug("in ActionPortlet in setNextState: setting state to " + state);
    }

    /**
     * Returns the next display state. The state specified may be either a JSP or it can
     * be another method name to invoke.
     *
     * @param request the <code>PortletRequest</code>
     * @return the next display state, either the JSP to include or a method to invoke
     */
    protected String getNextState(PortletRequest request) {
        String id = getUniqueId();
        String state = (String)request.getPortletSession(true).getAttribute(id+".state");
        if (state == null) {
            state = DEFAULT_VIEW_PAGE;
        } else {
            log.debug("in ActionPortlet: in getNextState: a page has been set to:" + state);
        }
        return state;
    }

    /**
     * Returns the title to display in the portlet
     * Doesn't work since title rendering occurs before title is set!
     *
     * @param request the <code>PortletRequest</code>
     * @return the title to display in the portlet
     */
    public String getNextTitle(PortletRequest request) {
        String id = getUniqueId();
        log.debug("ActionPortlet in getNextTitle: setting title attribute " + id+".title");
        String title = (String)request.getAttribute(id+".title");
        if (title == null) {
            Locale locale = request.getLocale();
            ResourceBundle rb = this.getPortletConfig().getResourceBundle(locale);
            title = rb.getString("java.portlet.title");
            log.debug("Printing default title: " + title);
        }
        log.debug("next title= " +title);
        return title;
    }

    /**
     * Sets the title to display in the portlet
     * Doesn't work since title rendering occurs before title is set!
     *
     * @param request the <code>PortletRequest</code>
     * @param title the title display in the portlet
     */
    public void setNextTitle(PortletRequest request, String title) {
        this.log.debug("Setting title to " + title);
        String id = getUniqueId();
        //System.err.println("in setNextT: in attribute " + id + ".title");
        request.setAttribute(id + ".title", title);
    }

    /**
     * Sets the error to display in the portlet
     *
     * @param request the <code>PortletRequest</code>
     * @param error the error text to display in the portlet
     */
    protected void setNextError(PortletRequest request, String error) {
        String id = getUniqueId();
        request.setAttribute(id + ".error", error);
    }

    /**
     * Returns true of an error has occured
     *
     * @param request the <code>PortletRequest</code>
     * @return true if an error has occurred, false otherwise
     */
    protected boolean hasError(PortletRequest request) {
        String id = getUniqueId();
        return (request.getAttribute(id+".error") == null) ? false : true;
    }

    /**
     * Return the error to display in the portlet
     *
     * @param request the <code>PortletRequest</code>
     * @return the error text to display in the portlet
     */
    protected String getNextError(PortletRequest request) {
        String id = getUniqueId();
        String error = (String)request.getAttribute(id+".error");
        return error;
    }

    /**
     * Sets the tag beans obtained from the FormEvent. Used internally and should not
     * normally need to be invoked by portlet developers.
     *
     * @param request the <code>PortletRequest</code>
     * @param tagBeans a <code>Map</code> containing the portlet UI visual beans
     */
    protected void setTagBeans(PortletRequest request, Map tagBeans) {
        String id = getUniqueId();
        request.setAttribute(id + ".form", tagBeans);
    }

    /**
     * Returns the tag beans obtained from the FormEvent. Used internally and should not
     * normally need to be invoked by portlet developers.
     *
     * @param request the <code>PortletRequest</code>
     * @return the visual beans
     */
    protected Map getTagBeans(PortletRequest request) {
        String id = getUniqueId();
        Map tagBeans = (Map)request.getAttribute(id+".form");
        return tagBeans;
    }

    /**
     * Uses the action name obtained from the <code>ActionEvent</code> to invoke the
     * appropriate portlet action method.
     *
     * @param actionRequest the <code>ActionRequest</code>
     * @param actionResponse the <code>ActionResponse</code>
     *
     * @throws PortletException if a portlet exception occurs
     */
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
        log.debug("in ActionPortlet: processAction\t\t\t");
        DefaultPortletAction action = (DefaultPortletAction) actionRequest.getAttribute(SportletProperties.ACTION_EVENT);
        ActionFormEvent formEvent = new ActionFormEventImpl(action, actionRequest, actionResponse);

        Class[] parameterTypes = new Class[] { ActionFormEvent.class };
        Object[] arguments = new Object[] { formEvent };

        String methodName = formEvent.getAction().getName();

        log.debug("method name to invoke: " + methodName);

        doAction(actionRequest, actionResponse, methodName, parameterTypes, arguments);

        formEvent.store();
        setTagBeans(actionRequest, formEvent.getTagBeans());
        if (hasError(actionRequest)) {
            setNextState(actionRequest, ERROR_PAGE);
        }
    }

    /**
     * Invokes the appropriate portlet action method based on the portlet action received
     *
     * @param request the portlet request
     * @param response the portlet response
     * @param methodName  the method name to invoke
     * @param parameterTypes the method parameters
     * @param arguments the method arguments
     * @throws PortletException if an error occurs during the method invocation
     */
    protected void doAction(PortletRequest request, PortletResponse response,
                            String methodName,
                            Class[] parameterTypes,
                            Object[] arguments) throws PortletException {

        // Get object and class references
        Object thisObject = (Object)this;
        Class thisClass = this.getClass();
        // Call method specified by action name
        try {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Getting action method " + thisClass.getName() + "." + methodName + "()");
            }

            Method method = thisClass.getMethod(methodName, parameterTypes);
            this.log.debug("Invoking action method: " + methodName);

            method.invoke(thisObject, arguments);
        } catch (NoSuchMethodException e) {
            String error = "No such method: " + methodName + "\n" + e.getMessage();
            this.log.error(error, e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction(request, error);
        } catch (IllegalAccessException e) {
            String error = "Error accessing action method: " + methodName + "\n" + e.getMessage();
            this.log.error(error, e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction(request, error);
        } catch (InvocationTargetException e) {
            String error = "Error invoking action method: " + methodName + "\n" + e.getMessage();
            this.log.error(error, e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction(request, error);
        }
    }

    /**
     * Sets an approriate error message to be displayed in the next render cycle
     *
     * @param req the portlet request
     * @param errorMessage the error message
     */
    public void doErrorInvalidAction(PortletRequest req, String errorMessage) {
        setNextTitle(req, "Error: Undefined Action");
        setNextError(req, errorMessage);
    }

    /**
     * Renders the supplied JSP page.
     *
     * @param request the portlet request
     * @param response the portlet response
     * @param jsp the JSP page to include
     */
    public void doViewJSP(RenderRequest request, RenderResponse response, String jsp)  {
        log.debug("Including JSP page:" + jsp);
        try {
            if (jsp.startsWith("/")) {
                getPortletConfig().getPortletContext().getRequestDispatcher(jsp).include(request, response);
            } else {
                getPortletConfig().getPortletContext().getRequestDispatcher("/jsp/" + jsp).include(request, response);
            }
        } catch (Exception e) {
            log.error("Unable to include resource : " + e.getMessage());
        }

    }

    /**
     * Uses #getNextState to either render a JSP or invoke the specified render action method
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {

        String id = getUniqueId();
        String state = (String)request.getAttribute(id+".state");
        if (state == null) {
            log.debug("in ActionPortlet: state is null-- setting to DEFAULT_VIEW_PAGE");
            setNextState(request, DEFAULT_VIEW_PAGE);
        }
        String next = getNextState(request);
        log.debug("in ActionPortlet: portlet id= " + id + " doView next page is= " + next);
        if (next.endsWith(".jsp"))  {
            doViewJSP(request, response, next);
        } else {
            Map tagBeans = getTagBeans(request);
            RenderFormEvent formEvent = new RenderFormEventImpl(request, response, tagBeans);
            Class[] paramTypes = new Class[] { RenderFormEvent.class };
            Object[] arguments = new Object[] { formEvent };

            doAction(request, response, next, paramTypes, arguments);
            formEvent.store();
            next = getNextState(request);
            log.debug("in doView: next page is= " + next);
            doViewJSP(request, response, next);

            if (hasError(request)) {
                PrintWriter out = response.getWriter();
                log.debug("hasError = true");
                String message = getNextError(request);
                out.println(message);
            }


        }
    }

    /**
     * Simply forwards to #doView
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("ActionPortlet: in doEdit");
        setNextState(request, DEFAULT_EDIT_PAGE);
        doView(request, response);
    }

    /**
     * Simply forwards to #doView
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doConfigure(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("ActionPortlet: in doConfigure");
        setNextState(request, DEFAULT_CONFIGURE_PAGE);
        doView(request, response);
    }

    /**
     * Simply forwards to #doView
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doHelp(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("ActionPortlet: in doHelp");
        setNextState(request, DEFAULT_HELP_PAGE);
        doView(request, response);
    }

    public void doError(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("in doError");
        PrintWriter out = response.getWriter();
        String message = getNextError(request);
        out.println(message);
    }

    protected String getLocalizedText(PortletRequest req, String key) {
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
        return bundle.getString(key);
    }

    public String getParameter(PortletRequest request, String param) {
        String value = request.getParameter(param);
        if (value == null)
            return "";
        else
            return value;
    }

    public String getParameter(PortletRequest request, String param, String defaultValue) {
        String value = request.getParameter(param);
        if (value == null)
            return defaultValue;
        else
            return value;
    }

    public String[] getParameterValues(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);

        if (values == null)
            return new String[0];
        else
            return values;
    }

    public int getParameterAsInt(PortletRequest request, String param) {
        return getParameterAsInt(request, param, 0);
    }

    public int getParameterAsInt(PortletRequest request, String param, int defaultValue) {
        String value = request.getParameter(param);
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

    public int[] getParameterValuesAsInt(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public long getParameterAsLng(PortletRequest request, String param) {
        return getParameterAsLng(request, param, 0);
    }

    public long getParameterAsLng(PortletRequest request, String param, long defaultValue) {
        String value = request.getParameter(param);
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

    public long[] getParameterValuesAsLng(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public float getParameterAsFlt(PortletRequest request, String param) {
        return getParameterAsFlt(request, param, (float)0.0);
    }

    public float getParameterAsFlt(PortletRequest request, String param, float defaultValue) {
        String value = request.getParameter(param);
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

    public float[] getParameterValuesAsFlt(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public double getParameterAsDbl(PortletRequest request, String param) {
        return getParameterAsDbl(request, param, 0.0);
    }

    public double getParameterAsDbl(PortletRequest request, String param, double defaultValue) {
        String value = request.getParameter(param);
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


    public double[] getParameterValuesAsDbl(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public boolean getParameterAsBool(PortletRequest request, String param) {
        String value = request.getParameter(param);
        if (value == null) return false;
        if (value.equals("")) return false;
        if (value.equals("true")) return true;
        return false;
    }

    public boolean[] getParameterValuesAsBool(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public Integer getParameterAsInteger(PortletRequest request, String param) {
        String value = request.getParameter(param);
        if (value == null)
            return (new Integer(0));
        if (value.equals("")) value = "0";
        try {
            return (new Integer(value));
        } catch (Exception e) {
            return (new Integer(0));
        }
    }

    public Integer[] getParameterValuesAsInteger(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public Float getParameterAsFloat(PortletRequest request, String param) {
        String value = request.getParameter(param);
        if (value == null)
            return (new Float(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Float(value));
        } catch (Exception e) {
            return (new Float(0.0));
        }
    }

    public Float[] getParameterValuesAsFloat(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public Double getParameterAsDouble(PortletRequest request, String param) {
        String value = request.getParameter(param);
        if (value == null)
            return (new Double(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Double(value));
        } catch (Exception e) {
            return (new Double(0.0));
        }
    }

    public Double[] getParameterValuesAsDouble(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public Boolean getParameterAsBoolean(PortletRequest request, String param) {
        String value = request.getParameter(param);
        if (value == null) return new Boolean(false);
        if (value.equals("")) return new Boolean(false);
        if (value.equals("true")) return new Boolean(true);
        return new Boolean(false);
    }

    public Boolean[] getParameterValuesAsBoolean(PortletRequest request, String param) {
        String values[] = request.getParameterValues(param);
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

    public List getParameterValuesAsList(PortletRequest request, String param) {
        // Create list for values
        List listValues = new Vector();
        // Get parameter values
        String paramValues[] = getParameterValues(request, param);
        for (int ii = 0; ii < paramValues.length; ++ii) {
            listValues.add(paramValues[ii]);
        }
        return listValues;
    }

    public Map getParameterValuesAsMap(PortletRequest request, String param) {
        // Create list for values
        HashMap mapValues = new HashMap();
        // Get parameter values
        String paramValues[] = getParameterValues(request, param);
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

    private String getUniqueId() {
        return this.getPortletConfig().getPortletName();
    }

    public PortletService createPortletService(Class serviceClass) throws PortletServiceException {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        return factory.createPortletService(serviceClass, null, true);
    }

}
