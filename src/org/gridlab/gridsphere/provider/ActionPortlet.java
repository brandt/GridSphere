package org.gridlab.gridsphere.provider;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.provider.event.impl.NewFormEventImpl;
import org.gridlab.gridsphere.provider.event.FormEvent;

import javax.servlet.UnavailableException;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class ActionPortlet extends AbstractPortlet {

    protected static String ERROR_PAGE = "error.jsp";

    protected static String DEFAULT_VIEW_PAGE = "view.jsp";
    protected static String DEFAULT_EDIT_PAGE = "edit.jsp";
    protected static String DEFAULT_HELP_PAGE = "help.jsp";
    protected static String DEFAULT_CONFIGURE_PAGE = "configure.jsp";

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        // load pages config file here
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    protected void setNextPage(PortletRequest request, String method) {
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".page", method);
    }

    protected String getNextPage(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
        String state = (String)request.getAttribute(id+".page");
        if (state == null) {
            Portlet.Mode m = request.getMode();
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
                log.error("in ActionPortlet: couldn't get portlet mode in getNextpage()");
            }
        }
        return state;
    }

    public String getNextTitle(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
        String state = (String)request.getAttribute(id+".title");
        return state;
    }

    public void setNextTitle(PortletRequest request, String title) {
        this.log.debug("Setting title to " + title);
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".title", title);
    }

    protected void setTagBeans(PortletRequest request, Map tagBeans) {
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".form", tagBeans);
    }

    protected Map getTagBeans(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
        Map tagBeans = (Map)request.getAttribute(id+".form");
        return tagBeans;
    }

    public void actionPerformed(ActionEvent event) throws PortletException {

        FormEvent formEvent = new NewFormEventImpl(event);

        Class[] parameterTypes = new Class[] { FormEvent.class };
        Object[] arguments = new Object[] { formEvent };
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        String methodName = event.getAction().getName();

        log.debug("method name to invoke: " + methodName);

        doAction(req, res, methodName, parameterTypes, arguments);

        formEvent.store();
        setTagBeans(req, formEvent.getTagBeans());
    }

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

                //Class e = request.getClass();
                //Class e = PortletRequest.class;
                //showMethods(thisObject);

                //Class[] types = new Class[] { e };
                //Method method = thisClass.getMethod("yo", types);
                //Object[] arguments = new Object[] { request };
                //method.invoke(thisObject, arguments);
                Method method = thisClass.getMethod(methodName, parameterTypes);
                this.log.debug("Invoking action method");

                method.invoke(thisObject, arguments);
            } catch (NoSuchMethodException e) {
                this.log.error("Error invoking action method", e);
                // If action is not illegal do error undefined action
                doErrorInvalidAction(request, methodName);
            } catch (IllegalAccessException e) {
                this.log.error("Error invoking action method", e);
                // If action is not illegal do error undefined action
                doErrorInvalidAction(request, methodName);
            } catch (InvocationTargetException e) {
                this.log.error("Error invoking action method", e);
                // If action is not illegal do error undefined action
                doErrorInvalidAction(request, methodName);
            }
            // Store any error messages to request
            //this.errorMessageBean.store("errorMessage", this.request);
        }

    public void doErrorInvalidAction(PortletRequest req, String action)
            throws PortletException {
        setNextTitle(req, "Error: Undefined Action");
        String errorMessage = "Attempt to invoke invalid portlet action "
                            + this.getClass().getName()
                            + "."
                            + action
                            + "()";
        setNextPage(req, ERROR_PAGE);
        this.log.error(errorMessage);
    }

    public void doViewJSP(PortletRequest request, PortletResponse response, String jsp) throws PortletException, IOException {
        log.debug("Forward to JSP page:" + jsp);
        getPortletConfig().getContext().include("/jsp/" + jsp, request, response);
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        String next = getNextPage(request);
        log.debug("in doView: next page is= " + next);
        if (next.endsWith(".jsp"))  {
            doViewJSP(request, response, next);
        } else {
            Map tagBeans = getTagBeans(request);
            FormEvent formEvent = new NewFormEventImpl(request, response, tagBeans);
            Class[] paramTypes = new Class[] { FormEvent.class };
            Object[] arguments = new Object[] { formEvent };
            doAction(request, response, next, paramTypes, arguments);
            formEvent.store();

            next = getNextPage(request);
            log.error("in doView: next page is= " + next);
            doViewJSP(request, response, next);
        }
    }

    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        doView(request, response);
    }

    public void doConfigure(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        doView(request, response);
    }

    public void doHelp(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        doView(request, response);
    }


}

