package org.gridsphere.provider.portlet.jsr.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.impl.ActionRequestImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.DefaultPortletAction;
import org.gridsphere.portletcontainer.DefaultPortletRender;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.FormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.event.jsr.impl.ActionFormEventImpl;
import org.gridsphere.provider.event.jsr.impl.RenderFormEventImpl;
import org.gridsphere.provider.portlet.jsr.mvc.descriptor.*;
import org.gridsphere.provider.portletui.beans.MessageBoxBean;
import org.gridsphere.provider.portletui.beans.MessageStyle;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import javax.portlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * An <code>ActionPortlet</code> provides an abstraction on top of
 * <code>AbstractPortlet</code> to develop portlets under the action provider model.
 */
public class ActionPortlet extends GenericPortlet {

    public static Log log = LogFactory.getLog(ActionPortlet.class);

    private Map viewPages = null;
    private Map editPages = null;
    private Map helpPages = null;
    private Map configPages = null;

    private String DEFAULT_VIEW_PAGE = null;
    private String DEFAULT_EDIT_PAGE = null;
    private String DEFAULT_HELP_PAGE = null;
    private String DEFAULT_CONFIG_PAGE = null;

    private String portletName = null;

    private String ACTION_PAGE_LABEL = "gs_Page";

    public void init(PortletConfig config) throws PortletException {
        super.init(config);

        // load in actionportlet.xml for pages

        String configFile = config.getInitParameter("ACTION_PORTLET_XML");
        if (configFile == null) configFile = "/WEB-INF/actionportlet.xml";

        portletName = config.getInitParameter("PORTLET_NAME");

        ActionPortletsDescriptor descriptor;

        try {
            String actionConfigDescriptor = config.getPortletContext().getRealPath(configFile);
            descriptor = new ActionPortletsDescriptor(actionConfigDescriptor);
        } catch (PersistenceManagerException e) {
            throw new PortletException("Unable to load actionportlet.xml", e);
        }

        viewPages = new HashMap();
        editPages = new HashMap();
        helpPages = new HashMap();
        configPages = new HashMap();
        ActionPortletCollection actionPortletCollection = descriptor.getActionPortletCollection();
        List actionPortletList = actionPortletCollection.getActionPortletList();
        Iterator it = actionPortletList.iterator();
        while (it.hasNext()) {
            ActionPortletDefinition def = (ActionPortletDefinition) it.next();
            String appName = def.getName();
            if (appName.equals(portletName)) {
                loadPages(def);
            }
        }

    }


    public void loadPages(ActionPortletDefinition def) {
        List pageList = def.getPageList();
        Iterator it = pageList.iterator();
        while (it.hasNext()) {
            ActionPageDefinition pageDef = (ActionPageDefinition) it.next();

            String pageName = pageDef.getName();
            String className = pageDef.getClassName();
            if (className != null) {
                try {
                    ActionPage page = (ActionPage) Class.forName(className).newInstance();
                    pageDef.setActionPage(page);
                    if (pageDef.getMode().equalsIgnoreCase(PortletMode.VIEW.toString())) {
                        if (DEFAULT_VIEW_PAGE == null) DEFAULT_VIEW_PAGE = pageName;
                        viewPages.put(pageName, pageDef);
                    } else if (pageDef.getMode().equalsIgnoreCase(PortletMode.EDIT.toString())) {
                        if (DEFAULT_EDIT_PAGE == null) DEFAULT_EDIT_PAGE = pageName;
                        editPages.put(pageName, pageDef);
                    } else if (pageDef.getMode().equalsIgnoreCase(PortletMode.HELP.toString())) {
                        if (DEFAULT_HELP_PAGE == null) DEFAULT_HELP_PAGE = pageName;
                        helpPages.put(pageName, pageDef);
                    } else if (pageDef.getMode().equalsIgnoreCase("CONFIG")) {
                        if (DEFAULT_CONFIG_PAGE == null) DEFAULT_CONFIG_PAGE = pageName;
                        configPages.put(pageName, pageDef);
                    }
                } catch (Exception e) {
                    log.error("Unable to create ActionPage!", e);
                }
            }
        }
    }

    protected void doDispatch(RenderRequest request,
                              RenderResponse response) throws PortletException, IOException {

        // if cid is null (true in non-GS portlet container) then use the portlet name
        //String cid = (String)request.getAttribute(SportletProperties.COMPONENT_ID);
        //if (cid == null) request.setAttribute(SportletProperties.COMPONENT_ID, getUniqueId());

        WindowState state = request.getWindowState();
        try {
            super.doDispatch(request, response);
        } catch (PortletException e) {
            if (!state.equals(WindowState.MINIMIZED)) {
                PortletMode mode = request.getPortletMode();
                if (mode.toString().equalsIgnoreCase("CONFIG")) {
                    doConfigure(request, response);
                    return;
                }
            }
            throw e;
        }

    }

