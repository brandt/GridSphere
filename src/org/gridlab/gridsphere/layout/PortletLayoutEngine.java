/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Hashtable;
import java.util.Map;

public class PortletLayoutEngine {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletLayoutEngine.class);
    private static PortletLayoutEngine instance = new PortletLayoutEngine();
    private static ServletContext context = null;

    private GridSphereConfig gsConfig = null;

    private PortletLayoutDescriptor templateLayout = null;
    private String templateLayoutPath, layoutMappingPath;
    private PortletLayoutDescriptor guestLayout = null;
    private String layoutConfigPath = null;

    private String error = "";

    private Map userLayouts = new Hashtable();

    private PortletLayoutEngine() {}

    public static PortletLayoutEngine getInstance() {
        return instance;
    }

    public void init() throws IOException, PortletLayoutDescriptorException {
        gsConfig = GridSphereConfig.getInstance();
        String layoutMappingPath = gsConfig.getProperty(GridSphereConfigProperties.LAYOUT_MAPPING_XML);
        layoutConfigPath =  gsConfig.getProperty(GridSphereConfigProperties.LAYOUT_XML);
        guestLayout = new PortletLayoutDescriptor(layoutConfigPath, layoutMappingPath);
    }

    public PortletContainer getPortletContainer(User user) throws PortletLayoutException {
        // if user is guest then use guest template
        PortletContainer pc = null;

        if (user instanceof GuestUser) {
            pc = guestLayout.getPortletContainer();

            // Check if we have user's layout already
        } else if (userLayouts.containsKey(user)) {

            pc = (PortletContainer)userLayouts.get(user);

            // If not we try to load it in (creating new one if necessary)
        } else {

            try {
                PortletLayoutDescriptor pld = loadUserLayout(user);
                pc = pld.getPortletContainer();
            } catch (IOException e) {
                log.error("Unable to loadUserLayout for user: " + user, e);
                throw new PortletLayoutException("Unable to deserailize user layout from layout descriptor: " + e.getMessage());
            }
            userLayouts.put(user, pc);
        }
        return pc;
    }

    public void doRender(User user, ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException {
        log.debug("in doRender()");

        PortletContainer pc = null;

        pc = getPortletContainer(user);

        // XXX: How do we signal a user has logged out so we can userLayouts.remove(user)???

        try {
            pc.doRenderFirst(ctx, req, res);
            pc.doRenderLast(ctx, req, res);
        } catch (IOException e) {
            error = e.getMessage();
            log.error("Caught IOException: ", e);
            throw new PortletLayoutException("Caught IOException", e);
        }
    }

    public void doRenderError(PortletRequest req, PortletResponse res, Throwable t) {
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            log.error("in doRenderError: ", e);
        }
        out.println("<h>Portlet Layout Engine unable to render!</h>");
        out.println("<b>" + error + "</b>");
    }

    protected PortletLayoutDescriptor loadUserLayout(User user) throws PortletLayoutDescriptorException, IOException {
        // load in layout.xml file
        String userLayoutDir = gsConfig.getProperty(GridSphereConfigProperties.USER_LAYOUT_DIR);
        String layoutMappingFile = gsConfig.getProperty(GridSphereConfigProperties.LAYOUT_MAPPING_XML);

        if ((userLayoutDir == null) || (layoutMappingFile == null)) {
            throw new PortletLayoutDescriptorException("Unable to get user layout directory info from web.xml. Please specify user-layouts-dir in web.xml.");
        }
        File layDir = new File(userLayoutDir);
        if (!layDir.exists()) {
            layDir.mkdir();
        }

        String layoutPath = userLayoutDir + "/" + user.getID();
        File f = new File(layoutPath);

        // if no layout file exists for user, make new one from template
        if (!f.exists()) {
            f.createNewFile();
            copyFile(new File(layoutConfigPath), f);
        }

        PortletLayoutDescriptor userLayout = new PortletLayoutDescriptor(layoutPath, layoutMappingFile);

        return userLayout;
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
