package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;
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

    private static PortletLog log = SportletLog.getInstance(PortletPageFactory.class);

    private String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");

    private PortletPage templatePage = null;
    private PortletPage guestPage = null;
    //private PortletPage newuserPage = null;
    private PortletPage errorPage = null;

    private String templateLayoutPath = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/TemplateLayout.xml");

    private String newuserLayoutPath =  GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/users/");

    // Store user layouts in a hash
    private static Map userLayouts = new Hashtable();

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

        String guestLayoutFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/GuestUserLayout.xml");

        guestPage = PortletLayoutDescriptor.loadPortletPage(guestLayoutFile, layoutMappingFile);
        guestPage.setLayoutDescriptor(guestLayoutFile);
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

    public PortletPage createFromAllWebApps(PortletRequest req) {

        PortletPage newPage = null;
        PortletTabbedPane pane = null;
        try {

            //newPage = (PortletPage)templatePage.clone();
            newPage = (PortletPage)deepCopy(templatePage);
            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();

            PortletManager manager = PortletManager.getInstance();
            List webappNames = manager.getPortletWebApplicationNames();

            for (int i = 0; i < webappNames.size(); i++) {
                String name = (String)webappNames.get(i);

                PortletTabbedPane portletTabs = PortletTabRegistry.getApplicationTabs(name);

                List tabs = Collections.synchronizedList(portletTabs.getPortletTabs());
                synchronized(tabs) {
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);

                    //pane.addTab(name, (PortletTab)tab.clone());
                    pane.addTab((PortletTab)deepCopy(tab));
                }
                }
            }

            //newPage.setPortletTabbedPane(pane);
            newPage.init(req, new ArrayList());


            //newPage = (PortletPage)templatePage;
        } catch (Exception e) {
          log.error("Unable to make a clone of the templatePage", e);

        }


        return newPage;
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

    public void addPortletApplicationTab(User user, String webAppName) {
        PortletPage page = createPortletPage(user);
        PortletTabbedPane pagePane = page.getPortletTabbedPane();
        PortletTabbedPane appPane = PortletTabRegistry.getApplicationTabs(webAppName);
        if (appPane != null) {
            System.err.println("trying to add tabs!!!!!");
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
            try {
                page.save();
            } catch (IOException e) {

            }
        }
    }

    public void removePortletApplicationTab(User user, String webAppName) {
        PortletPage page = createPortletPage(user);
        PortletTabbedPane pagePane = page.getPortletTabbedPane();
        PortletTabbedPane appPane = PortletTabRegistry.getApplicationTabs(webAppName);
        if (appPane != null) {
            System.err.println("trying to add tabs!!!!!");
            List tabs = appPane.getPortletTabs();
            try {
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);
                    pagePane.removeTab(tab);
                }
            } catch (Exception e) {
                log.error("Unable to copy application tabs for webapp: " + webAppName);
            }
            page.setPortletTabbedPane(pagePane);
            try {
                page.save();
            } catch (IOException e) {

            }
        }
    }

    public PortletPage createGridSpherePage() {
        PortletPage newPage = null;
        PortletTabbedPane pane = null;
        try {

            //newPage = (PortletPage)templatePage.clone();
            newPage = (PortletPage)deepCopy(templatePage);
            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();

            PortletManager manager = PortletManager.getInstance();
            List webappNames = manager.getPortletWebApplicationNames();

            PortletGroup g = PortletGroupFactory.GRIDSPHERE_GROUP;

            for (int i = 0; i < webappNames.size(); i++) {
                String webappName = (String)webappNames.get(i);

                    if (g.getName().equals(webappName)) {
                        log.debug("have group: " + g.getName());
                        PortletTabbedPane portletTabs = PortletTabRegistry.getApplicationTabs(webappName);
                        List tabs = portletTabs.getPortletTabs();
                        for (int j = 0; j < tabs.size(); j++) {
                            PortletTab tab = (PortletTab)tabs.get(j);

                            //pane.addTab(g.getName(), (PortletTab)tab.clone());
                            pane.addTab((PortletTab)deepCopy(tab));
                        }
                    }

            }

            newPage.setPortletTabbedPane(pane);
            //newPage = (PortletPage)templatePage;
        } catch (Exception e) {
            log.error("Unable to make a clone of the templatePage", e);

        }
        return newPage;
    }

    public PortletPage createFromGroups(PortletRequest req, List groups) {

        PortletPage newPage = null;
        PortletTabbedPane pane = null;
        try {

            //newPage = (PortletPage)templatePage.clone();
            newPage = (PortletPage)deepCopy(templatePage);
            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();

            PortletManager manager = PortletManager.getInstance();
            List webappNames = manager.getPortletWebApplicationNames();

            for (int i = 0; i < webappNames.size(); i++) {
                String webappName = (String)webappNames.get(i);

                Iterator it = groups.iterator();
                while (it.hasNext()) {
                    PortletGroup g = (PortletGroup)it.next();
                    if (g.getName().equals(webappName)) {
                        log.debug("have group: " + g.getName());
                        PortletTabbedPane portletTabs = PortletTabRegistry.getApplicationTabs(webappName);
                        List tabs = portletTabs.getPortletTabs();
                        for (int j = 0; j < tabs.size(); j++) {
                            PortletTab tab = (PortletTab)tabs.get(j);

                            //pane.addTab(g.getName(), (PortletTab)tab.clone());
                            pane.addTab((PortletTab)deepCopy(tab));
                        }
                    }
                }
            }

            newPage.setPortletTabbedPane(pane);
            //newPage = (PortletPage)templatePage;
        } catch (Exception e) {
            log.error("Unable to make a clone of the templatePage", e);

        }
        newPage.init(req, new ArrayList());
        return newPage;
    }
            /*
    public PortletPage createFromNewUserLayoutXML(PortletRequest req) {
        PortletPage page = null;
        //String sessionId = req.getSession().getId();
        User user = req.getUser();

        try {
            //page = (PortletPage)templatePage.clone();
            page = (PortletPage)deepCopy(templatePage);
             List groups = (List)req.getAttribute(SportletProperties.PORTLETGROUPS);
                PortletPage newuserPage = createFromGroups(groups);
            PortletTabbedPane tabs = PortletLayoutDescriptor.loadPortletTabs(userLayoutPath, layoutMappingFile);
            page.setPortletTabbedPane(tabs);
            page.setPortletHeader(templatePage.getPortletHeader());
            page.setPortletFooter(templatePage.getPortletFooter());
            page.setLayoutDescriptor(userLayoutPath);
            page.init(new ArrayList());

            //sessionManager.addSessionListener(sessionId, this);

            } catch (Exception e) {
            log.error("Unable to create user layout: ", e);
            }

            return page;

            }
            */



    public PortletPage createPortletPage(PortletRequest req) {

        String sessionId = req.getPortletSession().getId();
        User user = req.getUser();

        log.debug("User requesting layout: " + user.getUserName());

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

            String userLayout = userLayoutDir + File.separator + user.getID();

            File f = new File(userLayout);
            // try {

            if (f.exists()) {
                //page = (PortletPage)deepCopy(templatePage);
                //page.setLayoutDescriptor(userLayout);

                try {
                    page = PortletLayoutDescriptor.loadPortletPage(userLayout, layoutMappingFile);
                    //page.setPortletTabbedPane(pane);
                    page.init(req, new ArrayList());
                } catch (Exception e) {
                    page = createNewPage(req);
                }
            } else {
                page = createNewPage(req);
            }
            userLayouts.put(sessionId, page);
            sessionManager.addSessionListener(sessionId, this);
            //} catch (Exception e) {
            //    log.error("Unable to create new user layout", e);
            //}
        }
        return page;
    }


    public PortletPage createPortletPage(User user) {

        log.debug("User requesting layout: " + user.getUserName());

        PortletPage page = null;

        String userLayout = userLayoutDir + File.separator + user.getID();

        File f = new File(userLayout);
        // try {

        if (f.exists()) {
            //page = (PortletPage)deepCopy(templatePage);
            //page.setLayoutDescriptor(userLayout);

            try {
                page = PortletLayoutDescriptor.loadPortletPage(userLayout, layoutMappingFile);
            } catch (Exception e) {
                log.error("unable to get user layout");
            }
        } else {
            page = createGridSpherePage();
            page.setLayoutDescriptor(userLayout);
            try {
                page.save();
            } catch (IOException e) {
                log.error("unable to save layout", e);
            }
        }

        return page;
    }

    public PortletPage createNewPage(PortletRequest req) {
        // is user a SUPER?
        PortletPage page = null;
        PortletRole role = req.getRole();

       /* if (role.equals(PortletRole.SUPER)) {

            page = createFromAllWebApps(req);


        }  else {   */

            List groups = (List)req.getAttribute(SportletProperties.PORTLETGROUPS);
            page = createFromGroups(req, groups);

       // }

        // save user's layout
        try {
            String userLayout = userLayoutDir + File.separator + req.getUser().getID();
            page.setLayoutDescriptor(userLayout);

            page.save();
        } catch (IOException e) {
            log.error("Unable to save users layout!", e);
        }

        return page;
    }

    public void destroyPortletPage(PortletRequest req) {
        PortletSession session = req.getPortletSession();

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
        String id = session.getId();
        if (userLayouts.containsKey(id)) {
            userLayouts.remove(id);
        }
        log.debug("removed user layout: " + userLayout);
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
          //String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");
     /*
         try {
            PortletLayoutDescriptor.savePortletPage((PortletPage)ro, "/tmp/test.xml", layoutMappingFile);
        } catch (PersistenceManagerException e) {
            throw new IOException("Unable to save user's tabbed pane: " + e.getMessage());
        }
      */
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
