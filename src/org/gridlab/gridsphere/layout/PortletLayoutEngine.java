/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>PortletLayoutEngine</code> is a singleton that is responsible for managing
 * user's layouts. It also manages portlet web application default layout
 * configurations that can be potentially added to a user layout
 * via the PortletLayout Service.
 * <p>
 * The portlet layout engine is a higher level manager of portlet containers
 * that represent a users customized layout. The portlet layout engine is used
 * by the {@link org.gridlab.gridsphere.portletcontainer.GridSphereServlet}
 * and the Portlet Layout Service that will
 * manage the customization of users' layouts. To be implemented...
 * Expect the PortletLayoutEngine methods to change possibly....
 */
public class PortletLayoutEngine {

    protected static PortletLog log = SportletLog.getInstance(PortletLayoutEngine.class);

    private static PortletLayoutEngine instance = new PortletLayoutEngine();

    private String layoutMappingFile = null;

    private PortletContainer guestContainer;

    private String newuserLayoutPath;
    private PortletContainer newuserContainer;

    private String userLayoutDir = GridSphereConfig.getProperty(GridSphereConfigProperties.GRIDSPHERE_LAYOUT_DIR);

    private String error = "";

    // Store user layouts in a hash
    private Map userLayouts = new HashMap();

    // Store application tabs in a hash
    private Map applicationTabs = new HashMap();

    /**
     * Constructs a concrete instance of the PortletLayoutEngine
     */
    private PortletLayoutEngine() {
    }

    /**
     * Returns the single instance of the PortletLayoutEngine
     *
     * @return the PortletLayoutEngine instance
     */
    public static PortletLayoutEngine getInstance() {
        return instance;
    }

    public void addApplicationTab(String webAppName, String tabXMLfile) {
        /*
        try {
            PortletTab webAppTab = PortletLayoutDescriptor.loadPortletTab(tabXMLfile, layoutMappingPath);
            applicationTabs.put(webAppName, webAppTab);
        } catch (Exception e) {
            Don't worry- already logged
        }
        */
    }

    public void removeApplicationTab(String webAppName) {
        applicationTabs.remove(webAppName);
    }

    /**
     * Initializes the portlet layout engine by loading in the defined Guest
     * user layout and new user layout templates specified in
     * {@link GridSphereConfigProperties}
     *
     * @throws IOException if an I/O error occurs during the template layout loading
     * @throws DescriptorException if a descriptor parsing error occurs
     */
    public void init() throws IOException, DescriptorException {

        File layDir = new File(userLayoutDir);
        if (!layDir.exists()) {
            layDir.mkdir();
        }

        layoutMappingFile = GridSphereConfig.getProperty(GridSphereConfigProperties.GRIDSPHERE_LAYOUT_MAPPING);

        String guestLayoutFile = GridSphereConfig.getProperty(GridSphereConfigProperties.GRIDSPHERE_GUEST_USER_LAYOUT);

        guestContainer = PortletLayoutDescriptor.loadPortletContainer(guestLayoutFile, layoutMappingFile);
        guestContainer.init(new ArrayList());

        newuserLayoutPath = GridSphereConfig.getProperty(GridSphereConfigProperties.GRIDSPHERE_NEW_USER_LAYOUT);

        newuserContainer = PortletLayoutDescriptor.loadPortletContainer(newuserLayoutPath, layoutMappingFile);
        newuserContainer.init(new ArrayList());
    }

    public void removeUser(User user) {
        System.err.println("REMOVING USER: " + user.getFullName());
        if (userLayouts.containsKey(user)) {
            userLayouts.remove(user);
        }
    }

