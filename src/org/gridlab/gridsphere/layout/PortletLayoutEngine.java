/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;

import java.io.*;
import java.util.*;

/**
 * The <code>PortletLayoutEngine</code> is a singleton that is responsible for managing
 * user's layouts. It also manages portlet web application default layout
 * configurations that can be potentially added to a user layout
 * via the PortletLayout Service.
 * <p>
 * The portlet layout engine is a higher level manager of portlet containers
 * that represent a users customized layout. The portlet layout engine is used
 * by the {@link org.gridlab.gridsphere.portletcontainer.GridSphereServlet}
 * Expect the PortletLayoutEngine methods to change possibly....
 */
public class PortletLayoutEngine {

    protected static PortletLog log = SportletLog.getInstance(PortletLayoutEngine.class);

    private static PortletLayoutEngine instance = new PortletLayoutEngine();
    private static int MAX_GUEST_CONTAINERS = 50;
    private String layoutMappingFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_MAPPING);

    private PortletPage guestContainer = null;
    private PortletPage errorContainer = null;
    private PortletPage templateContainer = null;
    private static int counter = 0;
    private String newuserLayoutPath;

    private String userLayoutDir = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR);

    private String error = "";

    // Store user layouts in a hash
    private Map userLayouts = new HashMap();

    // Store application tabs in a hash
    private  Map applicationTabs = new HashMap();

    private Map guests = new HashMap();

    /**
     * Constructs a concrete instance of the PortletLayoutEngine
     */
    private PortletLayoutEngine() {
        String errorLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR) + "/ErrorLayout.xml";
        String templateLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.LAYOUT_DIR) + "/TemplateLayout.xml";

        newuserLayoutPath = GridSphereConfig.getProperty(GridSphereConfigProperties.NEW_USER_LAYOUT);
        String guestLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.GUEST_USER_LAYOUT);
        try {
            errorContainer = PortletLayoutDescriptor.loadPortletContainer(errorLayoutFile, layoutMappingFile);
            guestContainer = PortletLayoutDescriptor.loadPortletContainer(guestLayoutFile, layoutMappingFile);
            templateContainer = PortletLayoutDescriptor.loadPortletContainer(templateLayoutFile, layoutMappingFile);
            errorContainer.init(new ArrayList());
        } catch (IOException e) {
            error = "Caught IOException trying to unmarshall GuestUserLayout.xml" + e.getMessage();
            log.error(error, e);
        } catch (PersistenceManagerException e) {
            error = "Caught PersistenceManagerException trying to unmarshall GuestUserLayout.xml" + e.getMessage();
            log.error(error, e);
        }
    }

    /**
     * Returns the single instance of the PortletLayoutEngine
     *
     * @return the PortletLayoutEngine instance
     */
    public static PortletLayoutEngine getInstance() {
        return instance;
    }

    public synchronized void addApplicationTab(String webAppName, String tabXMLfile) {
        try {
            PortletTab webAppTab = PortletLayoutDescriptor.loadPortletTab(tabXMLfile, layoutMappingFile);
            applicationTabs.put(webAppName, webAppTab);
        } catch (Exception e) {
            log.error("Unable to add: " + webAppName + " tab");
        }
    }

    public  synchronized void removeApplicationTab(String webAppName) {
        applicationTabs.remove(webAppName);
    }

    public void removeUser(User user) {
        System.err.println("REMOVING USER: " + user.getFullName());
        if (userLayouts.containsKey(user)) {
            userLayouts.remove(user);
        }
    }

    protected PortletPage getPortletContainer(GridSphereEvent event)  {
        // if user is guest then use guest template
        PortletPage pc = null;

        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();
        PortletSession session = req.getPortletSession(true);

        // Check for framework errors
        Exception portletException = (Exception)req.getAttribute(GridSphereProperties.ERROR);
        if (portletException != null) {
            return errorContainer;
        }

        // Need to provide one guest container per users session
        if (user instanceof GuestUser) {
            String id = session.getId();
            if (guests.containsKey(id)) {
                return (PortletPage)guests.get(id);
            } else {
                PortletPage newcontainer = null;
                try {

                    synchronized (new Integer(counter)) {
                        counter = (counter >= MAX_GUEST_CONTAINERS) ? 0 : counter++;
                        //newcontainer = (PortletPage)guestContainer.clone();
                        newcontainer = guestContainer;
                    }
                    newcontainer.init(new ArrayList());
                    guests.put(id, newcontainer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return newcontainer;
            }

            // Check if we have user's layout already
        }
        if (userLayouts.containsKey(user)) {

            pc = (PortletPage) userLayouts.get(user);
            // If not we try to load it in (creating new one if necessary)
        }
        return pc;
    }

    /**
     * Services a portlet container instance by rendering its presentation
     *
     * @param event the gridsphere event
     * @throws IOException if an I/O error occcurs during processing
     */
    public void service(GridSphereEvent event) throws IOException {
        log.debug("in service()");
        PortletPage pc = null;

        // XXX: How do we signal a user has logged out so we can userLayouts.remove(user)???
        // XXX By jove I've got it! Via loggedIn method of UserService which in turn creates a hash of something

        try {
            pc = getPortletContainer(event);
            int numcomps = pc.getComponentIdentifierList().size();
            if (event.getPortletComponentID() < 0 || event.getPortletComponentID() > numcomps) {
                event.getPortletRequest().setAttribute(GridSphereProperties.COMPONENT_ID, "0");
            }
            pc.doRender(event);
        } catch (PortletLayoutException e) {
            log.error("Caught LayoutException: ", e);
            doRenderError(event.getPortletRequest(), event.getPortletResponse(), e);
        }
    }

    public void setPortletContainer(User user, PortletPage container) {

    }

    public void reloadPortletContainer(User user) {

    }

    /**
     * Invoked by the GridSphereServlet to perform portlet login of a users layout
     *
     * @param event the gridsphere event
     * @see org.gridlab.gridsphere.layout.PortletPage#loginPortlets
     */
    public void loginPortlets(GridSphereEvent event) {
        log.debug("in loginPortlets()");
        User user = event.getPortletRequest().getUser();
        PortletPage pc = null;
        try {
            pc = createNewUserLayout(user);
            pc.init(new ArrayList());
            pc.loginPortlets(event);
        } catch (Exception e) {
            log.error("Unable to loadUserLayout for user: " + user, e);
            //throw new PortletLayoutException("Unable to deserialize user layout from layout descriptor: " + e.getMessage());
        }
        userLayouts.put(user, pc);
    }

    /**
     * Invoked by the GridSphereServlet to perform portlet logout of a users layout
     * Currently does nothing
     *
     * @param event the gridsphere event
     * @see PortletPage#logoutPortlets
     */
    public void logoutPortlets(GridSphereEvent event) throws IOException {
        log.debug("in logoutPortlets()");
        try {
            PortletPage pc = getPortletContainer(event);
            pc.logoutPortlets(event);
        } catch (PortletException e) {
            log.error("Unable to logout portlets", e);
        }
    }

    /**
     * Performs an action on the portlet container referenced by the
     * gridsphere event
     *
     * @param event a gridsphere event
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws IOException {
        log.debug("Entering actionPerformed()");
        if (log.isDebugEnabled()) {
            log.debug("Event component = " + event.getPortletComponentID());
            if (event.hasAction()) {
                DefaultPortletAction action = event.getAction();
                log.debug("Event action = " + action.toString());
            }
        }
        PortletPage pc = null;
        // XXX: How do we signal a user has logged out so we can userLayouts.remove(user)???
        try {
            pc = getPortletContainer(event);
            int numcomps = pc.getComponentIdentifierList().size();
            if (event.getPortletComponentID() < 0 || event.getPortletComponentID() > numcomps) {
                event.getPortletRequest().setAttribute(GridSphereProperties.COMPONENT_ID, "0");
            }
            pc.actionPerformed(event);
        } catch (PortletLayoutException e) {
            doRenderError(event.getPortletRequest(), event.getPortletResponse(), e);
            log.error("Caught LayoutException: ", e);
        }
        log.debug("Exiting actionPerformed()");
    }

    public void doRenderError(PortletRequest req, PortletResponse res, Throwable t) {
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            log.error("in doRenderError: ", e);
        }
        out.println("<h>Portlet PortletLayout Engine unable to render!</h>");
        out.println("<b>" + error + "</b>");
    }

    protected PortletPage createNewUserLayout(User user) throws PersistenceManagerException, IOException {
        System.err.println("in createNewUserL");
        /*
        PortletPage newContainer = null;
        PortletTab newTab = null;

        PortletTab coreTab = (PortletTab)applicationTabs.get("gridsphere");
        try {
            newContainer = (PortletPage)templateContainer.clone();
            newTab = (PortletTab)coreTab.clone();
            newContainer.getTabbedPane(newTab);

        } catch (CloneNotSupportedException e) {
            log.error("Unable to make a clone of the templateContainer", e);

        }
        System.err.println("made a clone!!!!!");
        return newContainer;
        */

        String layoutPath = getUserLayoutPath(user);
        File f = new File(layoutPath);
        if (!f.exists()) {
            f.createNewFile();
            copyFile(new File(newuserLayoutPath), f);
        }
        return PortletLayoutDescriptor.loadPortletContainer(layoutPath, layoutMappingFile);

    }

    public void saveUserLayout(User user) throws PersistenceManagerException, IOException {

        PortletPage pc = (PortletPage) userLayouts.get(user);
        if (pc == null) {
            throw new PersistenceManagerException("PortletLayout does not exist for user: " + user.getID());
        }
        String userLayoutPath = getUserLayoutPath(user);
        PortletLayoutDescriptor.savePortletContainer(pc, userLayoutPath, layoutMappingFile);
    }

    protected String getUserLayoutPath(User user) {
        return userLayoutDir + user.getID();
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

    private static Object deepCopy(Object oldObj) throws Exception {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try
        {
            ByteArrayOutputStream bos =
                    new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            // serialize and pass the object
            oos.writeObject(oldObj);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);
            // return the new object
            return ois.readObject();
        }
        catch(Exception e)
        {
            throw(e);
        }
        finally
        {
            oos.close();
            ois.close();
        }
    }

}
