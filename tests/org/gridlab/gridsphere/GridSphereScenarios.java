/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere;

import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.GridSphereServlet;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.apache.cactus.ServletTestCase;

import java.util.List;


/**
 * Simple class to build a TestSuite out of the individual test classes.
 */
public class GridSphereScenarios {

    protected static PortletServiceFactory factory = null;
    protected ServletTestCase testCase = null;
    protected GridSphereServlet gsServlet = null;
    protected static PortletLog log = SportletLog.getInstance(GridSphereScenarios.class);

    protected UserManagerService rootUserService = null;
    protected AccessControlManagerService rootACLService = null;
    protected User rootUser = null;

    public GridSphereScenarios(ServletTestCase testCase) {
        this.testCase = testCase;
    }

    public void setupGridSphereServlet() {
        gsServlet = new GridSphereServlet();
        testCase.assertNotNull(gsServlet);
        try {
            gsServlet.init(testCase.config);
        } catch (Exception e) {
            testCase.fail("Unable to perform init() of GridSphereServlet!");
        }
        try {
            gsServlet.initializeServices();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.fail("Unable to initialize GridSphere Portlet services!");
            log.error("Unable to initialize GridSphere Portlet services!", e);
        }
        factory = SportletServiceFactory.getInstance();
    }

    public void loginRoot() throws PortletServiceException, AuthorizationException {
        LoginService loginService = (LoginService) factory.createUserPortletService(LoginService.class, GuestUser.getInstance(), testCase.config, true);
        rootUser = loginService.login("root", "");
        testCase.assertNotNull(rootUser);
    }

    public void initRootServices() throws PortletServiceException {

        rootUserService = (UserManagerService) factory.createUserPortletService(UserManagerService.class, rootUser, testCase.config, true);
        rootACLService = (AccessControlManagerService) factory.createUserPortletService(AccessControlManagerService.class, rootUser, testCase.config, true);
    }

    public void createTestUsers() {
        // jason, michael should be users
        // oliver will be denied

        AccountRequest jasonRequest = rootUserService.createAccountRequest();
        jasonRequest.setUserID("jason");
        jasonRequest.setGivenName("Jason");
        jasonRequest.setPasswordValue("");
        jasonRequest.setPasswordValidation(false);

        AccountRequest michaelRequest = rootUserService.createAccountRequest();
        michaelRequest.setUserID("michael");
        michaelRequest.setGivenName("Michael");
        michaelRequest.setPasswordValue("");
        michaelRequest.setPasswordValidation(false);

        AccountRequest oliverRequest = rootUserService.createAccountRequest();
        oliverRequest.setUserID("oliver");
        oliverRequest.setGivenName("Oliver");
        oliverRequest.setPasswordValue("");
        oliverRequest.setPasswordValidation(false);
        User jason = null;
        User michael = null;
        User oliver = null;
        try {
            rootUserService.submitAccountRequest(jasonRequest);
            rootUserService.submitAccountRequest(michaelRequest);
            rootUserService.submitAccountRequest(oliverRequest);
            jason = rootUserService.approveAccountRequest(jasonRequest);
            michael = rootUserService.approveAccountRequest(michaelRequest);
            oliver = rootUserService.approveAccountRequest(oliverRequest);
        } catch (PortletServiceException e) {
            String msg = "Failed to setup users";
            //log.error(msg, e);
            testCase.fail(msg);
        }

        // test accessor methods
        List users = rootUserService.getUsers();
        testCase.assertTrue(users.contains(jason));
        testCase.assertTrue(users.contains(michael));
        testCase.assertTrue(users.contains(oliver));

        // test delete users
        rootUserService.deleteAccount(jason);
        rootUserService.deleteAccount(michael);
        rootUserService.deleteAccount(oliver);

        // test accessor methods
        users = rootUserService.getUsers();
        testCase.assertFalse(users.contains(jason));
        testCase.assertFalse(users.contains(michael));
        testCase.assertFalse(users.contains(oliver));
    }

}

