package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;

import java.io.*;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletPageFactory implements PortletSessionListener {

    private String userLayoutDir = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/users");

    private static PortletPageFactory instance = null;
    private static PortletSessionManager sessionManager = PortletSessionManager.getInstance();
    private static PortletRegistry registry = PortletRegistry.getInstance();
    private static PortletLog log = SportletLog.getInstance(PortletPageFactory.class);

    private String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");

    private PortletPage templatePage = null;

    //private PortletPage newuserPage = null;
    private PortletPage errorPage = null;

    private String templateLayoutPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/TemplateLayout.xml");

    private String newuserLayoutPath =  GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/users/");

    // Store user layouts in a hash
    private static Map userLayouts = new Hashtable();

    private PortletPage tckLayout = null;

    private static Map guests = new Hashtable();

    private PortletPageFactory() throws IOException, PersistenceManagerException {
        String errorLayoutFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/ErrorLayout.xml");

        errorPage = PortletLayoutDescriptor.loadPortletPage(errorLayoutFile, layoutMappingFile);

        templatePage = PortletLayoutDescriptor.loadPortletPage(templateLayoutPath, layoutMappingFile);
        errorPage.setLayoutDescriptor(errorLayoutFile);

        File userdir = new File(newuserLayoutPath);
        if (!userdir.exists()) {
            userdir.mkdir();
        }
    }

    public static synchronized PortletPageFactory getInstance() throws IOException, PersistenceManagerException {
        if (instance == null) {
            instance = new PortletPageFactory();
        }
        return instance;
    }

    public PortletPage createErrorPage(PortletRequest req) {
        errorPage.init(req, new ArrayList());
        return errorPage;
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
    }

    public void destroy() {
        Iterator it = guests.keySet().iterator();
        while (it.hasNext()) {
            String key = (String)it.next();
            guests.remove(key);
        }
        it = userLayouts.keySet().iterator();
        while (it.hasNext()) {
            String key = (String)it.next();
            userLayouts.remove(key);
        }
    }


    public void addPortletApplicationTab(PortletRequest req, String webAppName) {
        PortletPage page = createPortletPage(req);
        PortletTabbedPane pagePane = page.getPortletTabbedPane();
        PortletTabbedPane appPane = PortletTabRegistry.getApplicationTabs(webAppName);
        if (appPane != null) {
            List tabs = appPane.getPortletTabs();
            try {
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);
                    pagePane.addTab((PortletTab)deepCopy(tab));
                }
            } catch (Exception e) {
                log.error("Unable to copy application tabs for webapp: " + webAppName);
            }
            page.setPortletTabbedPane(pagePane);
            page.init(req, new ArrayList());
        }
    }

    public void addPortletGroupTab(PortletRequest req, String groupName) {
        PortletPage page = createPortletPage(req);
        PortletTabbedPane pagePane = page.getPortletTabbedPane();
        PortletTabbedPane appPane = PortletTabRegistry.getGroupTabs(groupName);
        //getApplicationTabs(webAppName);
        if (appPane != null) {
            List tabs = appPane.getPortletTabs();
            try {
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);
                    pagePane.addTab((PortletTab)deepCopy(tab));
                }
            } catch (Exception e) {
                log.error("Unable to copy application tabs for webapp: " + groupName);
            }
            page.setPortletTabbedPane(pagePane);
            page.init(req, new ArrayList());
        }
    }

    public PortletPage createFromGroups(PortletRequest req) {

        List groups = (List)req.getAttribute(SportletProperties.PORTLETGROUPS);
        PortletPage newPage = null;
        PortletTabbedPane pane = null;

        try {

            //newPage = (PortletPage)templatePage.clone();
            newPage = (PortletPage)deepCopy(templatePage);

            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();


            // see if user has a "user" tab
            User user = req.getUser();
            String userLayout = userLayoutDir + File.separator + user.getID();

            File f = new File(userLayout);
            newPage.setLayoutDescriptor(userLayout);

            // try {

            if (f.exists()) {
                //page = (PortletPage)deepCopy(templatePage);
                //page.setLayoutDescriptor(userLayout);
                PortletTabbedPane userTab = null;
                try {
                    userTab = PortletLayoutDescriptor.loadPortletTabs(userLayout, layoutMappingFile);
                    pane.addTab((PortletTab)deepCopy(userTab));
                    log.debug("Adding user tab to layout");
                } catch (Exception e) {
                    log.error("Unable to make a clone of the templatePage", e);
                }
            }

            // get groups that a user is member of
            PortletManager manager = PortletManager.getInstance();
            List webappNames = manager.getPortletWebApplicationNames();

            PortletTabbedPane gsTab  = PortletTabRegistry.getGroupTabs(PortletGroupFactory.GRIDSPHERE_GROUP.getName());
            List tabs = gsTab.getPortletTabs();
            for (int j = 0; j < tabs.size(); j++) {
                PortletTab tab = (PortletTab)tabs.get(j);
                System.err.println("adding tab: " + tab.getTitle("en"));
                //pane.addTab(g.getName(), (PortletTab)tab.clone());
                pane.addTab((PortletTab)deepCopy(tab));
            }


            //for (int i = 0; i < webappNames.size(); i++) {
             //   String webappName = (String)webappNames.get(i);
                Iterator it = groups.iterator();
                while (it.hasNext()) {
                    PortletGroup g = (PortletGroup)it.next();

                    if (g.getName().equals(PortletGroupFactory.GRIDSPHERE_GROUP.getName())) continue;
                    //if (g.getName().equals(webappName)) {
                    log.debug("adding group layout: " + g.getName());
                    PortletTabbedPane portletTabs = PortletTabRegistry.getGroupTabs(g.getName());
                    tabs = portletTabs.getPortletTabs();
                    for (int j = 0; j < tabs.size(); j++) {
                        PortletTab tab = (PortletTab)tabs.get(j);
                        System.err.println("adding tab: " + tab.getTitle("en"));
                        //pane.addTab(g.getName(), (PortletTab)tab.clone());
                        pane.addTab((PortletTab)deepCopy(tab));
                    }
                    //}
                }
            //}
            newPage.setPortletTabbedPane(pane);
            //newPage = (PortletPage)templatePage;
            newPage.init(req, new ArrayList());
        } catch (Exception e) {
            log.error("Unable to make a clone of the templatePage", e);

        }
        return newPage;
    }

    public PortletPage createUserPage(PortletRequest req) {


        PortletPage newPage = null;
        PortletTabbedPane pane = null;

        try {

            //newPage = (PortletPage)templatePage.clone();
            newPage = (PortletPage)deepCopy(templatePage);

            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();

            // see if user has a "user" tab
            User user = req.getUser();
            String userLayout = userLayoutDir + File.separator + user.getID();

            File f = new File(userLayout);
            newPage.setLayoutDescriptor(userLayout);

            // try {

            PortletTabbedPane tabbedPane = new PortletTabbedPane();

           PortletTab tab = new PortletTab();
            tabbedPane.addTab(tab);

            /*
            if (f.exists()) {
                //page = (PortletPage)deepCopy(templatePage);
                //page.setLayoutDescriptor(userLayout);
                PortletTabbedPane userTab = null;
                try {
                    userTab = PortletLayoutDescriptor.loadPortletTabs(userLayout, layoutMappingFile);
                    for (int i = 0; i < userTab.getTabCount(); i++) {
                        PortletTab tab = userTab.getPortletTabAt(i);
                        tab.setIsCustomizable(true);
                    }
                    pane.addTab((PortletTab)deepCopy(userTab));
                    log.debug("Adding user tab to layout");
                } catch (Exception e) {
                    log.error("Unable to make a clone of the templatePage", e);
                }
            }

            // get groups that a user is member of
            PortletManager manager = PortletManager.getInstance();
            List webappNames = manager.getPortletWebApplicationNames();

            PortletTabbedPane gsTab  = PortletTabRegistry.getGroupTabs(PortletGroupFactory.GRIDSPHERE_GROUP.getName());
            List tabs = gsTab.getPortletTabs();
            for (int j = 0; j < tabs.size(); j++) {
                PortletTab tab = (PortletTab)tabs.get(j);
                System.err.println("adding tab: " + tab.getTitle("en"));
                //pane.addTab(g.getName(), (PortletTab)tab.clone());
                pane.addTab((PortletTab)deepCopy(tab));
            }


            //for (int i = 0; i < webappNames.size(); i++) {
            //   String webappName = (String)webappNames.get(i);
            Iterator it = groups.iterator();
            while (it.hasNext()) {
                PortletGroup g = (PortletGroup)it.next();

                if (g.getName().equals(PortletGroupFactory.GRIDSPHERE_GROUP.getName())) continue;
                //if (g.getName().equals(webappName)) {
                log.debug("adding group layout: " + g.getName());
                PortletTabbedPane portletTabs = PortletTabRegistry.getGroupTabs(g.getName());
                tabs = portletTabs.getPortletTabs();
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);
                    System.err.println("adding tab: " + tab.getTitle("en"));
                    //pane.addTab(g.getName(), (PortletTab)tab.clone());
                    pane.addTab((PortletTab)deepCopy(tab));
                }
                //}
            }
            //}
            newPage.setPortletTabbedPane(pane);
            //newPage = (PortletPage)templatePage;
            newPage.init(req, new ArrayList());
            */
        } catch (Exception e) {
            log.error("Unable to make a clone of the templatePage", e);

        }

        return newPage;
    }


    public PortletPage createTCKPage(PortletRequest req, String[] portletNames) {
        String pageName = req.getParameter("pageName");

        PortletPage page = new PortletPage();
        PortletTableLayout tableLayout = new PortletTableLayout();
        StringTokenizer tokenizer;
        for (int i = 0; i < portletNames.length; i++) {
            tokenizer =  new StringTokenizer(portletNames[i], "/");
            String appName = tokenizer.nextToken();
            String portletName = tokenizer.nextToken();
            String portletClass = registry.getPortletClassName(appName, portletName);
            if (portletClass == null) {
                System.err.println("Unable to find portlet class for " + portletName);

            }
            if ( pageName == null ) {
                pageName = "TCK_testpage_" + portletName;
            }
            PortletFrame frame = new PortletFrame();
            PortletTitleBar tb = new PortletTitleBar();
            tb.setPortletClass(portletClass);
            frame.setPortletTitleBar(tb);
            frame.setLabel(portletName);
            frame.setPortletClass(portletClass);
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
        } catch (IOException e) {

        }
        page.init(req, new ArrayList());

        return page;
    }

    public PortletPage createPortletPage(PortletRequest req) {

        String sessionId = req.getPortletSession().getId();
        User user = req.getUser();

        log.debug("User requesting layout: " + user.getUserName());

        String[] portletNames = req.getParameterValues("portletName");
        if ( portletNames != null ) {
            System.err.println("Creating TCK LAYOUT!");
            tckLayout =  createTCKPage(req, portletNames );
        }

        if (tckLayout != null) {
            tckLayout.init(req, new ArrayList());
            return tckLayout;
        }

        if (user instanceof GuestUser) {
            return createFromGuestLayoutXML(req);
        }

        PortletPage page = null;

        // Need to provide one guest container per users session
        if (userLayouts.containsKey(sessionId)) {
            log.debug("Returning existing layout for:" + sessionId + " for user=" + user.getUserName());
            return (PortletPage) userLayouts.get(sessionId);
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

    public PortletPage createFromGuestLayoutXML(PortletRequest req) {
        PortletSession session = req.getPortletSession();

        String id = session.getId();

        if (userLayouts.containsKey(id)) {
            userLayouts.remove(id);
        }

        if ((id != null) && (guests.containsKey(id))) {
            return (PortletPage)guests.get(id);
        } else {
            PortletPage newcontainer = null;
            try {
                //newcontainer = (PortletPage)guestPage.clone();
                PortletPage guestPage = PortletTabRegistry.getGuestLayoutPage();
                newcontainer = (PortletPage)deepCopy(guestPage);
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


    static public Object deepCopy(Object oldObj) throws Exception
   {
      ObjectOutputStream oos = null;
      ObjectInputStream ois = null;
      try
      {
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
         Object ro = ois.readObject(); // G
          return ro;
      }
      catch(Exception e)
      {
         System.out.println("Exception in ObjectCloner = " + e);
         throw(e);
      }
      finally
      {
         oos.close();
         ois.close();
      }
   }

    public void logStatistics() {

        log.debug("number of guest layouts: " + guests.size());
        Iterator it = guests.keySet().iterator();
        while (it.hasNext()) {
            String id = (String)it.next();
            log.debug("guest has session: " + id);
        }
        log.debug("number of user layouts: " + userLayouts.size());
        it = userLayouts.keySet().iterator();
        while (it.hasNext()) {
            String id = (String)it.next();
            log.debug("user has session: " + id);
        }

    }

}
