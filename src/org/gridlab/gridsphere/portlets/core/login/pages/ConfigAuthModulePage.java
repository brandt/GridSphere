/**
 * GSISSHSubmissionPage.java
 * $Id$
 */

package org.gridlab.gridsphere.portlets.core.login.pages;

import org.gridlab.gridsphere.provider.gpdk.core.DefaultActionPage;
import org.gridlab.gridsphere.provider.gpdk.core.PageException;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBeanFactory;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.security.auth.modules.PasswordAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.LoginAuthModule;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public class ConfigAuthModulePage extends DefaultActionPage {

    /**
     * LoginPage constructor
     *
     * @param gotoPage defines the web page to forward control to
     */
    public ConfigAuthModulePage(String gotoPage) {
	    super(gotoPage);
    }

    public void initPageConfig(PortletConfig config) {
        log.debug("in initPageConfig");
    }

    /**
     * The GSISSHSubmissionPage execute method reads in form variables
     * and performs the appropiate job submission.
     *
     * @param event the <code>ActionEvent</code>
     *
     * @return a String containing the web page to forward control to
     *
     * @throws PortletException if error occurs during the page execution
     */
    public String actionPerformed(ActionEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();

        LoginService loginService = (LoginService)config.getContext().getService(LoginService.class, req.getUser());

        CheckBoxBean passCheck = (CheckBoxBean)TagBeanFactory.getTagBean(req, "passCheck");
        CheckBoxBean ldapCheck = (CheckBoxBean)TagBeanFactory.getTagBean(req, "passCheck");
        CheckBoxBean myproxyCheck = (CheckBoxBean)TagBeanFactory.getTagBean(req, "passCheck");

        List authModules = new ArrayList();
        Iterator it = loginService.getSupportedAuthModules().iterator();
        while (it.hasNext()) {
            LoginAuthModule authModule = (LoginAuthModule)it.next();
            String modName = authModule.getModuleName();
            if (modName.equals("PASSWORD_AUTH_MODULE")) {
                if (passCheck != null) {
                    if (passCheck.isSelected()) {
                        log.debug("adding PASSWORD_AUTH_MODULE");
                        authModules.add(authModule);
                    }
                }
            }
            if (modName.equals("LDAP_AUTH_MODULE")) {
                if (ldapCheck != null) {
                    if (ldapCheck.isSelected()) {
                        log.debug("adding LDAP_AUTH_MODULE");
                        authModules.add(authModule);
                    }
                }
            }
            if (modName.equals("MYPROXY_AUTH_MODULE")) {
                if (myproxyCheck != null) {
                    if (myproxyCheck.isSelected()) {
                        log.debug("adding MYPROXY_AUTH_MODULE");
                        authModules.add(authModule);
                    }
                }
            }
        }

        if (!authModules.isEmpty()) {
            loginService.setActiveAuthModules(authModules);
        }

        return gotoPage;
    }

}
