/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.layout.*;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import javax.servlet.UnavailableException;
import java.util.*;
import java.io.*;

public class LayoutManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_JSP = "admin/layout/view.jsp";
    public static final String EDIT_JSP = "admin/layout/edit.jsp";

    // Portlet services
    private LayoutManagerService layoutMgr = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        this.log.debug("Entering initServices()");
        try {
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        this.log.debug("Exiting initServices()");
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

        String filename = this.getPortletConfig().getContext().getRealPath("/html/pagehead.html");
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        ta.setValue(sb.toString());

        Map tabs = PortletTabRegistry.getApplicationTabs();

        List tabNames = new ArrayList();
        Iterator it = tabs.keySet().iterator();
        while (it.hasNext()) {
            tabNames.add((String)it.next());
        }

        req.setAttribute("tabNames", tabNames);

        Map groups = PortletTabRegistry.getGroupTabs();
        it = groups.keySet().iterator();
        Map groupNames = new HashMap();

        User user = req.getUser();

        AccessControlManagerService aclService = getACLService(user);

        String name;
        PortletGroup group;
        while (it.hasNext()) {
            name = (String)it.next();
            group = aclService.getGroupByName(name);
            if (group != null) {
                groupNames.put(name, group.getDescription());
            }
        }
        req.setAttribute("groupNames", groupNames);

        setNextState(req, VIEW_JSP);
    }

    public AccessControlManagerService getACLService(User user) {
        AccessControlManagerService aclManagerService = null;
        try {
            aclManagerService = (AccessControlManagerService)this.getConfig().getContext().getService(AccessControlManagerService.class, user);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        return aclManagerService;
    }

    public void saveBanner(FormEvent event) throws PortletException, IOException {
        this.checkSuperRole(event);
        TextAreaBean ta = event.getTextAreaBean("bannerTA");
        String newText = ta.getValue();
        String filename = this.getPortletConfig().getContext().getRealPath("/html/pagehead.html");

        FileWriter f = new FileWriter(filename);

        f.write(newText);
        f.close();

    }

    public void importLayout(FormEvent event) throws PortletException, IOException {
        this.checkSuperRole(event);

        ListBoxBean appsLB = event.getListBoxBean("appsLB");
        String val = appsLB.getSelectedValue();

        HiddenFieldBean groupHF = event.getHiddenFieldBean("layoutHF");
        String thisgroup = groupHF.getValue();
        String thisFile = PortletTabRegistry.getTabDescriptorPath(thisgroup);

        //String groupFile = PortletTabRegistry.getTabDescriptorPath(val);

        PortletTabbedPane groupPane = PortletTabRegistry.getGroupTabs(val);

        groupPane.setLayoutDescriptor(thisFile);

        groupPane.save();

        PortletTabRegistry.reloadTab(val, thisFile);

        saveLayout(event);

        editGroup(event, thisgroup);

    }

    public void editGroupLayout(FormEvent event) throws PortletException, IOException {

        String group = event.getAction().getParameter("group");

        editGroup(event, group);

    }

    public void editGroup(FormEvent event, String group) throws PortletException, IOException {
        String groupLayoutPath = PortletTabRegistry.getTabDescriptorPath(group);
        PortletRequest req = event.getPortletRequest();
        
        Boolean allowImport = Boolean.TRUE;

        if (PortletTabRegistry.getApplicationTabs(group) != null) {
            allowImport = Boolean.FALSE;
        }

        TextAreaBean ta = event.getTextAreaBean("layoutFile");
        HiddenFieldBean hf = event.getHiddenFieldBean("layoutHF");
        hf.setName("group");
        hf.setValue(group);
        req.setAttribute("name", group);
        req.setAttribute("allowImport", allowImport);

        ListBoxBean appsLB = event.getListBoxBean("appsLB");

        Map tabs = PortletTabRegistry.getGroupTabs();

        Iterator it = tabs.keySet().iterator();
        while (it.hasNext()) {
            ListBoxItemBean item = new ListBoxItemBean();
            String name = (String)it.next();
            item.setName(name);
            item.setValue(name);
            appsLB.addBean(item);
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(groupLayoutPath), "UTF8"));

        String line = null;
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
        //this.checkSuperRole(event);

        User user = event.getPortletRequest().getUser();
        AccessControlManagerService aclService = getACLService(user);
        HiddenFieldBean groupHF = event.getHiddenFieldBean("layoutHF");
        TextAreaBean ta = event.getTextAreaBean("layoutFile");
        String newText = ta.getValue();

        if (groupHF.getValue().equals("guest")) {
            this.checkSuperRole(event);
            String guestFile = PortletTabRegistry.getGuestLayoutFile();
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(guestFile), "UTF-8"));
            byte[] text = newText.getBytes("iso-8859-1");
            String newstring = new String(text, "UTF-8");
            out.write(newstring);
            out.close();
            try {
                PortletTabRegistry.reloadGuestLayout();
            } catch (PersistenceManagerException e) {

            }
            return;
        }

        PortletGroup group = aclService.getGroupByName(groupHF.getValue());
        if (!aclService.hasSuperRole(user) && !aclService.hasAdminRoleInGroup(user, group)) {
            return;
        }
        
        String groupFile = PortletTabRegistry.getTabDescriptorPath(group.getName());
        System.err.println("saving group layout: " + group.getName());
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(groupFile), "UTF-8"));
        byte[] text = newText.getBytes("iso-8859-1");
        String newstring = new String(text, "UTF-8");
        out.write(newstring);
        out.close();

        PortletTabRegistry.reloadTab(groupHF.getValue(), groupFile);
       
    }

    public void editGuestLayout(FormEvent event)  throws PortletException, IOException {
        PortletRequest req = event.getPortletRequest();
        req.setAttribute("name", "GuestUserLayout.xml");
        req.setAttribute("allowImport", Boolean.FALSE);

        TextAreaBean ta = event.getTextAreaBean("layoutFile");
        HiddenFieldBean hf = event.getHiddenFieldBean("layoutHF");
        hf.setName("group");
        hf.setValue("guest");
        String guestLayoutPath = PortletTabRegistry.getGuestLayoutFile();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(guestLayoutPath), "UTF-8"));

        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        reader.close();
        ta.setValue(sb.toString());

        setNextState(req, EDIT_JSP);
    }


    public void deleteLayout(FormEvent event) throws PortletException, IOException {
        PortletRequest req = event.getPortletRequest();

        String group = event.getAction().getParameter("group");

        PortletTabRegistry.removeGroupTab(group);
        setNextState(req, VIEW_JSP);
    }
}
