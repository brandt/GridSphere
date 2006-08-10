package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;
import org.gridlab.gridsphere.services.core.security.role.RoleManagerService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletPageFactory implements PortletSessionListener {

    private String userLayoutDir = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/CustomPortal/layouts/users");

    public static final String PAGE = "gsPageLayout";
    public static final String SETUP_PAGE = "SetupLayout.xml";

    private static final String DEFAULT_THEME = "default";

    private static PortletPageFactory instance = null;
    private PortletSessionManager sessionManager = PortletSessionManager.getInstance();
    private PortalConfigService portalConfigService = null;
    private GroupManagerService groupManagerService = null;

    private static PortletLog log = SportletLog.getInstance(PortletPageFactory.class);


    private static PortletPage templatePage = null;
    private static PortletPage setupPage = null;

    //private PortletPage newuserPage = null;
    private static PortletPage errorPage = null;

    private static String layoutMappingFile = null;

    // Store user layouts in a hash
    private static Map userLayouts = new Hashtable();

    private static Map tckLayouts = new Hashtable();

    private static Map guests = new Hashtable();
    private static Map customPages = new Hashtable();
    private static Map customLayouts = new Hashtable();
    private static boolean useTCK = false;

    private static boolean setupNeeded = false;

    private PortletPageFactory() {

    }

    public static void setSetupNeeded(boolean needSetup) {
        setupNeeded = needSetup;
    }

    public static void setUseTCK(boolean usetck) {
        useTCK = usetck;
    }

    public void init(ServletContext ctx) throws PortletException {
        layoutMappingFile = ctx.getRealPath("/WEB-INF/mapping/layout-mapping.xml");
        String templateLayoutPath = ctx.getRealPath("/WEB-INF/CustomPortal/layouts/TemplateLayout.xml");
        String newuserLayoutPath = ctx.getRealPath("/WEB-INF/CustomPortal/layouts/users/");
        String setupLayoutFile = ctx.getRealPath("/WEB-INF/CustomPortal/layouts/SetupLayout.xml");
        String errorLayoutFile = ctx.getRealPath("/WEB-INF/CustomPortal/layouts/ErrorLayout.xml");
        try {
            errorPage = PortletLayoutDescriptor.loadPortletPage(errorLayoutFile, layoutMappingFile);
            templatePage = PortletLayoutDescriptor.loadPortletPage(templateLayoutPath, layoutMappingFile);
            errorPage.setLayoutDescriptor(errorLayoutFile);
            setupPage = PortletLayoutDescriptor.loadPortletPage(setupLayoutFile, layoutMappingFile);
            setupPage.setLayoutDescriptor(setupLayoutFile);
        } catch (Exception e) {
            throw new PortletException("Error unmarshalling layout file", e);
        }

        String layoutsDirPath = ctx.getRealPath("/WEB-INF/CustomPortal/layouts/custom");
        File layoutsDir = new File(layoutsDirPath);
        if (!layoutsDir.exists()) layoutsDir.mkdir();
        File[] files = layoutsDir.listFiles();
        PortletPage customPage = null;
        for (int i = 0; i < files.length; i++) {
            File f = (File)files[i];
            try {
                String pageName = f.getName();
                pageName = pageName.substring(0, pageName.lastIndexOf("."));
                System.err.println("found a page= " + pageName);
                customPage = PortletLayoutDescriptor.loadPortletPage(f.getAbsolutePath(), layoutMappingFile);
                customPages.put(pageName, customPage);
            } catch (Exception e) {
                throw new PortletException("Error unmarshalling layout file", e);
            }
        }

        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        RoleManagerService roleService = null;
        UserManagerService userManagerService = null;

        try {
            portalConfigService = (PortalConfigService) factory.createPortletService(PortalConfigService.class, true);
            groupManagerService = (GroupManagerService) factory.createPortletService(GroupManagerService.class, true);
            roleService = (RoleManagerService)factory.createPortletService(RoleManagerService.class, true);
            userManagerService = (UserManagerService)factory.createPortletService(UserManagerService.class, true);
        } catch (PortletServiceException e) {
            log.error("Unable to init portal config service! ", e);
            throw new PortletException("Unable to init portal config service! ", e);
        }
        File userdir = new File(newuserLayoutPath);
        if (!userdir.exists()) {
            userdir.mkdir();
        }
        if ((userManagerService.getNumUsers() == 0) || (roleService.getUsersInRole(PortletRole.SUPER).size() == 0)) {
            setupNeeded = true;
        }
    }

    public static synchronized PortletPageFactory getInstance() {
        if (instance == null) {
            instance = new PortletPageFactory();
        }
        return instance;
    }

    public PortletPage createErrorPage(PortletRequest req) {
        errorPage.init(req, new ArrayList());
        return errorPage;
    }

    public PortletPage createSetupPage(PortletRequest req) {
        setupPage.init(req, new ArrayList());
        setupPage.setTheme(DEFAULT_THEME);
        return setupPage;
    }


    public void login(PortletRequest request) {

    }

    public void logout(PortletSession session) {
        log.debug("in logout PortletPageFactory");
        String sessionId = session.getId();

        log.debug("in logout PortletPageFactory for session: " + sessionId);
        if (guests.containsKey(sessionId)) {
            log.debug("Removing guest container for:" + sessionId);
            guests.remove(sessionId);
        }
        if (userLayouts.containsKey(sessionId)) {
            log.debug("Removing user  container for:" + sessionId);
            userLayouts.remove(sessionId);
        }
        if (customLayouts.containsKey(sessionId)) {
            log.debug("Removing custom containers for:" + sessionId);
            customLayouts.remove(sessionId);
        }
    }

    public synchronized void destroy() {
        guests.clear();
        userLayouts.clear();
    }

    public void addPortletGroupTab(PortletRequest req, PortletGroup group) {
        PortletPage page = createPortletPage(req);
        PortletTabbedPane pagePane = page.getPortletTabbedPane();
        PortletTabbedPane appPane = PortletTabRegistry.getGroupTabs(group);
        if (appPane != null) {
            List tabs = appPane.getPortletTabs();
            try {
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab) tabs.get(j);
                    pagePane.addTab((PortletTab) deepCopy(tab));
                }
            } catch (Exception e) {
                log.error("Unable to copy application tabs for webapp: " + group.getName());
            }

            page.setPortletTabbedPane(pagePane);
            page.init(req, new ArrayList());
        }
    }

    public void removePortletGroupTab(PortletRequest req, PortletGroup group) {
        PortletPage page = createPortletPage(req);
        PortletTabbedPane pagePane = page.getPortletTabbedPane();
        PortletTabbedPane appPane = PortletTabRegistry.getGroupTabs(group);
        if (appPane != null) {
            List tabs = appPane.getPortletTabs();
            try {
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab) tabs.get(j);
                    pagePane.removeTab(tab);
                }
            } catch (Exception e) {
                log.error("Unable to copy application tabs for webapp: " + group.getName());
            }
            page.setPortletTabbedPane(pagePane);
            page.init(req, new ArrayList());
        }
    }

    public PortletTabbedPane getUserTabbedPane(PortletRequest req) {
        User user = req.getUser();

        String sessionId = req.getPortletSession(true).getId();

        String userLayout = userLayoutDir + File.separator + user.getUserName();

        if (userLayouts.containsKey(sessionId)) {
            PortletPage page = (PortletPage) userLayouts.get(sessionId);
            PortletTabbedPane pane = new PortletTabbedPane();
            pane.setLayoutDescriptor(userLayout);
            PortletTabbedPane existPane = page.getPortletTabbedPane();
            List tabs = existPane.getPortletTabs();
            Iterator it = tabs.iterator();
            while (it.hasNext()) {
                PortletTab tab = (PortletTab) it.next();
                if (tab.getCanModify()) {
                    pane.addTab(tab);
                }
            }
            return (!pane.getPortletTabs().isEmpty() ? pane : null);
        }

        File f = new File(userLayout);
        PortletTabbedPane pane = null;

        // try {
        if (f.exists()) {
            //page = (PortletPage)deepCopy(templatePage);
            //page.setLayoutDescriptor(userLayout);
            try {
                pane = PortletLayoutDescriptor.loadPortletTabs(userLayout, layoutMappingFile);
                pane.setLayoutDescriptor(userLayout);
                log.debug("Adding user tab to layout");
            } catch (Exception e) {
                log.error("Unable to make a clone of the templatePage", e);
            }
        } else {
            return null;
        }

        // check for portlets no longer in groups and remove if necessary
        List groups = (List) req.getGroups();
        List allowedPortlets = new ArrayList();
        Iterator it = groups.iterator();
        while (it.hasNext()) {
            String groupName = (String) it.next();
            PortletGroup group = groupManagerService.getGroup(groupName);
            Set s = group.getPortletRoleList();
            Iterator sit = s.iterator();
            while (sit.hasNext()) {
                SportletRoleInfo roleInfo = (SportletRoleInfo) sit.next();
                allowedPortlets.add(roleInfo.getPortletClass());
            }
        }


        // create tmp page
        PortletPage tmpPage = new PortletPage();
        try {
            //tmpPage.setLayoutDescriptor(userLayout + ".tmp");
            PortletTabbedPane tmpPane = (PortletTabbedPane) deepCopy(pane);
            tmpPage.setPortletTabbedPane(tmpPane);
            this.setPageTheme(tmpPage, req);
            tmpPage.init(req, new ArrayList());

            // when deleting must reinit everytime
            int i = 0;
            boolean found;
            while (i < tmpPage.getComponentIdentifierList().size()) {
                found = false;
                it = tmpPage.getComponentIdentifierList().iterator();
                while (it.hasNext() && (!found)) {
                    found = false;
                    ComponentIdentifier cid = (ComponentIdentifier) it.next();
                    if (cid.getPortletComponent() instanceof PortletFrame) {
                        if (!allowedPortlets.contains(cid.getPortletClass())) {
                            PortletComponent pc = cid.getPortletComponent();
                            PortletComponent parent = pc.getParentComponent();
                            parent.remove(pc, req);
                            tmpPage.init(req, new ArrayList());
                            found = true;
                        }
                    }
                }
                i++;
            }

            tmpPane.save();
            return tmpPane;
        } catch (Exception e) {
            log.error("Unable to save user pane!", e);
        }

        return null;

    }

    public PortletPage createFromGroups(PortletRequest req) {

        List groups = (List) req.getGroups();
        PortletPage newPage = null;
        PortletTabbedPane pane;

        try {

            //newPage = (PortletPage)templatePage.clone();
            newPage = (PortletPage) deepCopy(templatePage);

            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();


            PortletTabbedPane gsTab = PortletTabRegistry.getGroupTabs(req.getGroup());
            List tabs = gsTab.getPortletTabs();
            for (int j = 0; j < tabs.size(); j++) {
                PortletTab tab = (PortletTab) tabs.get(j);
                log.debug("adding tab: " + tab.getTitle("en"));
                pane.addTab((PortletTab) deepCopy(tab));
            }

            Iterator it = groups.iterator();
            while (it.hasNext()) {
                String groupName = (String)it.next();

                if (groupName.equals(((PortletGroup)req.getGroup()).getName())) continue;

                log.debug("adding group layout: " + groupName);
                PortletGroup group = groupManagerService.getGroup(groupName);
                PortletTabbedPane portletTabs = PortletTabRegistry.getGroupTabs(group);
                if (portletTabs != null) {
                    tabs = portletTabs.getPortletTabs();

                    for (int j = 0; j < tabs.size(); j++) {
                        PortletTab tab = (PortletTab) tabs.get(j);
                        log.debug("adding tab: " + tab.getTitle("en"));

                        //pane.addTab(g.getName(), (PortletTab)tab.clone());
                        pane.addTab((PortletTab) deepCopy(tab));
                    }
                }
            }

            // place user tabs after group tabs
            PortletTabbedPane userPane = getUserTabbedPane(req);
            if (userPane != null) {
                List userTabs = userPane.getPortletTabs();
                for (int i = 0; i < userTabs.size(); i++) {
                    PortletTab tab = (PortletTab) userTabs.get(i);
                    log.debug("adding user tab: " + tab.getTitle("en"));
                    pane.addTab((PortletTab) deepCopy(tab));
                }
            }

            // sorting tabs
            Collections.sort(pane.getPortletTabs(), new PortletTab());

            // first use default theme
            setPageTheme(newPage, req);
            newPage.setPortletTabbedPane(pane);
            //newPage = (PortletPage)templatePage;
            newPage.init(req, new ArrayList());

            List list = newPage.getComponentIdentifierList();
            StringBuffer compSB = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                ComponentIdentifier c = (ComponentIdentifier) list.get(i);
                compSB.append("\tid: " + c.getComponentID() + " : " + c.getClassName() + " : " + c.hasPortlet() + "\n");
                //if (c.hasPortlet()) System.err.println("portlet= " + c.getPortletID());
            }
            log.debug("Made a components list!!!! " + list.size() + "\n" + compSB.toString());
        } catch (Exception e) {
            log.error("Unable to make a clone of the templatePage", e);

        }
        return newPage;
    }

    protected void setPageTheme(PortletPage page, PortletRequest req) {
        String defaultTheme = portalConfigService.getPortalConfigSettings().getDefaultTheme();
        if (defaultTheme != null) {
            page.setTheme(defaultTheme);
        }
        User user = req.getUser();
        if (user != null) {
            String theme = (String) user.getAttribute(User.THEME);
            if (theme != null) {
                page.setTheme(theme);
            }
        }
        if ((page.getTheme() == null) || (page.getTheme().equals(""))) {
            page.setTheme(DEFAULT_THEME);
        }
    }

    public PortletTabbedPane createNewUserPane(PortletRequest req, int cols, String tabName) {

        PortletTabbedPane pane = null;
        try {
            pane = getUserTabbedPane(req);
            int tabNum = PortletTab.DEFAULT_USERTAB_ORDER;
            if (pane == null) {
                pane = new PortletTabbedPane();
                User user = req.getUser();
                String userLayout = userLayoutDir + File.separator + user.getUserName();
                pane.setLayoutDescriptor(userLayout);
            } else {
                tabNum = pane.getLastPortletTab().getTabOrder() + 1;
            }

            PortletTab topTab = new PortletTab();
            System.err.println("setting tab num to " + tabNum);
            topTab.setTabOrder(tabNum);

            topTab.setCanModify(true);
            topTab.setTitle(req.getLocale().getLanguage(), tabName);
            PortletTabbedPane childPane = new PortletTabbedPane();
            PortletTab childTab = new PortletTab();

            childPane.setStyle("sub-menu");
            topTab.setPortletComponent(childPane);
            pane.addTab(topTab);
            topTab.setName(tabName);
            topTab.setLabel(URLEncoder.encode(tabName, "UTF-8") + "Tab");
            //pane.save(userLayout);

            PortletTableLayout table = new PortletTableLayout();

            table.setCanModify(true);
            table.setLabel(URLEncoder.encode(tabName, "UTF-8") + "TL");


            PortletRowLayout row = new PortletRowLayout();
            int width = 100 / cols;
            for (int i = 0; i < cols; i++) {
                PortletColumnLayout col = new PortletColumnLayout();
                col.setWidth(String.valueOf(width) + "%");
                row.addPortletComponent(col);
            }

            table.addPortletComponent(row);

            childTab.setPortletComponent(table);
            childTab.setTitle(req.getLocale().getLanguage(), "");
            childPane.addTab(childTab);

        } catch (Exception e) {
            log.error("Unable to make a clone of the templatePage", e);

        }
        return pane;

    }


    public PortletPage createTCKPage(PortletRequest req, String[] portletNames) {
        String pageName = req.getParameter("pageName");

        PortletPage page = new PortletPage();
        PortletTableLayout tableLayout = new PortletTableLayout();
        StringTokenizer tokenizer;
        for (int i = 0; i < portletNames.length; i++) {
            tokenizer = new StringTokenizer(portletNames[i], "/");
            String appName = tokenizer.nextToken();
            String portletName = tokenizer.nextToken();
            //String portletClass = registry.getPortletClassName(appName, portletName);
            //if (portletClass == null) {
            //    log.error("Unable to find portlet class for " + portletName);
            //}
            if (pageName == null) {
                pageName = "TCK_testpage_" + portletName;
            }
            PortletFrame frame = new PortletFrame();
            PortletTitleBar tb = new PortletTitleBar();
            //tb.setPortletClass(portletClass);
            tb.setPortletClass(appName + "#" + portletName);
            frame.setPortletTitleBar(tb);
            //frame.setPortletClass(portletClass);
            frame.setPortletClass(appName + "#" + portletName);
            tableLayout.addPortletComponent(frame);
        }

        PortletTab tab = new PortletTab();

        tab.setTitle(pageName);
        tab.setPortletComponent(tableLayout);
        PortletTabbedPane pane = new PortletTabbedPane();
        pane.addTab(tab);
        page.setPortletTabbedPane(pane);
        page.setLayoutDescriptor("/tmp/test.xml");
        try {
            page.save();
            this.setPageTheme(page, req);
            page.init(req, new ArrayList());
        } catch (IOException e) {
            log.error("Unale to save TCK page to /tmp/test.xml", e);
        }
        return page;
    }

    public PortletPage createPortletPage(PortletRequest req) {

        String sessionId = req.getPortletSession().getId();
        User user = req.getUser();

        String[] portletNames = req.getParameterValues("portletName");

        // Sun TCK test uses Jakarta Commons-HttpClient/2.0beta1
        if (useTCK) {
            if (req.getClient().getUserAgent().indexOf("HttpClient") > 0) {
                if (portletNames != null) {
                    log.info("Creating TCK LAYOUT!");
                    PortletPage tckLayout = createTCKPage(req, portletNames);
                    tckLayout.init(req, new ArrayList());
                    tckLayouts.put(sessionId, tckLayout);
                    sessionManager.addSessionListener(sessionId, this);
                }

                if (tckLayouts.containsKey(sessionId)) {
                    return (PortletPage)tckLayouts.get(sessionId);
                }
            }
        }

        String pageLayout = (String)req.getAttribute(PAGE);
        if (pageLayout != null) {
            return this.createSetupPage(req);
        }

        PortletPage page;

        if (setupNeeded) return createSetupPage(req);
        // check for custom page
        page = getCustomPage(req);
        if (page != null) return page;

        Principal principal = req.getUserPrincipal();
        if (principal == null) {
            log.debug("Creating a guest layout!!");
            return createFromGuestLayoutXML(req);
        } 



        // Need to provide one guest container per users session
        if (userLayouts.containsKey(sessionId)) {
            page = (PortletPage) userLayouts.get(sessionId);
            log.debug("Returning existing layout for:" + sessionId + " for user=" + user.getUserName());
        } else {
            // Now the user is user so remove guest layout
            if (guests.containsKey(sessionId)) {
                log.debug("Removing guest container for:" + sessionId);
                guests.remove(sessionId);
            }

            page = createFromGroups(req);
            userLayouts.put(sessionId, page);
            sessionManager.addSessionListener(sessionId, this);
        }
        return page;
    }


    public void removePortletPage(PortletRequest req) {
        PortletSession session = req.getPortletSession();
        /*
        String userLayout = "";
        try {
            userLayout = userLayoutDir + File.separator + req.getUser().getID();
            File f = new File(userLayout);
            if (f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            log.error("Unable to delete layout: " + userLayout, e);
        }
        */
        String id = session.getId();
        if (userLayouts.containsKey(id)) {
            userLayouts.remove(id);
        }
        //log.debug("removed user layout: " + userLayout);
    }

    public List getAllCustomPages(PortletRequest req) {
        PortletSession session = req.getPortletSession();
        String id = session.getId();
        Map userLayouts = (Map)customLayouts.get(id);
        return (userLayouts != null) ? (List)userLayouts.values() : new ArrayList();
    }

    public PortletPage getCustomPage(PortletRequest req) {
        PortletSession session = req.getPortletSession();
        String customPageDesc = (String)req.getAttribute(PAGE);
        if (customPageDesc == null) customPageDesc = req.getParameter(PAGE);
        if (customPageDesc == null) return null;

        String id = session.getId();
        Map userLayouts = (Map)customLayouts.get(id);
        if (userLayouts == null) userLayouts = new Hashtable();
        PortletPage customPage = (PortletPage)userLayouts.get(customPageDesc);
        if (customPage == null) {
            PortletPage page = (PortletPage)customPages.get(customPageDesc);
            try {
                PortletPage newpage = (PortletPage) deepCopy(page);
                newpage.init(req, new ArrayList());
                newpage.setTheme(DEFAULT_THEME);
                userLayouts.put(customPageDesc, newpage);
                customLayouts.put(id, userLayouts);
            } catch (Exception e) {
                log.error("Unable to clone page: " + customPageDesc);
            }
        }
        return customPage;

    }


    public PortletPage createFromGuestLayoutXML(PortletRequest req) {
        PortletSession session = req.getPortletSession();

        String id = session.getId();

        if (userLayouts.containsKey(id)) {
            userLayouts.remove(id);
        }

        if ((id != null) && (guests.containsKey(id))) {
            return (PortletPage) guests.get(id);
        } else {
            PortletPage newcontainer = null;
            try {
                //newcontainer = (PortletPage)guestPage.clone();
                PortletPage guestPage = PortletTabRegistry.getGuestLayoutPage();

                newcontainer = (PortletPage) deepCopy(guestPage);

                this.setPageTheme(newcontainer, req);

                // theme has to be set before it is inited
                newcontainer.init(req, new ArrayList());
                guests.put(id, newcontainer);
                sessionManager.addSessionListener(id, this);
            } catch (Exception e) {
                log.error("Unable to clone GuestUserLayout!", e);
                e.printStackTrace();
            }
            return newcontainer;
        }
    }

    protected void copyFile(File oldFile, File newFile) throws IOException {
        // Destination and streams
        log.debug("in copyFile(): oldFile: " + oldFile.getAbsolutePath() + " newFile: " + newFile.getCanonicalPath());
        FileInputStream fis = new FileInputStream(oldFile);
        FileOutputStream fos = new FileOutputStream(newFile);

        // Amount of data to copy
        long fileLength = oldFile.length();
        byte[] bytes = new byte[1024]; // 1K at a time
        int length = 0;
        long totalLength = 0;
        while (length > -1) {
            length = fis.read(bytes);
            if (length > 0) {
                fos.write(bytes, 0, length);
                totalLength += length;
            }
        }
        // Test that we copied all the data
        if (fileLength != totalLength) {
            throw new IOException("File copy size missmatch");
        }
        fos.flush();
        fos.close();
        fis.close();
    }


    static public Object deepCopy(Object oldObj) throws Exception {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos =
                    new ByteArrayOutputStream(); // A
            oos = new ObjectOutputStream(bos); // B
            // serialize and pass the object
            oos.writeObject(oldObj);   // C
            oos.flush();               // D
            ByteArrayInputStream bin =
                    new ByteArrayInputStream(bos.toByteArray()); // E
            ois = new ObjectInputStream(bin);                  // F
            // return the new object
            return ois.readObject(); // G
        } catch (Exception e) {
            System.out.println("Exception in ObjectCloner = " + e);
            throw(e);
        } finally {
            if (oos != null) oos.close();
            if (ois != null) ois.close();
        }
    }

    public void logStatistics() {

        log.debug("\n\nnumber of guest layouts: " + guests.size());
        Iterator it = guests.keySet().iterator();
        while (it.hasNext()) {
            String id = (String) it.next();
            log.debug("guest has session: " + id);
        }
        log.debug("number of user layouts: " + userLayouts.size());
        it = userLayouts.keySet().iterator();
        while (it.hasNext()) {
            String id = (String) it.next();
            log.debug("user has session: " + id);
        }

    }

}
