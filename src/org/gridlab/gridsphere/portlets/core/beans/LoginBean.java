/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.portlet.*;

public class LoginBean {

    private PortletURI loginURI;

    public LoginBean() {}

    public String getLoginURI(PortletRequest request, PortletResponse response) {
        loginURI = response.createURI();
        if (loginURI == null) System.err.println("loginURI is null");
        //System.err.println(settings.getConcretePortletID());
        // set login method
        //DefaultPortletAction action = new DefaultPortletAction(PortletAction.LOGIN);
        //action.addParameter("portletid", settings.getConcretePortletID());
        /*
        loginURI.addAction(action);
        return loginURI.toString();
        */
        return "";
    }
}
