package org.gridsphere.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.DriverManagerConnectionProvider;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: DBTask.java 4728 2006-04-10 04:37:52Z novotny $
 */

/**
 * Ant task to create/update the database.
 */
public class DBTask extends Task {

    private Log log = LogFactory.getLog(DBTask.class);

    public final static String ACTION_CREATE = "CREATE";
    public final static String ACTION_UPDATE = "UPDATE";

    private final static String MAPPING_ERROR =
            "FATAL: Could not create database! Please check above errormessages! ";
    private final static String CONFIGFILE_ERROR =
            "FATAL: No database configfile found! ";
    private final static String CHECK_PROPS =
            "Please check the hibernate.properties file! ";
    private final static String DATABASE_CONNECTION_NOT_VALID =
            "FATAL: Database conenction is not valid! ";
    private final static String CONNECTION_ERROR =
            "FATAL: Could not connect to database! ";
    private final static String NOT_INSTALLED =
            "Gridsphere is NOT correctly installed! ";
    private final static String CREATION_ERROR =
            "Could not create database!";
    private final static String UPDATE_ERROR =
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
        //SportletLog.setConfigureURL(configDir + "/WEB-INF/classes/log4j.properties");
        this.configDir = configDir;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private void createDatabase(Configuration cfg) throws BuildException {
        try {
            new SchemaExport(cfg).create(false, true);
            log.info("Successfully created DB");
        } catch (HibernateException e) {
            log.error("DB Error: " + CREATION_ERROR + " " + NOT_INSTALLED + " !", e);
            throw new BuildException("DB Error: " + CREATION_ERROR + " " + NOT_INSTALLED + " !");
        }
    }

    private void updateDatabase(Configuration cfg) throws BuildException {
        try {
            new SchemaUpdate(cfg).execute(false, true);
        } catch (HibernateException e) {
            log.error("DB Error: " + UPDATE_ERROR + " " + NOT_INSTALLED + " !", e);
            throw new BuildException("DB Error: " + UPDATE_ERROR + " " + NOT_INSTALLED + " !");
        }
    }

    /**
     * Loads properties from a given directory.
     *
     * @return
     * @throws BuildException
     */
    private Properties loadProperties() throws BuildException {
        Properties prop = new Properties();

        FileInputStream fis = null;
        String hibPath = "";
        try {
            hibPath = configDir + File.separator + "WEB-INF" + File.separator + "CustomPortal" +
                    File.separator + "database" + File.separator + hibernatePropertiesFileName;
            fis = new FileInputStream(hibPath);
        } catch (FileNotFoundException e) {
            hibPath = configDir + File.separator + "WEB-INF" + File.separator + "persistence" + File.separator + hibernatePropertiesFileName;
            try {
                fis = new FileInputStream(hibPath);
            } catch (FileNotFoundException ef) {
                throw new BuildException("Unable to locate hibernate.properties file!", ef);
            }

        }
        try {
            prop.load(fis);
            log.info("Using database configuration information from: " + hibPath);
        } catch (IOException e) {
            throw new BuildException("DB Error:" + CONFIGFILE_ERROR + " (" + hibPath + ")", e);
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
            throw new BuildException("Error: testDBConnection (1) : " + DATABASE_CONNECTION_NOT_VALID + " " + CHECK_PROPS + " " + NOT_INSTALLED);
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
            String mappingPath = configDir + File.separator  + "WEB-INF" + File.separator + "persistence";
            File mappingdir = new File(mappingPath);
            String[] children = mappingdir.list();

            if (children == null) {
                // Either dir does not exist or is not a directory
            } else {
                // Create list from children array
                List filenameList = Arrays.asList(children);
                // Ensure that this list is sorted alphabetically
                Collections.sort(filenameList);
                for (Iterator filenames = filenameList.iterator(); filenames.hasNext();) {
                    String filename = (String) filenames.next();
                    if (filename.endsWith(".hbm.xml")) {
                        // Get filename of file or directory
                        log.debug("add hbm file :" + mappingPath + File.separator + filename);
                        cfg.addFile(mappingPath + File.separator + filename);
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

        // try to load the properties
        log.info("Using project database");
        Properties properties = loadProperties();

        // test the db connection
        this.testDBConnection(properties);
        log.info("Tested DB connection.");

        // get a hibernate db Configuration
        Configuration cfg = getDBConfiguration(properties);
        log.info("Got DB configuration.");

        if (action.equals(ACTION_CREATE)) {
            this.createDatabase(cfg);
        } else if (action.equals(ACTION_UPDATE)) {
            this.updateDatabase(cfg);
        } else {
            throw new BuildException("Unknown Action specified (" + this.action + ")!");
        }
    }


}
