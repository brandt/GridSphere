/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.locale;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.ActionEvent;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.Locale;

public class LocalePortlet extends AbstractPortlet {

    public static final String VIEW_JSP = "/jsp/locale/view.jsp";

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        String locale = (String)request.getPortletSession(true).getAttribute(User.LOCALE);
        Locale loc = new Locale(locale, "", "");
        request.setAttribute("curLoc", loc);
        getPortletConfig().getContext().include(VIEW_JSP, request, response);
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        String locale = event.getAction().getParameter("locale");
        PortletSession session = event.getPortletRequest().getPortletSession(true);
        session.setAttribute(User.LOCALE, locale);
    }
}
