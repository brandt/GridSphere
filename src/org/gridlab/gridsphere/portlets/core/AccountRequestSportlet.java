/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.UserManagerService;

import javax.servlet.UnavailableException;
import java.io.IOException;

public class AccountRequestSportlet extends AbstractPortlet {

    private org.gridlab.gridsphere.services.user.UserManagerService userService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            userService = (UserManagerService) context.getService(UserManagerService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("in PortletInit: Service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException s) {
            throw new UnavailableException("in PortletInit: Service instance not found: " + s.toString());
        }
        System.err.println("init() in AccountRequest");
    }

    public void execute(PortletRequest request) {
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        // get parameters out of request and then store with UserManagerService
        DefaultPortletAction action;
        PortletAction _action = event.getAction();
        if (_action instanceof DefaultPortletAction) {
            action = (DefaultPortletAction) _action;
        } else {
            throw new PortletException("PortletAction is not an instance of  DefaultPortletAction");
        }

        PortletRequest request = event.getPortletRequest();
        AccountRequest accountRequest = null;
        if (action.getName().equals("submit")) {
            accountRequest = (AccountRequest) request.getAttribute("account");
            if (accountRequest != null) {
                userService.submitAccountRequest(accountRequest);
            }
        } else if (action.getName().equals("create")) {
            accountRequest = userService.createAccountRequest();
            request.setAttribute("account", accountRequest);
        }
        request.setAttribute(GridSphereProperties.ACTION, action);
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        DefaultPortletAction action;
        action = (DefaultPortletAction) request.getAttribute(GridSphereProperties.ACTION);
        if (action == null) {
            getPortletConfig().getContext().include("/jsp/accountrequest.jsp", request, response);
        }
        if (action.getName().equals("submit")) {
            getPortletConfig().getContext().include("/jsp/accountthankyou.jsp", request, response);
        }
        PortletURI portletURI = response.createURI();

        action = new DefaultPortletAction("create");
        portletURI.addAction(action);
    }

}
