package org.gridsphere.services.core.persistence.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.customization.SettingsService;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
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

/**
 * Ant task to create/update the database.
 */
public class CreateDatabase {

    private Log log = LogFactory.getLog(CreateDatabase.class);

    public final static String ACTION_CREATE = "CREATE";
    public final static String ACTION_UPDATE = "UPDATE";

    private final static String MAPPING_ERROR =
            "FATAL: Could not create database! Please check above errormessages! ";
    private final static String CHECK_PROPS =
            "Please check the hibernate.properties file! ";
    private final static String DATABASE_CONNECTION_NOT_VALID =
            "FATAL: Database conenction is not valid! ";
    private final static String CONNECTION_ERROR =
            "FATAL: Could not connect to database! ";
    private final static String CREATION_ERROR =
            "Could not create database!";
    private final static String UPDATE_ERROR =
            "Could not update database!";


    private String hibernatePropertiesFileName = "hibernate.properties";

    // where the persistence Mappings are stored
    private String persistenceMappingDir = "";

    private String action = "";

    public CreateDatabase() {
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getPersistenceMappingDir() {
        return persistenceMappingDir;
    }

    public void setPersistenceMappingDir(String persistenceMappingDir) {
        this.persistenceMappingDir = persistenceMappingDir;
    }

    public String getAction() {
        return action;
    }

    private void createDatabase(Configuration cfg) throws PersistenceManagerException {
        try {
            new SchemaExport(cfg).create(false, true);
            log.info("Successfully created DB");
        } catch (HibernateException e) {
            log.error("DB Error: " + CREATION_ERROR + " !", e);
            throw new PersistenceManagerException("DB Error: " + CREATION_ERROR + " ! " + e.getMessage());
        }
    }

    private void updateDatabase(Configuration cfg) throws PersistenceManagerException {
        try {
            new SchemaUpdate(cfg).execute(false, true);
        } catch (HibernateException e) {
            log.error("DB Error: " + UPDATE_ERROR + " !", e);
            throw new PersistenceManagerException("DB Error: " + UPDATE_ERROR + " ! " + e.getMessage());
        }
    }

    /**
     * Loads properties from a given directory.
     */
    private Properties loadProperties() throws IOException {
        Properties prop = new Properties();

        FileInputStream fis = null;
        String hibPath = "";
        try {
            SettingsService settingsService = (SettingsService) PortletServiceFactory.createPortletService(SettingsService.class, true);
            hibPath = settingsService.getRealSettingsPath("database") + hibernatePropertiesFileName;
            fis = new FileInputStream(hibPath);
        } catch (FileNotFoundException e) {
            // todo  I think I should fix this....oliver 
            log.warn("This should not happen. Check the DB Config!");
        }

        prop.load(fis);
        log.info("Using database configuration information from: " + hibPath);

        return prop;
    }

    /**
     * Test the Database connection.
     *
     * @param props
     * @throws org.apache.tools.ant.BuildException
     *
     */
    private void testDBConnection(Properties props) throws PersistenceManagerException {
        DriverManagerConnectionProvider dmcp = new DriverManagerConnectionProvider();
        try {
            dmcp.configure(props);
            Connection con = dmcp.getConnection();
            dmcp.closeConnection(con);
        } catch (HibernateException e) {
            log.error(e);
            throw new PersistenceManagerException(DATABASE_CONNECTION_NOT_VALID + " " + CHECK_PROPS + " " + e.getMessage(), e);
        } catch (SQLException e) {
            log.error(e);
            throw new PersistenceManagerException(CONNECTION_ERROR + " " + CHECK_PROPS + " " + e.getMessage(), e);
        }

    }

    /**
     * Get a hibernate configuration.
     *
     * @param props
     * @return
     * @throws org.apache.tools.ant.BuildException
     *
     */
    private Configuration getDBConfiguration(Properties props) throws PersistenceManagerException {
        Configuration cfg = null;
        try {
            cfg = new Configuration();
            cfg.setProperties(props);
            log.debug("MappingPath is :" + persistenceMappingDir);
            File mappingdir = new File(persistenceMappingDir);
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
                        log.debug("add hbm file :" + persistenceMappingDir + File.separator + filename);
                        cfg.addFile(persistenceMappingDir + File.separator + filename);
                    }
                }
            }

        } catch (MappingException e) {
            throw new PersistenceManagerException("DB Error: " + MAPPING_ERROR);
        } catch (HibernateException e) {
            throw new PersistenceManagerException("DB Error: " + MAPPING_ERROR);
        }

        return cfg;
    }


    public void execute() throws IOException, PersistenceManagerException {

        log.info("Database:");
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
            throw new PersistenceManagerException("Unknown Action specified (" + this.action + ")!");
        }
    }


}
