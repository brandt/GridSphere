/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.layout;

import org.gridlab.gridsphere.layout.PortletPage;
import org.gridlab.gridsphere.layout.PortletTab;
import org.gridlab.gridsphere.layout.PortletTabbedPane;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.File;
import java.util.*;
import java.net.URLEncoder;

public class UserLayoutPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_JSP = "layout/view.jsp";

    // Portlet services
    private LayoutManagerService layoutMgr = null;
    private UserManagerService userManagerService = null;
    private String[] themes = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            this.layoutMgr = (LayoutManagerService) config.getContext().getService(LayoutManagerService.class);
            this.userManagerService = (UserManagerService) config.getContext().getService(UserManagerService.class);

        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
/* moved to doRender
        String themesPath = getPortletConfig().getContext().getRealPath("/themes");
      
        File f = new File(themesPath);
        if (f.isDirectory()) {
            themes = f.list();
        }
*/
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
        if (tabName == null) {
            createErrorMessage(event, this.getLocalizedText(event.getPortletRequest(), "LAYOUT_NOTAB_ERROR"));
            return;
        }
        log.debug("creating tab " + tabName + " cols= " + cols);
        PortletTabbedPane pane = layoutMgr.getUserTabbedPane(event.getPortletRequest());
        if (pane != null) {
            Iterator it = pane.getPortletTabs().iterator();
            while (it.hasNext()) {
                PortletTab tab = (PortletTab)it.next();
                if (tab.getLabel().equals(tabName + "Tab")) {
                    createErrorMessage(event, this.getLocalizedText(event.getPortletRequest(), "LAYOUT_SAMETAB_ERROR"));
                    return;
                }
            }
        }

        pane = layoutMgr.createUserTabbedPane(event.getPortletRequest(), cols, tabName);

        PortletTab tab = pane.getLastPortletTab();

        pane.save();
        PortletPage page = layoutMgr.getPortletPage(event.getPortletRequest());
        PortletTabbedPane mypane = page.getPortletTabbedPane();
        List tabs = mypane.getPortletTabs();
        tabs.add(tab);
        Collections.sort(tabs, new PortletTab());
        layoutMgr.reloadPage(event.getPortletRequest());
    }

    public void deleteTab(FormEvent event) throws IOException {
        String label = event.getAction().getParameter("tabid");

        PortletTabbedPane pane = layoutMgr.getUserTabbedPane(event.getPortletRequest());
        List tabs = pane.getPortletTabs();
        Iterator it = tabs.iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab) it.next();
            if (tab.getLabel().equals(label)) {
                it.remove();
            }
        }
        pane.save();
        PortletPage page = layoutMgr.getPortletPage(event.getPortletRequest());
        PortletTabbedPane mypane = page.getPortletTabbedPane();
        it = mypane.getPortletTabs().iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab) it.next();
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
            PortletTab tab = (PortletTab) it.next();
            if (tab.getLabel().equals(label)) {
                tab.setTitle(lang, name);
                tab.setLabel(URLEncoder.encode(name, "UTF-8"));
            }
        }
        pane.save();
        PortletPage page = layoutMgr.getPortletPage(event.getPortletRequest());
        PortletTabbedPane mypane = page.getPortletTabbedPane();
        it = mypane.getPortletTabs().iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab) it.next();
            if (tab.getLabel().equals(label)) {
                tab.setTitle(lang, name);
                tab.setLabel(URLEncoder.encode(name, "UTF-8"));
            }
        }
        layoutMgr.reloadPage(event.getPortletRequest());
    }

    public void doRender(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
      
        req.setAttribute("lang", req.getLocale().getLanguage());
        
        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        PortletPage page = layoutMgr.getPortletPage(req);
        String theme = page.getTheme();
        themeLB.clear();
        
        String themesPath = getPortletConfig().getContext().getRealPath("/themes");       
        /// retrieve the current renderkit
        themesPath += "/" + page.getRenderKit();
      
        File f = new File(themesPath);
        if (f.isDirectory()) {
            themes = f.list();
        }

        for (int i = 0; i < themes.length; i++) {
            ListBoxItemBean lb = new ListBoxItemBean();
            lb.setValue(themes[i].trim());
            if (themes[i].trim().equalsIgnoreCase(theme)) lb.setSelected(true);
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
        if (user != null) {
            user.setAttribute(User.THEME, theme);
            userManagerService.saveUser(user);
        }

        PortletPage page = layoutMgr.getPortletPage(req);
        page.setTheme(theme);
        layoutMgr.reloadPage(req);
    }

    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.setValue(msg);
    }
}
