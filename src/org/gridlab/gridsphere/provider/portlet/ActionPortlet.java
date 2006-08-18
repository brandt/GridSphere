/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portlet;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.event.impl.FormEventImpl;

import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * An <code>ActionPortlet</code> provides an abstraction on top of
 * <code>AbstractPortlet</code> to develop portlets under the action provider model.
 */
public class ActionPortlet extends AbstractPortlet {

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

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    protected void setFileDownloadEvent(PortletRequest req, String fileName, String path) {
        setFileDownloadEvent(req, fileName, path, false);
    }

    protected void setFileDownloadEvent(PortletRequest req, String fileName, String path, boolean deleteFile) {
        req.setAttribute(SportletProperties.FILE_DOWNLOAD_NAME, fileName);
        req.setAttribute(SportletProperties.FILE_DOWNLOAD_PATH, path);
        req.setAttribute(SportletProperties.FILE_DELETE, Boolean.valueOf(deleteFile));
    }

    /**
     * Sets the next display state. The state specified may be either a JSP or it can
     * be another method name to invoke.
     *
     * @param request the <code>Portletrequest</code>
     * @param state   the next display state
     */
    public void setNextState(PortletRequest request, String state) {
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".state", state);
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
        String id = request.getPortletSettings().getConcretePortletID();
        String state = (String) request.getAttribute(id + ".state");
        if (state == null) {
            state = DEFAULT_VIEW_PAGE;
            /*
            log.debug("no state was set!!");
            Portlet.Mode m = request.getMode();
            System.err.println("in getNEXTSTATE id=" + id + " mode=" + m);
            if (m.equals(Portlet.Mode.VIEW)) {
                state = DEFAULT_VIEW_PAGE;
            } else if (m.equals(Portlet.Mode.HELP)) {
                state = DEFAULT_HELP_PAGE;
            }  else if (m.equals(Portlet.Mode.CONFIGURE)) {
                state = DEFAULT_CONFIGURE_PAGE;
            } else if (m.equals(Portlet.Mode.EDIT)) {
                state = DEFAULT_EDIT_PAGE;
            } else {
                state = DEFAULT_VIEW_PAGE;
                log.error("in ActionPortlet: couldn't get portlet mode in getNextState()");
            }
            */
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
        String id = request.getPortletSettings().getConcretePortletID();
        log.debug("ActionPortlet in getNextTitle: setting title attribute " + id + ".title");
        String title = (String) request.getAttribute(id + ".title");
        if (title == null) {
            Locale locale = request.getLocale();
            title = getPortletSettings().getTitle(locale, null);
            log.debug("Printing default title: " + title);
        }
        log.debug("next title= " + title);
        return title;
    }

    /**
     * Sets the title to display in the portlet
     * Doesn't work since title rendering occurs before title is set!
     *
     * @param request the <code>PortletRequest</code>
     * @param title   the title display in the portlet
     */
    public void setNextTitle(PortletRequest request, String title) {
        log.debug("Setting title to " + title);
        String id = request.getPortletSettings().getConcretePortletID();
        //System.err.println("in setNextT: in attribute " + id + ".title");
        request.setAttribute(id + ".title", title);
    }

    /**
     * Sets the error to display in the portlet
     *
     * @param request the <code>PortletRequest</code>
     * @param error   the error text to display in the portlet
     */
    protected void setNextError(PortletRequest request, String error) {
        request.setAttribute("somerror", error);
    }

    /**
     * Returns true of an error has occured
     *
     * @param request the <code>PortletRequest</code>
     * @return true if an error has occurred, false otherwise
     */
    protected boolean hasError(PortletRequest request) {
        return (request.getAttribute("somerror") != null);
    }

    /**
     * Return the error to display in the portlet
     *
     * @param request the <code>PortletRequest</code>
     * @return the error text to display in the portlet
     */
    protected String getNextError(PortletRequest request) {
        return (String) request.getAttribute("somerror");
    }

    /**
     * Sets the tag beans obtained from the FormEvent. Used internally and should not
     * normally need to be invoked by portlet developers.
     *
     * @param request  the <code>PortletRequest</code>
     * @param tagBeans a <code>Map</code> containing the portlet UI visual beans
     */
    protected void setTagBeans(PortletRequest request, Map tagBeans) {
        String id = request.getPortletSettings().getConcretePortletID();
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
        String id = request.getPortletSettings().getConcretePortletID();
        return (Map) request.getAttribute(id + ".form");
    }

    /**
     * Uses the action name obtained from the <code>ActionEvent</code> to invoke the
     * appropriate portlet action method.
     *
     * @param event the <code>ActionEvent</code>
     * @throws org.gridlab.gridsphere.portlet.PortletException
     *          if a portlet exception occurs
     */
    public void actionPerformed(ActionEvent event) throws PortletException {
        log.debug("in ActionPortlet: actionPerformed");
        FormEvent formEvent = new FormEventImpl(event);

        Class[] parameterTypes = new Class[]{FormEvent.class};
        Object[] arguments = new Object[]{formEvent};
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        String methodName = event.getAction().getName();

        doAction(req, res, methodName, parameterTypes, arguments);
        formEvent.store();
        setTagBeans(req, formEvent.getTagBeans());
    }