    public String getNextResult(PortletRequest req) {
        PortletMode mode = req.getPortletMode();
        return req.getParameter(ACTION_PAGE_LABEL);
        //return (String)req.getPortletSession(true).getAttribute("org.gridsphere.provider.ActionPortlet.STATE." + mode.toString() + "." + portletName);
    }

    /*
    public void setNextResult(PortletRequest req, String result) {
        PortletMode mode = req.getPortletMode();
        req.getPortletSession(true).setAttribute("org.gridsphere.provider.ActionPortlet.STATE." + mode.toString() + "." + portletName, result);
    }*/

    public ActionPageDefinition getActionPageDef(PortletRequest req) {
        PortletMode mode = req.getPortletMode();
        return (ActionPageDefinition) req.getPortletSession(true).getAttribute("org.gridsphere.provider.ActionPortlet.PAGE." + mode.toString() + "." + portletName);
    }

    public void setActionPageDef(PortletRequest req, ActionPageDefinition def) {
        PortletMode mode = req.getPortletMode();
        req.getPortletSession(true).setAttribute("org.gridsphere.provider.ActionPortlet.PAGE." + mode.toString() + "." + portletName, def);
    }


    /**
     * Uses #getNextState to either render a JSP or invoke the specified render action method
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws javax.portlet.PortletException if a portlet exception occurs
     * @throws java.io.IOException            if an I/O error occurs
     */
    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String next = getNextResult(request);
        if (next == null) {
            log.debug("in ActionPortlet: state is null-- setting to DEFAULT_VIEW_PAGE");
            next = DEFAULT_VIEW_PAGE;
            //setNextResult(request, next);
        }
        ActionPageDefinition pageDef = (ActionPageDefinition) viewPages.get(next);
        doMode(request, response, "doView", pageDef);
    }

    /**
     * Uses #getNextState to either render a JSP or invoke the specified render action method
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws javax.portlet.PortletException if a portlet exception occurs
     * @throws java.io.IOException            if an I/O error occurs
     */
    public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String next = getNextResult(request);
        if (next == null) {
            log.debug("in ActionPortlet: state is null-- setting to DEFAULT_EDIT_PAGE");
            next = DEFAULT_EDIT_PAGE;
            //setNextResult(request, next);
        }
        ActionPageDefinition pageDef = (ActionPageDefinition) editPages.get(next);
        doMode(request, response, "doEdit", pageDef);
    }

    /**
     * Uses #getNextState to either render a JSP or invoke the specified render action method
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws javax.portlet.PortletException if a portlet exception occurs
     * @throws java.io.IOException            if an I/O error occurs
     */
    public void doHelp(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String next = getNextResult(request);
        if (next == null) {
            log.debug("in ActionPortlet: state is null-- setting to DEFAULT_HELP_PAGE");
            next = DEFAULT_HELP_PAGE;
            //setNextResult(request, next);
        }
        ActionPageDefinition pageDef = (ActionPageDefinition) helpPages.get(next);
        doMode(request, response, "doHelp", pageDef);
    }

    /**
     * Uses #getNextState to either render a JSP or invoke the specified render action method
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @throws javax.portlet.PortletException if a portlet exception occurs
     * @throws java.io.IOException            if an I/O error occurs
     */
    public void doConfigure(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String next = getNextResult(request);
        if (next == null) {
            log.debug("in ActionPortlet: state is null-- setting to DEFAULT_CONFIG_PAGE");
            next = DEFAULT_CONFIG_PAGE;
            //setNextResult(request, next);
        }
        ActionPageDefinition pageDef = (ActionPageDefinition) helpPages.get(next);
        doMode(request, response, "doConfigure", pageDef);
    }

    protected void doMode(RenderRequest request, RenderResponse response, String methodName, ActionPageDefinition pageDef) throws PortletException, IOException {
        String next = getNextResult(request);
        log.debug("in ActionPortlet: portlet id= " + getUniqueId() + " mode= " + request.getPortletMode() + " next page is= " + next);

        Map tagBeans = getTagBeans(request);
        DefaultPortletRender render = (DefaultPortletRender) request.getAttribute(SportletProperties.RENDER_EVENT);

        RenderFormEvent formEvent = new RenderFormEventImpl(render, request, response, tagBeans);
        Class[] paramTypes = new Class[]{RenderFormEvent.class};
        Object[] arguments = new Object[]{formEvent};

        setActionPageDef(request, pageDef);

        ActionPage page = pageDef.getActionPage();
        // if the page is defined invoke it
        if (page != null) {
            // Get object and class references
            Class pageClass = page.getClass();
            // Call method specified by action name
            try {
                Method method = pageClass.getMethod(methodName, paramTypes);
                method.invoke(this, arguments);
                StringBuffer sb = new StringBuffer();
                sb.append("Invoking portlet action ").append(pageClass.getName()).append("#").append(methodName);
            } catch (NoSuchMethodException e) {
                String error = "No such method: " + methodName + "\n" + e.getMessage();
                log.error(error, e);
                // If action is not illegal do error undefined action
                //doErrorInvalidAction(request, error);
                throw new PortletException(e);
            } catch (IllegalAccessException e) {
                String error = "Error accessing action method: " + methodName + "\n" + e.getMessage();
                log.error(error, e);
                // If action is not illegal do error undefined action
                //doErrorInvalidAction(request, error);
                throw new PortletException(e);
            } catch (InvocationTargetException e) {
                String error = "Error invoking action method: " + methodName;
                log.error(error, e);

                // JN request.setAttribute(SportletProperties.PORTLETERROR + request.getAttribute(SportletProperties.PORTLETID), e.getTargetException());
                //request.getPortletSession(true).setAttribute(SportletProperties.PORTLETERROR + request.getAttribute(SportletProperties.PORTLETID), e.getTargetException());

                // If action is not illegal do error undefined action
                //doErrorInvalidAction(request, error);
                throw new PortletException(e.getTargetException());
            }
        }

        formEvent.store();
        String state = pageDef.getState();

        // if JSP state is not null invoke it
        if (state != null) {
            try {
                if (page.getErrorMessage(request) != null) {
                    //getPortletContext().getRequestDispatcher(pageDef.getState()).include(request, response);
                    request.getPortletSession(true).removeAttribute(ActionPage.ERROR_PAGE);
                } else if (page.getSuccessMessage(request) != null) {
                    //getPortletContext().getRequestDispatcher(pageDef.getState()).include(request, response);
                    request.getPortletSession(true).removeAttribute(ActionPage.SUCCESS_PAGE);
                }
                getPortletContext().getRequestDispatcher(pageDef.getState()).include(request, response);
            } catch (Exception e) {
                // JN request.setAttribute(SportletProperties.PORTLETERROR + request.getAttribute(SportletProperties.PORTLETID), e);
                //request.getPortletSession(true).setAttribute(SportletProperties.PORTLETERROR + request.getAttribute(SportletProperties.PORTLETID), e);

                //log.error("Unable to include resource : " + e.getMessage());
                //setNextError(request, "Unable to include resource " + jsp);
                throw new PortletException(e);
            }
        }

        removeTagBeans(request);
        //removeNextTitle(request);
        //removeNextState(request);
    }


    /**
     * Sets the tag beans obtained from the FormEvent. Used internally and should not
     * normally need to be invoked by portlet developers.
     *
     * @param request  the <code>PortletRequest</code>
     * @param tagBeans a <code>Map</code> containing the portlet UI visual beans
     */
    protected void setTagBeans(PortletRequest request, Map tagBeans) {
        String id = getUniqueId();
        log.debug("saving tag beans in session " + id + ".beans");
        request.getPortletSession(true).setAttribute(id + ".beans", tagBeans);
    }

    protected void removeTagBeans(PortletRequest request) {
        String id = getUniqueId();
        log.debug("removing tag beans from session " + id + ".beans");
        request.getPortletSession(true).removeAttribute(id + ".beans");
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
        log.debug("getting tag beans from session " + id + ".beans");
        return (Map) request.getPortletSession(true).getAttribute(id + ".beans");
    }

    /**
     * Uses the action name obtained from the <code>ActionEvent</code> to invoke the
     * appropriate portlet action method.
     *
     * @param actionRequest  the <code>ActionRequest</code>
     * @param actionResponse the <code>ActionResponse</code>
     * @throws javax.portlet.PortletException if a portlet exception occurs
     */
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
        log.debug("in ActionPortlet: processAction\t\t\t");

        // if cid is null (true in non-GS portlet container) then use the portlet name
        //String cid = (String)actionRequest.getAttribute(SportletProperties.COMPONENT_ID);
        //if (cid == null) actionRequest.setAttribute(SportletProperties.COMPONENT_ID, getUniqueId());

        DefaultPortletAction action = (DefaultPortletAction) actionRequest.getAttribute(SportletProperties.ACTION_EVENT);
        // In non-GS container this will need to be created
        //TODO
        if (!(actionRequest instanceof ActionRequestImpl)) {
            //  action = GridSphereEventImpl.createAction(actionRequest);
            //System.err.println("action name" + action.getName());
        }
        ActionFormEvent formEvent = new ActionFormEventImpl(action, actionRequest, actionResponse);

        Class[] parameterTypes = new Class[]{ActionFormEvent.class};
        Object[] arguments = new Object[]{formEvent};

        String methodName = formEvent.getAction().getName();

        doAction(actionRequest, actionResponse, methodName, parameterTypes, arguments);
        //System.err.println("in processAction: befoire store cid=" + actionRequest.getAttribute(SportletProperties.COMPONENT_ID));

        setTagBeans(actionRequest, formEvent.getTagBeans());
    }

    /**
     * Invokes the appropriate portlet action method based on the portlet action received
     *
     * @param request        the portlet request
     * @param response       the portlet response
     * @param methodName     the method name to invoke
     * @param parameterTypes the method parameters
     * @param arguments      the method arguments
     * @throws javax.portlet.PortletException if an error occurs during the method invocation
     */
    protected void doAction(ActionRequest request, ActionResponse response,
                            String methodName,
                            Class[] parameterTypes,
                            Object[] arguments) throws PortletException {


        ActionPageDefinition pageDef = getActionPageDef(request);
        String result;
        // Get object and class references
        Class thisClass = this.getClass();
        // Call method specified by action name
        try {
            Method method = thisClass.getMethod(methodName, parameterTypes);
            result = (String) method.invoke(this, arguments);
            StringBuffer sb = new StringBuffer();
            sb.append("Invoking portlet action ").append(thisClass.getName()).append("#").append(methodName);

            log.info(sb.toString());
        } catch (NoSuchMethodException e) {
            String error = "No such method: " + methodName + "\n" + e.getMessage();
            log.error(error, e);
            // If action is not illegal do error undefined action
            //doErrorInvalidAction(request, error);
            throw new PortletException(e);
        } catch (IllegalAccessException e) {
            String error = "Error accessing action method: " + methodName + "\n" + e.getMessage();
            log.error(error, e);
            // If action is not illegal do error undefined action
            //doErrorInvalidAction(request, error);
            throw new PortletException(e);
        } catch (InvocationTargetException e) {
            String error = "Error invoking action method: " + methodName;
            log.error(error, e);

            // JN request.setAttribute(SportletProperties.PORTLETERROR + request.getAttribute(SportletProperties.PORTLETID), e.getTargetException());
            //request.getPortletSession(true).setAttribute(SportletProperties.PORTLETERROR + request.getAttribute(SportletProperties.PORTLETID), e.getTargetException());

            // If action is not illegal do error undefined action
            //doErrorInvalidAction(request, error);
            throw new PortletException(e.getTargetException());
        }

        List resultList = pageDef.getResultList();
        Iterator it = resultList.iterator();
        while (it.hasNext()) {
            ResultDefinition resultDef = (ResultDefinition) it.next();
            if (resultDef.getResult().equals(result)) {
                try {
                    response.setRenderParameter(ACTION_PAGE_LABEL, resultDef.getState());
                } catch (IllegalArgumentException e) {
                    // this might happen if a redirect was given
                }
                //setNextResult(request, resultDef.getState());
            }
        }
    }


    protected String getLocalizedText(PortletRequest req, String key) {
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
        return bundle.getString(key);
    }


    protected String getUniqueId() {
        //log.debug("setting unique cid: " + this.getPortletConfig().getPortletName());
        return portletName;
    }

    public PortletService createPortletService(Class serviceClass) throws PortletServiceException {
        return PortletServiceFactory.createPortletService(serviceClass, true);
    }

    protected void createErrorMessage(FormEvent evt, String text) {
        MessageBoxBean msgBox = evt.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        String msgOld = msgBox.getValue();
        msgBox.setValue((msgOld != null ? msgOld : "") + "\n" + text);
    }

    protected void createSuccessMessage(FormEvent evt, String text) {
        MessageBoxBean msg = evt.getMessageBoxBean("msg");
        msg.setValue(text);
        msg.setMessageType(MessageStyle.MSG_SUCCESS);
    }

}