    protected PortletContainer getPortletContainer(GridSphereEvent event) throws PortletLayoutException {
        // if user is guest then use guest template
        PortletContainer pc = null;

        User user = event.getPortletRequest().getUser();

        if (user instanceof GuestUser) {
            return guestContainer;

            // Check if we have user's layout already
        } else if (userLayouts.containsKey(user)) {

            pc = (PortletContainer) userLayouts.get(user);
            // If not we try to load it in (creating new one if necessary)
        } else {
            try {
                pc = createNewUserLayout(user);
                pc.init(new ArrayList());
                pc.loginPortlets(event);
            } catch (Exception e) {
                log.error("Unable to loadUserLayout for user: " + user, e);
                throw new PortletLayoutException("Unable to deserialize user layout from layout descriptor: " + e.getMessage());
            }
            userLayouts.put(user, pc);
        }
        return pc;
    }

    /**
     * Returns a portlet container for the supplied user
     *
     * @param user the User
     */
    public PortletContainer getPortletContainer(User user) {
        if (user instanceof GuestUser) return guestContainer;
        return (PortletContainer) userLayouts.get(user);
    }

    /**
     * Services a portlet container instance by rendering its presentation
     *
     * @param event the gridsphere event
     * @throws IOException if an I/O error occcurs during processing
     */
    public void service(GridSphereEvent event) throws IOException {
        log.debug("in service()");
        PortletContainer pc = null;

        // XXX: How do we signal a user has logged out so we can userLayouts.remove(user)???
        // XXX By jove I've got it! Via loggedIn method of UserService which in turn creates a hash of something

        try {
            pc = getPortletContainer(event);
            pc.doRender(event);
        } catch (PortletLayoutException e) {
            error = e.getMessage();
            //req.logRequest();
            log.error("Caught LayoutException: ", e);
        }
    }

    public void setPortletContainer(User user, PortletContainer container) {

    }

    public void reloadPortletContainer(User user) {

    }

    /**
     * Invoked by the GridSphereServlet to perform portlet login of a users layout
     *
     * @param event the gridsphere event
     * @see org.gridlab.gridsphere.layout.PortletContainer#loginPortlets
     */
    public void loginPortlets(GridSphereEvent event) {
        log.debug("in loginPortlets()");
        /*
        try {
            PortletContainer pc = getPortletContainer(event);
            pc.loginPortlets(event);
        } catch (PortletException e) {
            log.error("Unable to login portlets", e);
        }
        */
    }

    /**
     * Invoked by the GridSphereServlet to perform portlet logout of a users layout
     * Currently does nothing
     *
     * @param event the gridsphere event
     * @see PortletContainer#logoutPortlets
     */
    public void logoutPortlets(GridSphereEvent event) throws IOException {
        log.debug("in logoutPortlets()");
        try {
            PortletContainer pc = getPortletContainer(event);
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
        log.debug("in service()");
        PortletContainer pc = null;

        // XXX: How do we signal a user has logged out so we can userLayouts.remove(user)???
        try {
            pc = getPortletContainer(event);
            pc.actionPerformed(event);
        } catch (PortletLayoutException e) {
            error = e.getMessage();
            log.error("Caught LayoutException: ", e);
        }
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

    protected PortletContainer createNewUserLayout(User user) throws DescriptorException, IOException {

        String layoutPath = getUserLayoutPath(user);

        File f = new File(layoutPath);

        // if no layout file exists for user, make new one from template
        if (!f.exists()) {
            f.createNewFile();
            copyFile(new File(newuserLayoutPath), f);
        }
        return PortletLayoutDescriptor.loadPortletContainer(layoutPath, layoutMappingFile);
    }

    public void saveUserLayout(User user) throws DescriptorException, IOException {

        PortletContainer pc = (PortletContainer) userLayouts.get(user);
        if (pc == null) {
            throw new DescriptorException("PortletLayout does not exist for user: " + user.getID());
        }
        String userLayoutPath = getUserLayoutPath(user);
        PortletLayoutDescriptor.savePortletContainer(pc, userLayoutPath, layoutMappingFile);
    }

    protected String getUserLayoutPath(User user) {
        return userLayoutDir + "/" + user.getID();
    }

    protected void copyFile(File oldFile, File newFile) throws IOException {
        // Destination and streams
        log.info("in copyFile(): oldFile: " + oldFile.getAbsolutePath() + " newFile: " + newFile.getCanonicalPath());
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
