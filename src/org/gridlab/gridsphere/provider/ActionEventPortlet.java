/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.ActionEvent;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @deprecated This class is no longer supported
 */
public abstract class ActionEventPortlet extends AbstractPortlet {

    private String portletBeanName = null;
    private Class portletBeanClass = ActionEventHandler.class;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        getPortletLog().info("Exiting init()");
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        getPortletLog().info("Exiting initConcrete()");
    }

    protected String getPortletBeanName() {
        if (this.portletBeanName == null) {
            String className = portletBeanClass.getName();
            int index = className.lastIndexOf(".");
            if (index > 0 && index < className.length()) {
                className = className.substring(index+1);
            }
            return className;
        }
        return this.portletBeanName;
    }

    protected void setPortletBeanName(String name) {
        this.portletBeanName = name;
    }

    protected Class getPortletBeanClass() {
        return this.portletBeanClass;
    }

    protected void setPortletBeanClass(Class clazz) {
        this.portletBeanClass = clazz;
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        PortletLog log = getPortletLog();
        log.debug("Entering actionPerformed()");
        //'Get the portlet request and response
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        // Get instance of portlet bean
        ActionEventHandler portletBean = getPortletBean(request, response);
        // Perform action
        portletBean.performAction(event);
        log.debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PortletLog log = getPortletLog();
        log.debug("Entering doView()");
        // Get instance of user manager bean
        ActionEventHandler portletBean = getPortletBean(request, response);
        // Do default view action if no action performed
        if (portletBean.getActionPerformed() == null) {
            portletBean.doView();
        }
        // Get next page to display
        String nextPage = portletBean.getPage();
        log.debug("Next page = " + nextPage);
        // Include next page
        includeNextPage(request, response, nextPage);
        getPortletLog().debug("Exiting doView()");
    }

    public void doConfigure(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PortletLog log = getPortletLog();
        log.debug("Entering doConfig()");
        // Get instance of user manager bean
        ActionEventHandler portletBean = getPortletBean(request, response);
        // Do default config action if no action performed
        if (portletBean.getActionPerformed() == null) {
            portletBean.doConfigure();
        }
        // Get next page to display
        String nextPage = portletBean.getPage();
        log.debug("Next page = " + nextPage);
        // Include next page
        includeNextPage(request, response, nextPage);
        log.debug("Exiting doConfig()");
    }

    public void doEdit(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PortletLog log = getPortletLog();
        log.debug("Entering doEdit()");
        // Get instance of user manager bean
        ActionEventHandler portletBean = getPortletBean(request, response);
        // Do default edit action if no action performed
        if (portletBean.getActionPerformed() == null) {
            portletBean.doEdit();
        }
        // Get next page to display
        String nextPage = portletBean.getPage();
        log.debug("Next page = " + nextPage);
        // Include next page
        includeNextPage(request, response, nextPage);
        log.debug("Exiting doEdit()");
    }

    public void doHelp(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PortletLog log = getPortletLog();
        log.debug("Entering doHelp()");
        // Get instance of user manager bean
        ActionEventHandler portletBean = getPortletBean(request, response);
        // Do default help action if no action performed
        if (portletBean.getActionPerformed() == null) {
            portletBean.doHelp();
        }
        // Get next page to display
        String nextPage = portletBean.getPage();
        log.debug("Next page = " + nextPage);
        // Include next page
        includeNextPage(request, response, nextPage);
        log.debug("Exiting doHelp()");
    }

    public void doTitle(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PortletLog log = getPortletLog();
        log.debug("Entering doTitle()");
        // Get instance of user manager bean
        ActionEventHandler portletBean = getPortletBean(request, response);
        // Get next title to display
        String title = portletBean.getTitle();
        // Print the given title
        response.getWriter().println(title);
        log.debug("Exiting doTitle()");
    }

    private void includeNextPage(PortletRequest request, PortletResponse response, String nextPage)
            throws PortletException, IOException {
        // If not page to include, simply exit
        if (nextPage == null) {
            log.debug("No page to include");
            return;
        }
        // Include the given page
        getPortletConfig().getContext().include(nextPage, request, response);
    }

    private ActionEventHandler getPortletBean(PortletRequest request, PortletResponse response)
            throws PortletException {
        PortletLog log = getPortletLog();
        log.debug("Entering getPortletBean()");
        String portletBeanName = getPortletBeanName();
        log.debug("Getting " + portletBeanName);
        ActionEventHandler portletBean =
                (ActionEventHandler)request.getAttribute(portletBeanName);
        // Check if portlet bean already created
        if (portletBean == null) {
            // Create new instance of portlet bean if not created yet
            log.debug("Creating instance of " + portletBeanClass.getName());
            try {
                portletBean = (ActionEventHandler)this.portletBeanClass.newInstance();
            } catch (InstantiationException e) {
                String m = "Unable to instantiate class " + portletBeanClass.getName();
                log.error(m, e);
                throw new PortletException(m, e);
            } catch (IllegalAccessException e) {
                String m = "No public constructor for " + portletBeanClass.getName();
                log.error(m, e);
                throw new PortletException(m, e);
            }
            // Initialize portlet bean
            PortletConfig config = getPortletConfig();
            portletBean.init(config, request, response);
            // Add portlet bean to request attributes
            request.setAttribute(this.portletBeanName, portletBean);
        }
        log.debug("Exiting getPortletBean()");
        return portletBean;
    }
}
