/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.InvalidAccountRequestException;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.layout.*;

import javax.servlet.UnavailableException;
import java.util.*;
import java.io.IOException;

public class LayoutManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_JSP = "layout/view.jsp";

    public static final String CONFIGURE_JSP = "layout/configure.jsp";

    // Portlet services
    private LayoutManagerService layoutMgr = null;
    private UserManagerService userManagerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
            this.userManagerService = (UserManagerService)config.getContext().getService(UserManagerService.class);

        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }

        DEFAULT_VIEW_PAGE = "doRender";
        DEFAULT_HELP_PAGE = "layout/help.jsp";

    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void createNewTab(FormEvent event) throws PortletException, IOException {

        String tabName = event.getTextFieldBean("userTabTF").getValue();
        RadioButtonBean rb = event.getRadioButtonBean("colsRB");
        String rbtype = rb.getSelectedValue();

        int cols = Integer.valueOf(rbtype).intValue();
        PortletTabbedPane pane = layoutMgr.createUserTabbedPane(event.getPortletRequest(), cols, tabName);


        PortletTab tab = pane.getLastPortletTab();
       
        pane.save();
        PortletPage page = layoutMgr.getPortletPage(event.getPortletRequest());
        PortletTabbedPane mypane = page.getPortletTabbedPane();
        List tabs = mypane.getPortletTabs();
        tabs.add(0, tab);
        layoutMgr.reloadPage(event.getPortletRequest());
    }

    public void deleteTab(FormEvent event) throws IOException {
        String label = event.getAction().getParameter("tabid");

        PortletTabbedPane pane = layoutMgr.getUserTabbedPane(event.getPortletRequest());
        List tabs = pane.getPortletTabs();
        Iterator it = tabs.iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab)it.next();
            if (tab.getLabel().equals(label)) {
                it.remove();
            }
        }
        pane.save();
        PortletPage page = layoutMgr.getPortletPage(event.getPortletRequest());
        PortletTabbedPane mypane = page.getPortletTabbedPane();
        it = mypane.getPortletTabs().iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab)it.next();
            if (tab.getLabel().equals(label)) {
                it.remove();
            }
        }
        layoutMgr.reloadPage(event.getPortletRequest());
    }

    public void saveTab(FormEvent event) throws IOException {

        String lang = event.getPortletRequest().getLocale().getLanguage();
        String name = event.getPortletRequest().getParameter("myTF");
        String label = event.getAction().getParameter("tabid");

        PortletTabbedPane pane = layoutMgr.getUserTabbedPane(event.getPortletRequest());
        List tabs = pane.getPortletTabs();
        Iterator it = tabs.iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab)it.next();
            if (tab.getLabel().equals(label)) {
                tab.setTitle(lang, name);
            }
        }
        pane.save();
        PortletPage page = layoutMgr.getPortletPage(event.getPortletRequest());
        PortletTabbedPane mypane = page.getPortletTabbedPane();
        it = mypane.getPortletTabs().iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab)it.next();
            if (tab.getLabel().equals(label)) {
                tab.setTitle(lang, name);
            }
        }
        layoutMgr.reloadPage(event.getPortletRequest());
    }

    public void doRender(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        req.setAttribute("lang", req.getLocale().getLanguage());
        
        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        PortletPage page = layoutMgr.getPortletPage(req);
        String theme = page.getTheme();
        themeLB.clear();
        String themes = getPortletSettings().getAttribute("supported-themes");
        StringTokenizer st = new StringTokenizer(themes, ",");
        while (st.hasMoreTokens()) {
            ListBoxItemBean lb = new ListBoxItemBean();
            String val = (String)st.nextElement();
            lb.setValue(val.trim());
            if (val.trim().equalsIgnoreCase(theme)) lb.setSelected(true);
            themeLB.addBean(lb);
        }

        PortletTabbedPane pane = layoutMgr.getUserTabbedPane(req);
        List tabs;
        if (pane != null) {
            tabs = pane.getPortletTabs();
        } else {
            tabs = new ArrayList();
        }
        req.setAttribute("tabs", tabs);
        setNextState(req, VIEW_JSP);
    }

    public void saveTheme(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        String theme = themeLB.getSelectedValue();

        User user = req.getUser();
        AccountRequest acctReq = userManagerService.createAccountRequest(user);
        acctReq.setAttribute(User.THEME, theme);
        try {
            userManagerService.submitAccountRequest(acctReq);
        } catch (InvalidAccountRequestException e) {
            log.error("in ProfileManagerPortlet invalid account request", e);
        }
        user = userManagerService.approveAccountRequest(acctReq);
        PortletPage page = layoutMgr.getPortletPage(req);
        page.setTheme(theme);
        layoutMgr.reloadPage(req);       
    }

    public void doConfigureLayout(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();

        String themes = getPortletSettings().getAttribute("supported-themes");
        TextFieldBean themesTF = event.getTextFieldBean("themesTF");
        themesTF.setValue(themes);

        setNextState(req, CONFIGURE_JSP);
    }


}
