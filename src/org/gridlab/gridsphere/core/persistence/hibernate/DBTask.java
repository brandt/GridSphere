package org.gridlab.gridsphere.core.persistence.hibernate;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.connection.DriverManagerConnectionProvider;
import net.sf.hibernate.tool.hbm2ddl.SchemaExport;
import net.sf.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

/**
 * Ant task to create/update the database.
 */
public class DBTask extends Task {

    private PortletLog log = SportletLog.getInstance(DBTask.class);

    public final static String ACTION_CREATE = "CREATE";
    public final static String ACTION_UPDATE = "UPDATE";
    public final static String ACTION_CHECKDB = "CHECKDB";

    private String MAPPING_ERROR =
            "FATAL: Could not create database! Please check above errormessages! ";
    private String CONFIGFILE_ERROR =
            "FATAL: No database configfile found! ";
    private String CHECK_PROPS =
            "Please check the hibernate.properties file! ";
    private String DATABASE_CONNECTIN_NOT_VALID =
            "FATAL: Database conenction is not valid! ";
    private String CONNECTION_ERROR =
            "FATAL: Could not connect to database! ";
    private String NOT_INSTALLED =
            "Gridsphere is NOT correctly installed! ";
    private String NO_CORE_TABLES =
            "Some core tables could not be found!";
    private String CREATION_ERROR =
            "Could not create database!";
    private String UPDATE_ERROR =
            "Could not update database!";


    private String hibernatePropertiesFileName = "hibernate.properties";

    private String configDir = "";
    private String action = "";

    /**
     * Sets the configuration directory of the database. All mappingfiles and the hibernate.properties
     * are located in this directory.
     *
     * @param configDir full path to the configuration directory
     */
    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private void createDatabase(Configuration cfg) throws BuildException {
        try {
            new SchemaExport(cfg).create(false, true);
        } catch (HibernateException e) {
            throw new BuildException("DB Error: " + CREATION_ERROR + " " + NOT_INSTALLED + " !");
        }

    }

    private void updateDatabase(Configuration cfg) throws BuildException {
        try {
            new SchemaUpdate(cfg).execute(false, true);
        } catch (HibernateException e) {
            throw new BuildException("DB Error: " + UPDATE_ERROR + " " + NOT_INSTALLED + " !");
        }
    }

    private void checkDatabase() throws BuildException {
        try {
            PersistenceManagerRdbms rdbms = PersistenceManagerFactory.createGridSphereRdbms();
            // check if there is the user table, should be enough
            List r = rdbms.restoreList("select uzer from " + SportletUserImpl.class.getName() + " uzer");
        } catch (Exception e) {
            throw new BuildException("DB Error: " + NO_CORE_TABLES + " " + NOT_INSTALLED);
        }
    }

    /**
     * Loads properties from a given directory.
     *
     * @param path
     * @return
     * @throws BuildException
     */
    private Properties loadProperties(String path) throws BuildException {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(new File(path + File.separator + hibernatePropertiesFileName));
            prop.load(fis);
        } catch (IOException e) {
            throw new BuildException("DB Error:" + CONFIGFILE_ERROR + " (" + path + ")");
        }
        return prop;
    }

    /**
     * Test the Database connection.
     *
     * @param props
     * @throws BuildException
     */
    private void testDBConnection(Properties props) throws BuildException {
        DriverManagerConnectionProvider dmcp = new DriverManagerConnectionProvider();
        try {
            dmcp.configure(props);
            Connection con = dmcp.getConnection();
            dmcp.closeConnection(con);
        } catch (HibernateException e) {
            throw new BuildException("Error: testDBConnection (1) : " + DATABASE_CONNECTIN_NOT_VALID + " " + CHECK_PROPS + " " + NOT_INSTALLED);
        } catch (SQLException e) {
            throw new BuildException("Error: testDBConnection (2) " + CONNECTION_ERROR + " " + CHECK_PROPS + " " + NOT_INSTALLED);
        }

    }

    /**
     * Get a hibernate configuration.
     *
     * @param props
     * @return
     * @throws BuildException
     */
    private Configuration getDBConfiguration(Properties props) throws BuildException {
        Configuration cfg = null;
        try {
            cfg = new Configuration();
            cfg.setProperties(props);

            File mappingdir = new File(configDir);
            String[] children = mappingdir.list();
            if (children == null) {
                // Either dir does not exist or is not a directory
                // so this means we do not have a db here...
            } else {
                for (int i = 0; i < children.length; i++) {
                    // Get filename of file or directory
                    String filename = children[i];
                    if (filename.endsWith(".hbm.xml")) {
                        cfg.addFile(configDir + File.separator + filename);
                        //log.info("loading "+configDir + File.separator + filename);
                    }
                }
            }

        } catch (MappingException e) {
            throw new BuildException("DB Error: " + MAPPING_ERROR + " " + NOT_INSTALLED);
        } catch (HibernateException e) {
            throw new BuildException("DB Error: " + MAPPING_ERROR + " " + NOT_INSTALLED);
        }

        return cfg;
    }


    public void execute() throws BuildException {

        log.info("Database:");
        log.info("Config: " + this.configDir);
        log.info("Action: " + this.action);

        try {
            // try to load the properties
            Properties properties = loadProperties(this.configDir);
            log.info("Loaded DB properties: " + this.configDir);

            // test the db connection
            this.testDBConnection(properties);
            log.info("Tested DB connection.");

            // get a hibernate db Configuration
            Configuration cfg = getDBConfiguration(properties);
            log.info("Got DB configuration.");

            if (action.equals(ACTION_CHECKDB)) {
                this.checkDatabase();
            } else if (action.equals(ACTION_CREATE)) {
                this.createDatabase(cfg);
            } else if (action.equals(ACTION_UPDATE)) {
                this.updateDatabase(cfg);
            } else {
                throw new BuildException("Unknown Action specified (" + this.action + ")!");
            }

        } catch (BuildException e) {
            log.info("Database not correctly installed.\n" + e);
            throw new BuildException("The database is not correctly installed!");
        }
    }
}
