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
import java.util.Locale;

/**
 * An <code>ActionPortlet</code> provides an abstraction on top of
 * <code>AbstractPortlet</code> to develop portlets under the action provider model.
 *
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
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

    /**
     * Sets the next page to display. The page specified may be either a JSP or it can
     * be another method name to invoke.
     *
     * @param request the <code>Portletrequest</code>
     * @param page the next page to display
     */
    protected void setNextPresentation(PortletRequest request, String page) {
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".page", page);
    }

    /**
     * Returns the next page to display. The page specified may be either a JSP or it can
     * be another method name to invoke.
     *
     * @param request the <code>PortletRequest</code>
     * @return the next page to display, either the JSP to include or a method to invoke
     */
    protected String getNextPresentation(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
        String state = (String)request.getAttribute(id+".page");
        if (state == null) {
            log.debug("no page was set!!");
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
        } else {
            log.debug("a page has been set to:" + state);
        }
        return state;
    }

    /**
     * Returns the title to display in the portlet
     *
     * @param request the <code>PortletRequest</code>
     * @return the title to display in the portlet
     */
    public String getNextTitle(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
        String state = (String)request.getAttribute(id+".title");
        if (state == null) {
            Locale locale = request.getLocale();
            state = getPortletSettings().getTitle(locale, null);
        }
        return state;
    }

    /**
     * Sets the title to display in the portlet
     *
     * @param request the <code>PortletRequest</code>
     * @param title the title display in the portlet
     */
    public void setNextTitle(PortletRequest request, String title) {
        this.log.debug("Setting title to " + title);
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".title", title);
    }

    /**
     * Sets the error to display in the portlet
     *
     * @param request the <code>PortletRequest</code>
     * @param error the error text to display in the portlet
     */
    protected void setNextError(PortletRequest request, String error) {
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".error", error);
    }

    protected boolean hasError(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
        return (request.getAttribute(id+".error") == null) ? false : true;
    }

    protected String getNextError(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
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
        Map tagBeans = (Map)request.getAttribute(id+".form");
        return tagBeans;
    }

    /**
     * Uses the action name obtained from the <code>ActionEvent</code> to invoke the
     * appropriate portlet action method.
     *
     * @param event the <code>ActionEvent</code>
     * @throws PortletException if a portlet exception occurs
     */
    public void actionPerformed(ActionEvent event) throws PortletException {
        log.debug("in ActionPortlet: actionPerformed");
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
        if (hasError(req)) {
            setNextPresentation(req, ERROR_PAGE);
        }
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

                Method method = thisClass.getMethod(methodName, parameterTypes);
                this.log.debug("Invoking action method: " + methodName);

                method.invoke(thisObject, arguments);
            } catch (NoSuchMethodException e) {
                this.log.error("Error invoking action method: " + methodName, e);
                // If action is not illegal do error undefined action
                doErrorInvalidAction(request, methodName);
            } catch (IllegalAccessException e) {
                this.log.error("Error invoking action method: " + methodName, e);
                // If action is not illegal do error undefined action
                doErrorInvalidAction(request, methodName);
            } catch (InvocationTargetException e) {
                this.log.error("Error invoking action method: " + methodName, e);
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
        setNextError(req, errorMessage);
        this.log.error(errorMessage);
    }

    /**
     * Renders the supplied JSP page.
     *
     * @param request the portlet request
     * @param response the portlet response
     * @param jsp the JSP page to include
     */
    public void doViewJSP(PortletRequest request, PortletResponse response, String jsp)  {
        log.debug("Forward to JSP page:" + jsp);
        try {
            getPortletConfig().getContext().include("/jsp/" + jsp, request, response);
        } catch (Exception e) {
            log.error("Unable to include resource : " + e.getMessage());
        }

    }

    /**
     * Uses #getNextPresentation to either render a JSP or invoke the specified render action method
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws IOException if an I/O error occurs
     */
    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("In doView: ");
        String next = getNextPresentation(request);
        log.debug("in ActionPortlet:doView next page is= " + next);
        if (next.endsWith(".jsp"))  {
            doViewJSP(request, response, next);
        } else {
            Map tagBeans = getTagBeans(request);
            FormEvent formEvent = new NewFormEventImpl(request, response, tagBeans);
            Class[] paramTypes = new Class[] { FormEvent.class };
            Object[] arguments = new Object[] { formEvent };
            doAction(request, response, next, paramTypes, arguments);
            formEvent.store();
            if (hasError(request)) {
                log.debug("hasError = true");
                doError(formEvent);
            } else {
            next = getNextPresentation(request);
            log.error("in doView: next page is= " + next);
            doViewJSP(request, response, next);
            }
        }
    }

    /**
     * Simply forwards to #doView
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws IOException if an I/O error occurs
     */
    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("in doEdit");
        doView(request, response);
    }

    /**
     * Simply forwards to #doView
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws IOException if an I/O error occurs
     */
    public void doConfigure(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("in doConfigure");
        doView(request, response);
    }

    /**
     * Simply forwards to #doView
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws IOException if an I/O error occurs
     */
    public void doHelp(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("in doHelp");
        doView(request, response);
    }

    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        log.debug("in doTitle");
        PrintWriter out = response.getWriter();
        String title = getNextTitle(request);
        out.println(title);
    }

    public void doError(FormEvent formEvent) throws PortletException, IOException {
        log.debug("in doError");
        PortletResponse response = formEvent.getPortletResponse();
        PortletRequest request = formEvent.getPortletRequest();
        PrintWriter out = response.getWriter();
        String message = getNextError(request);
        out.println(message);
    }

}
