/**
 * BasicPage.java
 * $Id$
 */

package org.gridlab.gridsphere.provider.gpdk.core;

import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletTitleListener;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.event.ActionListener;
import org.gridlab.gridsphere.event.MessageListener;
import org.gridlab.gridsphere.event.WindowListener;
import org.gridlab.gridsphere.provider.gpdk.core.ActionPage;
import org.gridlab.gridsphere.provider.gpdk.core.PageException;

/**
 * A BasicPage subclasses Page and simply returns the page provided in
 * the constructor when the execute method is invoked
 */
public class DefaultActionPage extends ActionPage {

    /**
     * BasicPage constructor
     *
     * @param gotoPage is the page to forward control to
     */
    public DefaultActionPage(String gotoPage) {
	    super(gotoPage);
    }

    public String getPage() {
        return gotoPage;
    }

    /**
     * Allows the Page to initialize itself with the current portlet configuration
     *
     * @param config the PortletConfig
     */
    public void initPage(PortletConfig config) {
        this.config = config;
    }

    /**
     * Allows the concrete Page to initialize itself with the current portlet settings
     *
     * @param settings the PortletSettings
     */
    public void initConcretePage(PortletSettings settings) {
        this.settings = settings;
    }

    /**
     * Allows the concrete Page to destroy any resources created during its usage
     *
     * @param settings the PortletSettings
     */
    public void destroyConcretePage(PortletSettings settings) {

    }

    /**
     * Allows the Page to destroy any resources that have been created during its usage
     *
     * @param config the PortletConfig
     */
    public void destroyPage(PortletConfig config) {

    }

    /**
     * Execute method returns gotoPage
     *
     * @param evt the action event
     * @return gotoPage the page to forward control to
     *
     * @throws PortletException if error occurs during the page execution
     */
    public String actionPerformed(ActionEvent evt) throws PortletException {
	    log.debug("in actionPerformed");
        return gotoPage;
    }

}