    /**
     * Invokes the appropriate portlet action method based on the portlet action received
     *
     * @param request        the portlet request
     * @param response       the portlet response
     * @param methodName     the method name to invoke
     * @param parameterTypes the method parameters
     * @param arguments      the method arguments
     * @throws org.gridlab.gridsphere.portlet.PortletException
     *          if an error occurs during the method invocation
     */
    protected void doAction(PortletRequest request, PortletResponse response,
                            String methodName,
                            Class[] parameterTypes,
                            Object[] arguments) throws PortletException {

        // Get object and class references
        Object thisObject = (Object) this;
        Class thisClass = this.getClass();
        // Call method specified by action name
        try {

            Method method = thisClass.getMethod(methodName, parameterTypes);
            method.invoke(thisObject, arguments);
            StringBuffer sb = new StringBuffer();
            sb.append("Invoking portlet action ").append(thisClass.getName()).append("#").append(methodName);
            if (request.getUserPrincipal() != null) {
                sb.append(" user=").append(request.getUserPrincipal().getName());
                sb.append(" session id=").append(request.getPortletSession().getId());
            }
            if (request instanceof HttpServletRequestWrapper) {
                sb.append(" remote ip=").append(((HttpServletRequestWrapper)request).getRemoteAddr());
                sb.append(" user agent=").append(((HttpServletRequestWrapper)request).getHeader("user-agent"));
            }
            log.info(sb.toString());
        } catch (NoSuchMethodException e) {
            String error = "No such method: " + methodName + "\n" + e.getMessage();
            log.error(error, e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction(request, e);
            throw new PortletException(e);
        } catch (IllegalAccessException e) {
            String error = "Error accessing action method: " + methodName + "\n" + e.getMessage();
            log.error(error, e);
            // If action is not illegal do error undefined action
            doErrorInvalidAction(request, e);
            throw new PortletException(e);
        } catch (InvocationTargetException e) {
            String error = "Error invoking action method: " + methodName + "\n";
            log.error(error, e.getTargetException());
            // If action is not illegal do error undefined action
            doErrorInvalidAction(request, e.getTargetException());
            throw new PortletException(e);
        }
    }

    /**
     * Sets an approriate error message to be displayed in the next render cycle
     *
     * @param req          the portlet request
     * @param t      the error thrown
     */
    public void doErrorInvalidAction(PortletRequest req, Throwable t) {
        setNextTitle(req, "Error in action!");
        req.setAttribute(SportletProperties.PORTLETERROR + getPortletSettings().getConcretePortletID(), t);
        //setNextError(req, e);
    }

    /**
     * Renders the supplied JSP page.
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @param jsp      the JSP page to include
     */
    public void doViewJSP(PortletRequest request, PortletResponse response, String jsp) throws PortletException {
        log.debug("Including JSP page:" + jsp);
        try {
            if (jsp.startsWith("/")) {
                getPortletConfig().getContext().include(jsp, request, response);
            } else {
                getPortletConfig().getContext().include("/jsp/" + jsp, request, response);
            }
        } catch (Exception e) {
            log.error("Unable to include resource : ", e);
            doErrorInvalidAction(request, e);
            setNextError(request, "Unable to include resource " + jsp);
            throw new PortletException(e);
        }

    }

    /**
     * Uses #getNextState to either render a JSP or invoke the specified render action method
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws org.gridlab.gridsphere.portlet.PortletException
     *                             if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        String id = request.getPortletSettings().getConcretePortletID();
        String state = (String) request.getAttribute(id + ".state");
        if (state == null) {
            log.debug("in ActionPortlet: state is null-- setting to DEFAULT_VIEW_PAGE");
            setNextState(request, DEFAULT_VIEW_PAGE);
        }
        String next = getNextState(request);
        log.debug("in ActionPortlet: portlet id= " + id + " doView next page is= " + next);
        if (next.endsWith(".jsp")) {
            doViewJSP(request, response, next);
        } else {
            Map tagBeans = getTagBeans(request);
            FormEvent formEvent = new FormEventImpl(request, response, tagBeans);
            Class[] paramTypes = new Class[]{FormEvent.class};
            Object[] arguments = new Object[]{formEvent};

            doAction(request, response, next, paramTypes, arguments);
            formEvent.store();
            next = getNextState(request);
            log.debug("in doView: next page is= " + next);
            doViewJSP(request, response, next);

            /*
            if (hasError(request)) {
                PrintWriter out = response.getWriter();
                log.debug("hasError = true");
                String message = getNextError(request);
                out.println(message);
            }
            */

        }
    }

    /**
     * Simply forwards to #doView
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws org.gridlab.gridsphere.portlet.PortletException
     *                             if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("ActionPortlet: in doEdit");
        setNextState(request, DEFAULT_EDIT_PAGE);
        doView(request, response);
    }

    /**
     * Simply forwards to #doView
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws org.gridlab.gridsphere.portlet.PortletException
     *                             if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doConfigure(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("ActionPortlet: in doConfigure");
        setNextState(request, DEFAULT_CONFIGURE_PAGE);
        doView(request, response);
    }

    /**
     * Simply forwards to #doView
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws org.gridlab.gridsphere.portlet.PortletException
     *                             if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doHelp(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("ActionPortlet: in doHelp");
        setNextState(request, DEFAULT_HELP_PAGE);
        doView(request, response);
    }

    /**
     * Renders the title of the portlet
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws org.gridlab.gridsphere.portlet.PortletException
     *                             if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("in doTitle");
        PrintWriter out = response.getWriter();
        String title = getNextTitle(request);
        out.println(title);
    }

    /*
    public void doError(FormEvent formEvent) throws PortletException, IOException {
        log.debug("in doError");
        PortletResponse response = formEvent.getPortletResponse();
        PortletRequest request = formEvent.getPortletRequest();
        PrintWriter out = response.getWriter();
        String message = getNextError(request);
        out.println(message);
    }
    */

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
        return getParameterAsFlt(request, param, (float) 0.0);
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
            String value = paramValue.substring(index + 1, paramValue.length());
            mapValues.put(key, value);
        }
        return mapValues;
    }


}
