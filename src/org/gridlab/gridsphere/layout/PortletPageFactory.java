package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;

import java.io.*;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletPageFactory implements PortletSessionListener {

    private String userLayoutDir = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR);

    private static PortletPageFactory instance = null;
    private static PortletSessionManager sessionManager = PortletSessionManager.getInstance();

    private static PortletLog log = SportletLog.getInstance(PortletPageFactory.class);

    private String layoutMappingFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_MAPPING);

    private PortletPage templatePage = null;
    private PortletPage guestPage = null;
    private PortletPage newuserPage = null;
    private PortletPage errorPage = null;

    private String templateLayoutPath = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR) + "/TemplateLayout.xml";

    private String newuserLayoutPath = null;

    // Store user layouts in a hash
    private Map userLayouts = new Hashtable();

    private Map guests = new Hashtable();

    private PortletPageFactory() throws IOException, PersistenceManagerException {
        String errorLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR) + "/ErrorLayout.xml";

        newuserLayoutPath = GridSphereConfig.getProperty(GridSphereConfigProperties.NEW_USER_LAYOUT);


        errorPage = PortletLayoutDescriptor.loadPortletPage(errorLayoutFile, layoutMappingFile);
        errorPage.init(new ArrayList());

        templatePage = PortletLayoutDescriptor.loadPortletPage(templateLayoutPath, layoutMappingFile);
        errorPage.setLayoutDescriptor(errorLayoutFile);

        reloadGuestUserLayout();
    }

    public void reloadGuestUserLayout() throws IOException, PersistenceManagerException {
        String guestLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.GUEST_USER_LAYOUT);
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

    public PortletPage createFromNewUserLayoutXML(PortletRequest req) {
        PortletPage page = null;
        String sessionId = req.getSession().getId();
        User user = req.getUser();
        String userLayoutPath = userLayoutDir + user.getID();
        File userLayoutFile = new File(userLayoutPath);
        try {
            page = (PortletPage)templatePage.clone();

            if (!userLayoutFile.exists()) {

                userLayoutFile.createNewFile();

                File newUserLayoutFile = new File(newuserLayoutPath);
                //if (!newfile.exists()) {

                newUserLayoutFile.createNewFile();
                newuserPage = createFromAllWebApps();

                newuserPage.setLayoutDescriptor(newuserLayoutPath);
                newuserPage.save();

                copyFile(newUserLayoutFile, userLayoutFile);
                // }
            }

            PortletTabbedPane tabs = PortletLayoutDescriptor.loadPortletTabs(userLayoutPath, layoutMappingFile);
            page.setPortletTabbedPane(tabs);
            page.setPortletHeader(templatePage.getPortletHeader());
            page.setPortletFooter(templatePage.getPortletFooter());
            page.setLayoutDescriptor(userLayoutPath);
            page.init(new ArrayList());

            sessionManager.addSessionListener(sessionId, this);

        } catch (Exception e) {
            log.error("Unable to create user layout: ", e);
        }

        return page;

    }

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
                    userLayouts.put(page, sessionId);
                } catch (Exception e) {
                    log.error("Unable to clone layout: ", e);
                }
            }  else {
                try {
                    page = createFromNewUserLayoutXML(req);
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

}
