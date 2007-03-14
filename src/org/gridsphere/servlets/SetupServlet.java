package org.gridsphere.servlets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.layout.PortletLayoutEngine;
import org.gridsphere.portlet.impl.PortletContextImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.portletcontainer.impl.GridSphereEventImpl;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.persistence.impl.CreateDatabase;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.UserManagerService;
import org.hibernate.StaleObjectStateException;

import javax.portlet.PortletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class SetupServlet extends HttpServlet {

    private Log log = LogFactory.getLog(SetupServlet.class);
    private PortletLayoutEngine layoutEngine = PortletLayoutEngine.getInstance();
    private RoleManagerService roleService = null;
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordService = null;
    private PortalConfigService portalConfigService = null;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PortletContext ctx = new PortletContextImpl(getServletContext());
        GridSphereEventImpl event = new GridSphereEventImpl(ctx, req, res);

        if (req.getAttribute("setup") == null) {
            redirect(event);
            return;
        }

        String error = (String) req.getSession(true).getAttribute("error");
        if (error != null) {
            req.setAttribute("error", error);
            req.getSession().removeAttribute("error");
        }
        // check current GS release and the DB meta file
        String release = SportletProperties.getInstance().getProperty("gridsphere.release");
        int idx = release.lastIndexOf(" ");
        String gsversion = release.substring(idx + 1);
        //System.err.println("gsversion=" + gsversion);

        String dbpath = getServletContext().getRealPath("/WEB-INF/CustomPortal/database");

        File dbdir = new File(dbpath);
        String[] filenames = dbdir.list();
        String currentVersion = null;
        for (int i = 0; i < filenames.length; i++) {
            if (filenames[i].startsWith("GS")) currentVersion = filenames[i];
        }

        setupRoles(event);

        File thisdbfile = new File(dbpath + File.separator + "GS_" + gsversion);

        // if meta file exists, redirect to the portal unless admin needs to be created
        if (thisdbfile.exists()) {
            roleService = (RoleManagerService) PortletServiceFactory.createPortletService(RoleManagerService.class, true);
            userManagerService = (UserManagerService) PortletServiceFactory.createPortletService(UserManagerService.class, true);
            passwordService = (PasswordManagerService) PortletServiceFactory.createPortletService(PasswordManagerService.class, true);
            portalConfigService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);

            PersistenceManagerService pms = null;

            pms = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
            List admins = null;
            PersistenceManagerRdbms pm = null;
            try {
                log.info("Starting a database transaction");

                pm = pms.createGridSphereRdbms();
                pm.beginTransaction();

                admins = roleService.getUsersInRole(PortletRole.ADMIN);


                log.info("Committing the database transaction");

                pm.endTransaction();
            } catch (StaleObjectStateException staleEx) {
                log.error("This interceptor does not implement optimistic concurrency control!");
                log.error("Your application will not work until you add compensation actions!");

            } catch (Throwable ex) {
                ex.printStackTrace();
                pm.endTransaction();
                try {
                    pm.rollbackTransaction();
                } catch (Throwable rbEx) {
                    log.error("Could not rollback transaction after exception!", rbEx);
                }
            }


            if (admins.isEmpty()) {
                req.setAttribute(SportletProperties.LAYOUT_PAGE, "SetupAdmin");
            } else {
                redirect(event);
                return;
            }

        } else {

            // do a databse update since an old version exists
            if (currentVersion != null) {
                req.setAttribute(SportletProperties.LAYOUT_PAGE, "UpdateDatabase");
            } else {
                req.setAttribute(SportletProperties.LAYOUT_PAGE, "SetupDatabase");
            }
        }

        layoutEngine.actionPerformed(event);

        layoutEngine.service(event);

    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PortletContext ctx = new PortletContextImpl(getServletContext());
        GridSphereEventImpl event = new GridSphereEventImpl(ctx, req, res);
        req.setCharacterEncoding("utf-8");

        System.err.println("in do post!!!");

        req.setAttribute(SportletProperties.LAYOUT_PAGE, "SetupDatabase");
        try {
            String installType = req.getParameter("install");
            if (installType != null) {
                if (installType.equals("default")) {
                    createDefaultDatabase();
                    makeDatabase();
                    createDatabaseFile();
                    req.setAttribute(SportletProperties.LAYOUT_PAGE, "SetupAdmin");
                }
                if (installType.equals("custom")) {
                    createExternalDatabase(event);
                    makeDatabase();
                    createDatabaseFile();
                    req.setAttribute(SportletProperties.LAYOUT_PAGE, "SetupAdmin");
                }
                if (installType.equals("update")) {
                    req.setAttribute(SportletProperties.LAYOUT_PAGE, "UpdateDatabase");
                    updateDatabase();
                    removeOldDatabaseFile();
                    createDatabaseFile();
                    redirect(event);
                }
                if (installType.equals("admin")) {
                    req.setAttribute(SportletProperties.LAYOUT_PAGE, "SetupAdmin");
                    createAdmin(event);
                }
            }

        } catch (IllegalArgumentException e) {
            req.getSession(true).setAttribute("error", e.getMessage());
        }
        redirect(event);
    }


    private void createDefaultDatabase() {
        InputStream hibInputStream = getServletContext().getResourceAsStream("/WEB-INF/CustomPortal/database/hibernate.properties");
        String hibPath = getServletContext().getRealPath("/WEB-INF/CustomPortal/database/hibernate.properties");
        try {
            FileOutputStream hibOut = new FileOutputStream(hibPath);
            Properties hibProps = new Properties();
            String connURL = "jdbc:hsqldb:" + getServletContext().getRealPath("/WEB-INF/CustomPortal/database/gridsphere");
            log.debug("using connURL= " + connURL);
            hibProps.load(hibInputStream);
            hibProps.setProperty("hibernate.connection.url", connURL);
            hibProps.store(hibOut, "Hibernate Properties");
        } catch (IOException e) {
            log.error("Unable to load/save hibernate.properties", e);
            throw new IllegalArgumentException("Unable to  save hibernate.properties file! Please check the log file for more details");
        }

    }

    private void createExternalDatabase(GridSphereEvent event) {
        HttpServletRequest req = event.getHttpServletRequest();
        InputStream hibInputStream = getServletContext().getResourceAsStream("/WEB-INF/CustomPortal/database/hibernate.properties");
        String hibPath = getServletContext().getRealPath("/WEB-INF/CustomPortal/database/hibernate.properties");

        String dbtype = req.getParameter("dbtype");

        String connURL = req.getParameter("databaseURL");
        if ((connURL == null) || (connURL.equals("")))
            throw new IllegalArgumentException("Please provide a value for the Database URL!");
        String dialect = req.getParameter("dialect");
        if ((dialect == null) || (dialect.equals("")))
            throw new IllegalArgumentException("Please provide a value for the Hibernate Dialect!");
        String driverClass = req.getParameter("driverClass");
        if ((driverClass == null) || (driverClass.equals("")))
            throw new IllegalArgumentException("Please provide a value for the Driver Class Name!");

        String name = req.getParameter("username");
        if (name == null) name = "";
        String pass = req.getParameter("password");
        if (pass == null) pass = "";

        System.err.println("dbtype=" + dbtype);


        try {
            FileOutputStream hibOut = new FileOutputStream(hibPath);
            Properties hibProps = new Properties();

            System.err.println("driver class=" + driverClass);
            System.err.println("conn url=" + connURL);
            hibProps.load(hibInputStream);
            hibProps.setProperty("hibernate.dialect", dialect);
            hibProps.setProperty("hibernate.connection.username", name);
            hibProps.setProperty("hibernate.connection.password", pass);
            hibProps.setProperty("hibernate.connection.url", connURL);
            hibProps.setProperty("hibernate.connection.driver_class", driverClass);
            hibProps.store(hibOut, "Hibernate Properties");
            hibOut.close();
            hibInputStream.close();            
        } catch (IOException e) {
            log.error("Unable to load/save hibernate.properties", e);
            throw new IllegalArgumentException("Unable to  save hibernate.properties file! Please check the log file for more details");
        }
    }

    private void makeDatabase() {
        CreateDatabase dbtask = new CreateDatabase();
        dbtask.setAction("CREATE");
        dbtask.setConfigDir(getServletContext().getRealPath(""));
        try {
            dbtask.execute();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void updateDatabase() {
        CreateDatabase dbtask = new CreateDatabase();
        dbtask.setAction("UPDATE");
        dbtask.setConfigDir(getServletContext().getRealPath(""));
        try {
            dbtask.execute();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void createDatabaseFile() {

        String release = SportletProperties.getInstance().getProperty("gridsphere.release");
        int idx = release.lastIndexOf(" ");
        String gsversion = release.substring(idx + 1);
        String dbpath = getServletContext().getRealPath("/WEB-INF/CustomPortal/database/GS_" + gsversion);
        try {
            File dbfile = new File(dbpath);
            dbfile.createNewFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to create file: " + dbpath, e);
        }
    }


    private void setupRoles(GridSphereEvent event) {

        // Retrieve user if there is one
        HttpServletRequest req = event.getHttpServletRequest();

        List roles = new ArrayList();
        roles.add("setup");

        // set user, role and groups in request

        req.setAttribute(SportletProperties.PORTLET_ROLE, roles);
    }

    private void removeOldDatabaseFile() {
        String dbpath = getServletContext().getRealPath("/WEB-INF/CustomPortal/database");
        File dbdir = new File(dbpath);
        String[] filenames = dbdir.list();
        String currentVersion = null;
        for (int i = 0; i < filenames.length; i++) {
            if (filenames[i].startsWith("GS")) currentVersion = filenames[i];
        }
        if (currentVersion != null) {
            File f = new File(currentVersion);
            f.delete();
        }
    }

    private void createAdmin(GridSphereEvent event) {
        HttpServletRequest req = event.getHttpServletRequest();
        String username = req.getParameter("username");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String org = req.getParameter("organization");
        String passwd = req.getParameter("password");
        String passwd2 = req.getParameter("password2");

        if (username.equals("")) throw new IllegalArgumentException("Please provide a User Name!");
        if (firstname.equals("")) throw new IllegalArgumentException("Please provide a First Name!");
        if (lastname.equals("")) throw new IllegalArgumentException("Please provide a Last Name!");
        if (email.equals("")) throw new IllegalArgumentException("Please provide an Email Address!");
        if (!email.contains("@") || (!email.contains(".")))
            throw new IllegalArgumentException("Please provide a valid Email Address!");
        if (!passwd.equals(passwd2)) throw new IllegalArgumentException("The supplied passwords do not match!");
        if (passwd.equals("")) throw new IllegalArgumentException("Please provide a Password!");


        PersistenceManagerService pms = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        PersistenceManagerRdbms pm = pms.createGridSphereRdbms();

        try {
            log.debug("Starting a database transaction");
            pm.beginTransaction();


            User accountRequest = this.userManagerService.createUser();
            accountRequest.setUserName(username);
            accountRequest.setFirstName(firstname);
            accountRequest.setLastName(lastname);
            accountRequest.setFullName(lastname + ", " + firstname);
            accountRequest.setEmailAddress(email);
            accountRequest.setOrganization(org);
            PasswordEditor editor = passwordService.editPassword(accountRequest);
            editor.setValue(passwd);
            log.debug("Saving the admin account in the DB");
            portalConfigService.setProperty(PortalConfigService.PORTAL_ADMIN_EMAIL, accountRequest.getEmailAddress());
            passwordService.savePassword(editor);
            userManagerService.saveUser(accountRequest);

            roleService.addUserToRole(accountRequest, PortletRole.ADMIN);
            roleService.addUserToRole(accountRequest, PortletRole.USER);

            // Commit and cleanup
            log.debug("Committing the database transaction");
            pm.endTransaction();
        } catch (StaleObjectStateException staleEx) {
            log.error("This interceptor does not implement optimistic concurrency control!");
            log.error("Your application will not work until you add compensation actions!");
            // Rollback, close everything, possibly compensate for any permanent changes
            // during the conversation, and finally restart business conversation. Maybe
            // give the user of the application a chance to merge some of his work with
            // fresh data... what you do here depends on your applications design.
            throw staleEx;
        } catch (Throwable ex) {
            // Rollback only
            ex.printStackTrace();
            try {
                pm.rollbackTransaction();
            } catch (Throwable rbEx) {
                log.error("Could not rollback transaction after exception!", rbEx);
            }


        }
    }

    private void redirect(GridSphereEvent event) {
        HttpServletRequest req = event.getHttpServletRequest();
        HttpServletResponse res = event.getHttpServletResponse();
        PortalConfigService configService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        StringBuffer s = new StringBuffer();
        String port;
        if (req.isSecure()) {
            s.append("https://");
            port = configService.getProperty("gridsphere.port.https");
        } else {
            s.append("http://");
            port = configService.getProperty("gridsphere.port.http");
        }
        s.append(req.getServerName());
        s.append(":");
        s.append((!port.equals("")) ? port : String.valueOf(req.getServerPort()));
        String contextPath = "/" + configService.getProperty("gridsphere.deploy");
        String servletPath = "/" + configService.getProperty("gridsphere.context");
        if (contextPath.equals("/")) contextPath = "";
        String url = contextPath + servletPath;
        url = s.append(url).toString();
        try {
            res.sendRedirect(url.toString());
        } catch (IOException e) {
            log.error("Unable to redirect!", e);
        }
        log.debug("redirecting to " + url);
    }

}
