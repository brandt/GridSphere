/**
 * GSISSHSubmissionPage.java
 * $Id$
 */

package org.gridlab.gridsphere.portlets.core.login.pages;

import org.gridlab.gridsphere.provider.gpdk.core.DefaultActionPage;
import org.gridlab.gridsphere.provider.gpdk.core.PageException;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBeanFactory;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlets.core.login.LoginPortlet;

/**
 *
 */
public class LoginPage extends DefaultActionPage {

    /**
     * LoginPage constructor
     *
     * @param gotoPage defines the web page to forward control to
     */
    public LoginPage(String gotoPage) {
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

        TextBean errorMsg = (TextBean)TagBeanFactory.getTagBean(req, "errorMsg");
        String errorKey = (String)req.getAttribute(LoginPortlet.LOGIN_ERROR_FLAG);
        if ((errorKey != null) && (errorMsg != null)) {
            errorMsg.setKey(errorKey);
            TagBeanFactory.storeTagBean(req, errorMsg);
        }

        return gotoPage;
    }

}
