/** 
 * PageCommand.java
 * $Id$
 */

package org.gridlab.gridsphere.provider.gpdk.core;

import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.event.ActionEvent;

/**
 * A <code>PageCommand</code> utilizes the Command pattern to provide an execute method
 * invoked by a Portlet on a Page object
 */
public interface PageCommand {

    /**
     * Returns the JSP presentation page
     *
     * @return the JSP presentation page
     */
    public String getPage();

    /**
     * Allows the Page to initialize itself with the current portlet configuration
     *
     * @param config the PortletConfig
     */
    public void initPage(PortletConfig config);

    /**
     * Allows the concrete Page to initialize itself with the current portlet settings
     *
     * @param settings the PortletSettings
     */
    public void initConcretePage(PortletSettings settings);

    /**
     * Allows the concrete Page to destroy any resources created during its usage
     *
     * @param settings the PortletSettings
     */
    public void destroyConcretePage(PortletSettings settings);

    /**
     * Allows the Page to destroy any resources that have been created during its usage
     *
     * @param config the PortletConfig
     */
    public void destroyPage(PortletConfig config);

    /**
     * Execute defines a handler that is invoked by the servlet
     * when a request for the given page is received.
     *
     * @param evt the action event
     *
     * @return a String containing the web page to forward control to
     *
     * @throws CommandException if the action mapping Page is not found
     * @throws PortletException if an error occurs during page execution
     */
    public String actionPerformed(ActionEvent evt) throws PortletException, CommandException;

}
