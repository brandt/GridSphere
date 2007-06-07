/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.layout;

import org.gridlab.gridsphere.layout.PortletPage;
import org.gridlab.gridsphere.layout.PortletTabRegistry;
import org.gridlab.gridsphere.layout.PortletTabbedPane;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigSettings;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;

import javax.servlet.UnavailableException;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LayoutManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_JSP = "admin/layout/view.jsp";
    public static final String EDIT_JSP = "admin/layout/edit.jsp";

    // Portlet services
    private LayoutManagerService layoutMgr = null;
    private PortalConfigService portalConfigService = null;
    private GroupManagerService groupManagerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        log.debug("Entering initServices()");
        try {
            this.layoutMgr = (LayoutManagerService) config.getContext().getService(LayoutManagerService.class);
            this.portalConfigService = (PortalConfigService) config.getContext().getService(PortalConfigService.class);
            groupManagerService = (GroupManagerService) this.getConfig().getContext().getService(GroupManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        log.debug("Exiting initServices()");
        //portletMgr = PortletManager.getInstance();

        DEFAULT_VIEW_PAGE = "doShowLayout";
        DEFAULT_HELP_PAGE = "admin/layout/help.jsp";

    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void refreshLayout(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        layoutMgr.refreshPage(req);
    }

    public void doShowLayout(FormEvent event) throws PortletException, IOException {
        PortletRequest req = event.getPortletRequest();

        TextAreaBean ta = event.getTextAreaBean("bannerTA");

        String filename = this.getPortletConfig().getContext().getRealPath("/WEB-INF/CustomPortal/layouts/html/pagehead.html");
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        ta.setValue(sb.toString());

        ta = event.getTextAreaBean("footerTA");

        filename = this.getPortletConfig().getContext().getRealPath("/WEB-INF/CustomPortal/layouts/html/pagefooter.html");
        reader = new BufferedReader(new FileReader(filename));

        sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        ta.setValue(sb.toString());

        String themesPath = getPortletConfig().getContext().getRealPath("/themes");

        /// retrieve the current renderkit
        PortletPage page = layoutMgr.getPortletPage(req);
        themesPath += "/" + page.getRenderKit();

        System.err.println("themes path=" + themesPath);

        String[] themes = null;
        File f = new File(themesPath);
        if (f.isDirectory()) {
            themes = f.list();
        }

        String defaultTheme = portalConfigService.getPortalConfigSettings().getDefaultTheme();
        ListBoxBean lb = event.getListBoxBean("themesLB");
        ListBoxItemBean item;
        for (int i = 0; i < themes.length; i++) {
            item = new ListBoxItemBean();
            item.setValue(themes[i]);
            item.setName(themes[i]);
            if (themes[i].equals(defaultTheme)) item.setSelected(true);
            lb.addBean(item);
        }


        List groups = groupManagerService.getGroups();
        Iterator it = groups.iterator();
        Map groupNames = new HashMap();

        PortletGroup group;
        while (it.hasNext()) {
            group = (PortletGroup) it.next();
            groupNames.put(group.getName(), group.getDescription());
        }
        req.setAttribute("groupNames", groupNames);
        PortletGroup coreGroup = groupManagerService.getCoreGroup();
        req.setAttribute("coreGroup", coreGroup.getName());
        setNextState(req, VIEW_JSP);
    }

    public void saveBanner(FormEvent event) throws PortletException, IOException {

        TextAreaBean ta = event.getTextAreaBean("bannerTA");
        String newText = ta.getRawValue();
        String filename = this.getPortletConfig().getContext().getRealPath("/WEB-INF/CustomPortal/layouts/html/pagehead.html");

        FileWriter f = new FileWriter(filename);

        f.write(newText);
        f.close();

    }

    public void saveFooter(FormEvent event) throws PortletException, IOException {

        TextAreaBean ta = event.getTextAreaBean("footerTA");
        String newText = ta.getRawValue();
        String filename = this.getPortletConfig().getContext().getRealPath("/WEB-INF/CustomPortal/layouts/html/pagefooter.html");

        FileWriter f = new FileWriter(filename);

        f.write(newText);
        f.close();

    }

    public void saveDefaultTheme(FormEvent event) throws PortletException, IOException {

        PortletRequest req = event.getPortletRequest();
        ListBoxBean themesLB = event.getListBoxBean("themesLB");
        String theme = themesLB.getSelectedValue();
        themesLB.clear();
        if (!theme.equals("")) {
            PortalConfigSettings configSettings = portalConfigService.getPortalConfigSettings();
            configSettings.setDefaultTheme(theme);
            portalConfigService.savePortalConfigSettings(configSettings);
        }
        PortletPage page = layoutMgr.getPortletPage(req);

        page.setTheme(theme);
        User user = req.getUser();
        theme = (String) user.getAttribute(User.THEME);
        if (theme != null) page.setTheme(theme);
        layoutMgr.reloadPage(event.getPortletRequest());
    }

    public void importLayout(FormEvent event) throws PortletException, IOException {

        ListBoxBean appsLB = event.getListBoxBean("appsLB");
        String val = appsLB.getSelectedValue();

        HiddenFieldBean groupHF = event.getHiddenFieldBean("layoutHF");
        String thisgroup = groupHF.getValue();
        PortletGroup group = groupManagerService.getGroup(thisgroup);
        PortletGroup newgroup = groupManagerService.getGroup(val);
        String thisFile = PortletTabRegistry.getTabDescriptorPath(group);

        PortletTabbedPane groupPane = PortletTabRegistry.getGroupTabs(newgroup);

        groupPane.setLayoutDescriptor(thisFile);

        groupPane.save();
        try {
            PortletTabRegistry.reloadTab(thisFile, newgroup);
            saveLayout(event);

            String groupLayoutPath = PortletTabRegistry.getTabDescriptorPath(group);
            editGroup(event, group, groupLayoutPath);

        } catch (Exception e) {
            log.error("Unable to reload tab", e);

        }


    }

    public void editGroupLayout(FormEvent event) throws PortletException, IOException {

        String groupName = event.getAction().getParameter("group");
        PortletGroup group = this.groupManagerService.getGroup(groupName);
        String groupLayoutPath = PortletTabRegistry.getTabDescriptorPath(group);
        editGroup(event, group, groupLayoutPath);

    }

    public void editGroup(FormEvent event, PortletGroup group, String layoutPath) throws PortletException, IOException {

        PortletRequest req = event.getPortletRequest();

        Boolean allowImport = Boolean.FALSE;

        TextAreaBean ta = event.getTextAreaBean("layoutFile");
        HiddenFieldBean hf = event.getHiddenFieldBean("layoutHF");
        hf.setName("group");
        hf.setValue(group.getName());
        req.setAttribute("name", group.getName());
        req.setAttribute("allowImport", allowImport);

        ListBoxBean appsLB = event.getListBoxBean("appsLB");

        Map tabs = PortletTabRegistry.getGroupTabs();

        Iterator it = tabs.keySet().iterator();
        while (it.hasNext()) {
            ListBoxItemBean item = new ListBoxItemBean();
            String name = (String) it.next();
            item.setName(name);
            item.setValue(name);
            appsLB.addBean(item);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(layoutPath), "UTF8"));

        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        ta.setValue(sb.toString());

        setNextState(req, EDIT_JSP);
    }

    public void cancelLayout(FormEvent event) throws PortletException, IOException {
        setNextState(event.getPortletRequest(), DEFAULT_VIEW_PAGE);
    }

    public void saveLayout(FormEvent event) throws PortletException, IOException {
        HiddenFieldBean groupHF = event.getHiddenFieldBean("layoutHF");
        TextAreaBean ta = event.getTextAreaBean("layoutFile");
        String newText = ta.getRawValue();
        PortletRequest req = event.getPortletRequest();
        if (groupHF.getValue().equals("guest")) {
            // continue only if req has super role           
            if (!req.getRoles().contains(PortletRole.SUPER.getName())) return;
            String guestFile = PortletTabRegistry.getGuestLayoutFile();
            String tmpFile = guestFile + "-tmp";
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "UTF-8"));
            //byte[] text = newText.getBytes("iso-8859-1");
            //String newstring = new String(text, "UTF-8");
            out.write(newText);
            out.close();
            try {
                PortletTabRegistry.loadPage(tmpFile);
                PortletTabRegistry.copyFile(new File(tmpFile), new File(guestFile));
                PortletTabRegistry.reloadGuestLayout();

                createSuccessMessage(event, this.getLocalizedText(event.getPortletRequest(), "LAYOUTMGR_VALID_LAYOUT"));
            } catch (Exception e) {
                createErrorMessage(event, this.getLocalizedText(event.getPortletRequest(), "LAYOUTMGR_INVALID_LAYOUT"));
            } finally {
                File f = new File(tmpFile);
                f.delete();
            }
            return;
        }

        PortletGroup group = groupManagerService.getGroup(groupHF.getValue());

        /*
        if (!groupManagerService.hasSuperRole(user) && !groupManagerService.hasAdminRoleInGroup(user, group)) {
            return;
        }
        */

        String groupFile = PortletTabRegistry.getTabDescriptorPath(group);
        log.info("saving group layout: " + group.getName());
        String tmpFile = groupFile + "-tmp";
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "UTF-8"));
        //byte[] text = newText.getBytes("iso-8859-1");
        //String newstring = new String(text, "UTF-8");
        out.write(newText);
        out.close();

        // first validate tab
        try {
            PortletTabRegistry.loadTab(tmpFile);

            PortletTabRegistry.copyFile(new File(tmpFile), new File(groupFile));
            PortletTabRegistry.reloadTab(groupHF.getValue(), group);
            createSuccessMessage(event, this.getLocalizedText(event.getPortletRequest(), "LAYOUTMGR_VALID_LAYOUT"));
        } catch (Exception e) {
            // ok use old tab
            log.error("Unable to reload new tab!", e);
            createErrorMessage(event, this.getLocalizedText(event.getPortletRequest(), "LAYOUTMGR_INVALID_LAYOUT"));
            editGroup(event, group, tmpFile);
        } finally {
            File f = new File(tmpFile);
            f.delete();
        }


    }

    public void editGuestLayout(FormEvent event) throws PortletException, IOException {
        PortletRequest req = event.getPortletRequest();
        req.setAttribute("name", "GuestUserLayout.xml");
        req.setAttribute("allowImport", Boolean.FALSE);

        TextAreaBean ta = event.getTextAreaBean("layoutFile");
        HiddenFieldBean hf = event.getHiddenFieldBean("layoutHF");
        hf.setName("group");
        hf.setValue("guest");
        String guestLayoutPath = PortletTabRegistry.getGuestLayoutFile();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(guestLayoutPath), "UTF-8"));

        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        ta.setValue(sb.toString());

        setNextState(req, EDIT_JSP);
    }

    /*
    public void deleteLayout(FormEvent event) throws PortletException, IOException {
        PortletRequest req = event.getPortletRequest();

        String group = event.getAction().getParameter("group");
        PortletGroup pgroup = this.groupManagerService.getGroup(group);
        PortletTabRegistry.removeGroupTab(pgroup);
        createSuccessMessage(event, this.getLocalizedText(event.getPortletRequest(), "LAYOUTMGR_DELETE_LAYOUT") + "  " + group);
        setNextState(req, VIEW_JSP);
    }
    */

    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.setValue(msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.setValue(msg);
    }
}
