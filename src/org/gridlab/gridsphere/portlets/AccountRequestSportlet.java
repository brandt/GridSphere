/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.services.UserManagerService;
import org.gridlab.gridsphere.services.impl.AccountRequest;

import javax.servlet.UnavailableException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountRequestSportlet extends AbstractPortlet {

    private UserManagerService userService = null;
    private String ACTION = "REQUEST";

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            userService = (UserManagerService)context.getService(UserManagerService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("in PortletInit: Service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException s) {
            throw new UnavailableException("in PortletInit: Service instance not found: " + s.toString());
        }
        System.err.println("init() in AccountRequest");
    }

    public void execute(PortletRequest request) throws PortletException {
        // get parameters out of request and then store with UserManagerService
        AccountRequest accountRequest = (AccountRequest)request.getAttribute("AccountRequestSportlet.account");
        if (accountRequest != null) {
            userService.createAccountRequest(accountRequest);
            ACTION = "SUBMITTED";
        }
    }

    public void service(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        if (ACTION.equalsIgnoreCase("SUBMITTED")) {
            getPortletConfig().getContext().include("/jsp/accountthankyou.jsp", request, response);
        } else {
            getPortletConfig().getContext().include("/jsp/accountrequest.jsp", request, response);
        }
    }

}