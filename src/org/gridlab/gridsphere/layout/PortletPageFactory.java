package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
        errorPage.init(new ArrayList());

        templatePage = PortletLayoutDescriptor.loadPortletPage(templateLayoutPath, layoutMappingFile);
        errorPage.setLayoutDescriptor(errorLayoutFile);
        /*
        File userdir = new File(newuserLayoutPath);
        if (!userdir.exists()) {
            userdir.mkdir();
        }
        */
        reloadGuestUserLayout();
    }

    public void reloadGuestUserLayout() throws IOException, PersistenceManagerException {
        String guestLayoutFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/layouts/GuestUserLayout.xml");

        guestPage = PortletLayoutDescriptor.loadPortletPage(guestLayoutFile, layoutMappingFile);
        guestPage.setLayoutDescriptor(guestLayoutFile);
    }

    public static PortletPageFactory getInstance() throws IOException, PersistenceManagerException {
        if (instance == null) {
            instance = new PortletPageFactory();
        }
        return instance;
    }

    public PortletPage createErrorPage() {
        return errorPage;
    }

    public void login(PortletRequest request) {

    }

    public void logout(PortletSession session) {
        log.debug("in logout PortlretPageFactory");
        String sessionId = session.getId();
        if (guests.containsKey(sessionId)) {
            log.debug("Removing guest container for:" + sessionId);
            guests.remove(sessionId);
        }
        if (userLayouts.containsKey(sessionId)) {
            log.debug("Removing user  container for:" + sessionId);
            userLayouts.remove(sessionId);
        }
    }

    public PortletPage createFromAllWebApps() {

        PortletPage newPage = null;
        PortletTabbedPane pane = null;
        try {

            newPage = (PortletPage)templatePage.clone();
            //newPage = (PortletPage)deepCopy(templatePage);
            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();

            PortletManager manager = PortletManager.getInstance();
            List webappNames = manager.getPortletWebApplicationNames();

            for (int i = 0; i < webappNames.size(); i++) {
                String name = (String)webappNames.get(i);

                PortletTabbedPane portletTabs = PortletTabRegistry.getApplicationTabs(name);

                List tabs = portletTabs.getPortletTabs();
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);

                    pane.addTab(name, (PortletTab)tab.clone());
                    //pane.addTab(name, (PortletTab)deepCopy(tab));
                }
            }

            newPage.setPortletTabbedPane(pane);
            //newPage = (PortletPage)templatePage;
        } catch (Exception e) {
          log.error("Unable to make a clone of the templatePage", e);

        }
        newPage.init(new ArrayList());

        return newPage;
    }

    public PortletPage createFromGroups(List groups) {

        PortletPage newPage = null;
        PortletTabbedPane pane = null;
        try {

            newPage = (PortletPage)templatePage.clone();
            //newPage = (PortletPage)deepCopy(templatePage);
            log.debug("Returning cloned layout from webapps:");

            pane = newPage.getPortletTabbedPane();

            for (int i = 0; i < groups.size(); i++) {
                PortletGroup g = (PortletGroup)groups.get(i);
                log.debug("have group: " + g.getName());
                PortletTabbedPane portletTabs = PortletTabRegistry.getApplicationTabs(g.getName());

                List tabs = portletTabs.getPortletTabs();
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);

                    pane.addTab(g.getName(), (PortletTab)tab.clone());
                    //pane.addTab(g.getName(), (PortletTab)deepCopy(tab));
                }
            }

            newPage.setPortletTabbedPane(pane);
            //newPage = (PortletPage)templatePage;
        } catch (Exception e) {
          log.error("Unable to make a clone of the templatePage", e);

        }
        newPage.init(new ArrayList());
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

        log.debug("number of guest layouts: " + guests.size());
        log.debug("number of user layouts: " + userLayouts.size());

        String sessionId = req.getSession().getId();
        User user = req.getUser();

        if (user instanceof GuestUser) {
            return createFromGuestLayoutXML(req);
        }

        // Need to provide one guest container per users session
        if (userLayouts.containsKey(sessionId)) {
            log.debug("Returning existing layout for:" + sessionId);
            return (PortletPage) userLayouts.get(sessionId);
        } else {

            // Now the user is user so remove guest layout
            if (guests.containsKey(sessionId)) {
                log.debug("Removing guest container for:" + sessionId);
                guests.remove(sessionId);
            }

            PortletPage page = null;
            // is user a SUPER?
            PortletRole role = req.getRole();
            if (role.equals(PortletRole.SUPER)) {
                try {
                    page = createFromAllWebApps();
                    userLayouts.put(sessionId, page);
                    sessionManager.addSessionListener(sessionId, this);
                } catch (Exception e) {
                    log.error("Unable to clone layout: ", e);
                }
            }  else {
                try {
                    List groups = (List)req.getAttribute(SportletProperties.PORTLETGROUPS);
                    page = createFromGroups(groups);
                    userLayouts.put(sessionId, page);
                    sessionManager.addSessionListener(sessionId, this);
                } catch (Exception e) {
                    log.error("Unable to create new user layout", e);
                }
            }

            return page;
        }
    }

    public PortletPage createFromGuestLayoutXML(PortletRequest req) {
        PortletSession session = req.getPortletSession(true);
        String id = session.getId();
        if ((id != null) && (guests.containsKey(id))) {
            return (PortletPage)guests.get(id);
        } else {
            PortletPage newcontainer = null;
            try {
                newcontainer = (PortletPage)guestPage.clone();
                //newcontainer = (PortletPage)deepCopy(guestPage);
                newcontainer.init(new ArrayList());
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
}
