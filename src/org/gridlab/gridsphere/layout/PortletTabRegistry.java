package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;

import java.io.*;
import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class PortletTabRegistry {

    protected static final PortletLog log = SportletLog.getInstance(PortletTabRegistry.class);

    private static String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");

    private static String groupLayoutDir = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/CustomPortal/layouts/groups");

    private static String guestLayoutFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/CustomPortal/layouts/GuestUserLayout.xml");


    private static Map tabDescriptors = new Hashtable();
    private static Map groupTabs = new Hashtable();
    private static PortletPage guestPage = null;


    static {

        try {
            File f = new File(groupLayoutDir);
            if (!f.exists()) {
                f.mkdir();
            } else {
                String[] files = f.list();
                for (int i = 0; i < files.length; i++) {
                    String group = files[i];
                    int idx = group.indexOf(".xml");
                    String groupOid = group.substring(0, idx);
                    String groupFile = groupLayoutDir + File.separator + groupOid + ".xml";
                    tabDescriptors.put(groupOid, groupFile);

                    PortletTabbedPane tabbedPane = PortletLayoutDescriptor.loadPortletTabs(groupFile, layoutMappingFile);
                    groupTabs.put(groupOid, tabbedPane);
                }
            }
        } catch (Exception e) {
            log.error("Unable to load group layout files!!");
            e.printStackTrace();
        }
    }

    private PortletTabRegistry() {
    }

    public synchronized static void addGroupTab(PortletGroup group, String tabXMLfile) throws IOException, PersistenceManagerException {
        PortletTabbedPane webAppTabs = PortletLayoutDescriptor.loadPortletTabs(tabXMLfile, layoutMappingFile);
        tabDescriptors.put(group.getOid(), tabXMLfile);
        groupTabs.put(group.getOid(), webAppTabs);
    }

    public static void reloadGuestLayout() throws IOException, PersistenceManagerException {
        guestPage = PortletLayoutDescriptor.loadPortletPage(guestLayoutFile, layoutMappingFile);
        guestPage.setLayoutDescriptor(guestLayoutFile);
    }

    public static void loadPage(String file) throws IOException, PersistenceManagerException {
        PortletLayoutDescriptor.loadPortletPage(file, layoutMappingFile);
    }

    public static void loadTab(String file) throws IOException, PersistenceManagerException {
        PortletLayoutDescriptor.loadPortletTabs(file, layoutMappingFile);
    }

    public static PortletPage getGuestLayoutPage() {
        if (guestPage == null) {
            try {
                reloadGuestLayout();
            } catch (Exception e) {
                log.error("Unable to reload guest user layout", e);
            }
        }
        return guestPage;
    }

    public static String getGuestLayoutFile() {
        return guestLayoutFile;
    }

    public static PortletTabbedPane getGroupTabs(PortletGroup group) {
        return (PortletTabbedPane) groupTabs.get(group.getOid());
    }


    public static void newEmptyGroupTab(PortletGroup group) {
        if (tabDescriptors.get(group.getOid()) == null) {
            File f = new File(groupLayoutDir);
            if (!f.exists()) {
                f.mkdir();
            }
            String groupFile = groupLayoutDir + File.separator + group.getOid() + ".xml";
            tabDescriptors.put(group.getOid(), groupFile);
        }
    }

    public static void newTemplateGroupTab(PortletGroup group, Set portletRoleInfo) throws IOException, PersistenceManagerException {
        File f = new File(groupLayoutDir);
        if (!f.exists()) {
            f.mkdir();
        }
        String groupFile = groupLayoutDir + File.separator + group.getOid() + ".xml";
        tabDescriptors.put(group.getOid(), groupFile);
        PortletTabbedPane parentPane = new PortletTabbedPane();
        Iterator it = portletRoleInfo.iterator();
        String portletClass;
        PortletRole reqRole = null;
        PortletTab parentTab = new PortletTab();
        parentTab.setTitle(group.getName());
        //parentTab.setLabel(groupName);

        PortletTabbedPane childPane = new PortletTabbedPane();
        childPane.setStyle("sub-menu");
        parentTab.setPortletComponent(childPane);
        PortletTab pTab = null;
        PortletFrame pFrame = null;
        while (it.hasNext()) {
            SportletRoleInfo info = (SportletRoleInfo) it.next();
            portletClass = info.getPortletClass();

            //System.err.println("portletclass = " + portletClass);
            reqRole = info.getSportletRole();
            pTab = new PortletTab();
            PortletRegistry registry = PortletRegistry.getInstance();

            String appID = PortletRegistry.getApplicationPortletID(portletClass);
            //System.err.println("appID = " + appID);
            ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
            ConcretePortlet conc = appPortlet.getConcretePortlet(portletClass);

            String tabName = conc.getDisplayName(Locale.ENGLISH);
            pTab.setTitle("en", tabName);
            if (reqRole != null) pTab.setRequiredRole(reqRole.getName());
            PortletTableLayout table = new PortletTableLayout();
            PortletRowLayout row = new PortletRowLayout();
            PortletColumnLayout col = new PortletColumnLayout();

            pFrame = new PortletFrame();

            pFrame.setPortletClass(conc.getConcretePortletID());
            col.addPortletComponent(pFrame);
            row.addPortletComponent(col);
            table.addPortletComponent(row);

            pTab.setPortletComponent(table);

            childPane.addTab(pTab);
        }
        parentPane.addTab(parentTab);
        PortletLayoutDescriptor.savePortletTabbedPane(parentPane, groupFile, layoutMappingFile);
        groupTabs.put(group.getOid(), parentPane);
    }

    public static synchronized void reloadTab(String tab, PortletGroup group) throws PersistenceManagerException {
        try {
            PortletTabbedPane pane = PortletLayoutDescriptor.loadPortletTabs(group.getOid(), layoutMappingFile);
            if (groupTabs.containsKey(tab)) {
                groupTabs.put(tab, pane);
            }
            /*
            if (applicationTabs.containsKey(tab)) {
                applicationTabs.put(tab, pane);
            }*/
        } catch (IOException e) {
            log.error("Unable to reload tab: " + tab, e);
        }
    }

    
    public static Map getGroupTabs() {
        return Collections.unmodifiableMap(groupTabs);
    }


    public synchronized static void removeGroupTab(PortletGroup group) {
        String groupFile = (String) tabDescriptors.get(group.getOid());
        if (groupFile != null) {
            File f = new File(groupFile);
            if (f.exists()) f.delete();
            tabDescriptors.remove(group.getOid());
        }
        groupTabs.remove(group.getOid());
        //applicationTabs.remove(groupName);
    }

    public static String getTabDescriptorPath(PortletGroup group) {
        return (String) tabDescriptors.get(group.getOid());
    }

    public static void copyFile(File in, PortletGroup group) throws Exception {

        // copy over group tabs if they don't exist
        String tabDesc = groupLayoutDir + File.separator + group.getOid() + ".xml";
        File out = new File(tabDesc);

  
        if (!out.exists()) {
            copyFile(in, out);
        } else if (in.lastModified() > out.lastModified()) {
            // copy over file if it is more recent than existing one
            copyFile(in, out);
        }
        addGroupTab(group, tabDesc);
    }

    public static void copyFile(File in, File out) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(in), "UTF-8"));

        //while (line == (reader.))
        //File out = new File(groupLayoutDir + File.separator + groupName);

        //FileInputStream fis = new FileInputStream(in);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(out), "UTF-8");
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter())
        char[] buf = new char[1024];
        int i = 0;
        while ((i = reader.read(buf)) != -1) {
            writer.write(buf, 0, i);
        }
        //fis.close();
        //fos.close();
        reader.close();
        writer.close();

    }


}
