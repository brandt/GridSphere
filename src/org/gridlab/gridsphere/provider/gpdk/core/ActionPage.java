/**
 * Page.java
 * $Id$
 */

package org.gridlab.gridsphere.provider.gpdk.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.event.ActionEvent;

/**
 * A Page is the minimal represnetation for a dynamic portal page. 
 * It implemnts PageCommand and provides an execute a method that
 * gets called when the page is invoked by the servlet.
 */
public abstract class ActionPage implements PageCommand {
   
    protected static PortletLog log = SportletLog.getInstance(ActionPage.class);
    protected PortletConfig config;
    protected PortletSettings settings;
    protected String gotoPage;
    
    /**
     * Page constructor 
     */
    public ActionPage(String gotoPage) {
        this.gotoPage = gotoPage;
    }

    /**
     * Allows the Page to initialize itself with the current portlet configuration
     *
     * @param config the PortletConfig
     */
    abstract public void initPage(PortletConfig config);

    /**
     * Allows the concrete Page to initialize itself with the current portlet settings
     *
     * @param settings the PortletSettings
     */
    abstract public void initConcretePage(PortletSettings settings);

    /**
     * Allows the concrete Page to destroy any resources created during its usage
     *
     * @param settings the PortletSettings
     */
    abstract public void destroyConcretePage(PortletSettings settings);

    /**
     * Allows the Page to destroy any resources that have been created during its usage
     *
     * @param config the PortletConfig
     */
    abstract public void destroyPage(PortletConfig config);

    /**
     * Execute method is invoked by servlet
     *
     * @param evt
     *
     * @return a String containing a web page (html/jsp) to forward to
     *
     * @throws PortletException if error occurs during the page execution
     * @throws CommandException if a required request variable is not found
     */
    abstract public String actionPerformed(ActionEvent evt) throws PortletException, CommandException;

}

