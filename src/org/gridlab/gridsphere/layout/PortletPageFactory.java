package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;

import java.io.*;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletPageFactory {

    private String userLayoutDir = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR);

    private static PortletPageFactory instance = null;
    private static PortletLog log = SportletLog.getInstance(PortletPageFactory.class);

    private static int MAX_GUEST_CONTAINERS = 50;
    private String layoutMappingFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_MAPPING);

    private PortletPage guestPage = null;
    private PortletPage errorPage = null;
    private PortletPage templatePage = null;
    private static int counter = 0;
    private String newuserLayoutPath;

    // Store user layouts in a hash
    private Map userLayouts = new HashMap();

    private Map guests = new HashMap();

    private PortletPageFactory() throws IOException, PersistenceManagerException {
        String errorLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR) + "/ErrorLayout.xml";
        String templateLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR) + "/TemplateLayout.xml";

        newuserLayoutPath = GridSphereConfig.getProperty(GridSphereConfigProperties.NEW_USER_LAYOUT);
        String guestLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.GUEST_USER_LAYOUT);

        errorPage = PortletLayoutDescriptor.loadPortletPage(errorLayoutFile, layoutMappingFile);
        guestPage = PortletLayoutDescriptor.loadPortletPage(guestLayoutFile, layoutMappingFile);
        templatePage = PortletLayoutDescriptor.loadPortletPage(templateLayoutFile, layoutMappingFile);
        errorPage.init(new ArrayList());

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

    public PortletPage createFromAllWebApps(PortletRequest req) {

        PortletPage newPage = null;
        PortletTab newTab = null;
        PortletTabbedPane pane = null;

        try {
            newPage = (PortletPage)templatePage.clone();
            pane = templatePage.getPortletTabbedPane();
            PortletManager manager = PortletManager.getInstance();
            List webappNames = manager.getPortletWebApplicationNames();
            for (int i = 0; i < webappNames.size(); i++) {
                String name = (String)webappNames.get(i);
                PortletTabbedPane portletTabs = PortletTabRegistry.getApplicationTabs(name);
                List tabs = portletTabs.getPortletTabs();
                for (int j = 0; j < tabs.size(); j++) {
                    PortletTab tab = (PortletTab)tabs.get(j);
                    newTab = (PortletTab)tab.clone();
                    pane.addTab(name, newTab);
                }
            }
            newPage.setPortletTabbedPane(pane);
        } catch (CloneNotSupportedException e) {
            log.error("Unable to make a clone of the templatePage", e);

        }
        return newPage;
    }

    public PortletPage createFromNewUserLayoutXML(PortletRequest req) throws PersistenceManagerException, IOException {
        User user = req.getUser();
        String layoutPath = userLayoutDir + user.getID();
        File f = new File(layoutPath);
        if (!f.exists()) {
            f.createNewFile();
            copyFile(new File(newuserLayoutPath), f);
        }
        PortletPage page = PortletLayoutDescriptor.loadPortletPage(layoutPath, layoutMappingFile);
        userLayouts.put(user, page);
        return page;

    }

    public void removeUser(User user) {
        if (userLayouts.containsKey(user)) {
            userLayouts.remove(user);
        }
    }

    public PortletPage createPortletPage(PortletRequest req) {
        User user = req.getUser();

        if (user instanceof GuestUser) {
            return createFromGuestLayoutXML(req);
        }

        // Need to provide one guest container per users session
        if (userLayouts.containsKey(user)) {
            return (PortletPage) userLayouts.get(user);
        } else {

            // is user a SUPER?
            PortletRole role = req.getRole();
            if (role.equals(PortletRole.SUPER)) {

                PortletPage page = createFromAllWebApps(req);
                userLayouts.put(user, page);
                return page;
            }

            try {
                // For now users also get ALL web app layouts
                PortletPage page = createFromAllWebApps(req);
                userLayouts.put(user, page);
                return page;
                //return createFromNewUserLayoutXML(req);
            } catch (Exception e) {
                log.info("Using Guest Layout for " + req.getUser().getUserName());
            }
        }
        return createFromGuestLayoutXML(req);
    }

    public PortletPage createFromGuestLayoutXML(PortletRequest req) {
        PortletSession session = req.getPortletSession(true);
        String id = session.getId();
        if (guests.containsKey(id)) {
            return (PortletPage)guests.get(id);
        } else {
            PortletPage newcontainer = null;
            try {

                synchronized (new Integer(counter)) {
                    counter = (counter >= MAX_GUEST_CONTAINERS) ? 0 : counter++;
                    //newcontainer = (PortletPage)guestContainer.clone();
                    newcontainer = guestPage;
                }
                newcontainer.init(new ArrayList());
                guests.put(id, newcontainer);
            } catch (Exception e) {
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

}
