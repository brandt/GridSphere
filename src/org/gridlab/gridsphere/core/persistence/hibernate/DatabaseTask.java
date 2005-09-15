package org.gridlab.gridsphere.core.persistence.hibernate;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.hibernate.connection.DriverManagerConnectionProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.tool.hbm2ddl.SchemaExport;

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
 * @deprecated 
 */

/**
 * Test connection to the database and creates needed tables on it.
 */
public class DatabaseTask extends Task {

    private PortletLog log = SportletLog.getInstance(DatabaseTask.class);

    private String configDir = null;
    private String webappname = "gridsphere";  // fallback to gs if nothing is specified
    private static final String MAPPING_ERROR =
            "FATAL: Could not create database! Please check above errormessages! ";
    private static final String CONFIGFILE_ERROR =
            "FATAL: No database configfile found! ";
    private static final String CHECK_PROPS =
            "Please check the hibernate.properties file! ";
    private static final String DATABASE_CONNECTIN_NOT_VALID =
            "FATAL: Database conenction is not valid! ";
    private static final String CONNECTION_ERROR =
            "FATAL: Could not connect to database! ";
    private static final String NOT_INSTALLED =
            "Gridsphere is NOT correctly installed! ";
    private static final String NO_CORE_TABLES =
            "Some core tables could not be found!";

    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }

    public void setWebappname(String appName) {
        this.webappname = appName;
    }


    private void checkDatabase(boolean startUpCheck) throws BuildException {

        if (!startUpCheck)
            this.configDir =
                    configDir + File.separator + "webapps" + File.separator + webappname +
                    File.separator + "WEB-INF" + File.separator + "persistence";

        Properties prop = new Properties();
        String propfilename = configDir + File.separator + "hibernate.properties";

        log.info("config is: " + this.configDir);
        // try to load configfile
        try {
            FileInputStream fis = new FileInputStream(new File(propfilename));
            prop.load(fis);
        } catch (IOException e) {
            throw new BuildException("DB Creation dbError 1. " + CONFIGFILE_ERROR + " in " + propfilename);
        }

        // try to get a db connection
        DriverManagerConnectionProvider dmcp = new DriverManagerConnectionProvider();
        try {
            dmcp.configure(prop);
            Connection con = dmcp.getConnection();
            dmcp.closeConnection(con);
        } catch (HibernateException e) {
            throw new BuildException("DB Creation dbError 2. " + DATABASE_CONNECTIN_NOT_VALID + " " + CHECK_PROPS + " " + NOT_INSTALLED);
        } catch (SQLException e) {
            throw new BuildException("DB Creation dbError 3. " + CONNECTION_ERROR + " " + CHECK_PROPS + " " + NOT_INSTALLED);
        }

        // load mapping files and create db
        Configuration cfg = null;
        try {
            cfg = new Configuration();
            cfg.setProperties(prop);

            File mappingdir = new File(configDir);
            String[] children = mappingdir.list();
            if (children == null) {
                // Either dir does not exist or is not a directory
            } else {
                for (int i = 0; i < children.length; i++) {
                    // Get filename of file or directory
                    String filename = children[i];
                    if (filename.endsWith(".hbm.xml")) {
                        cfg.addFile(configDir + File.separator + filename);
                    }
                }
                if (!startUpCheck) {
                    new SchemaExport(cfg).create(false, true);
                } else {
                    // check if actual tables really exist in the db
                    PersistenceManagerRdbms rdbms = PersistenceManagerFactory.createGridSphereRdbms();
                    try {
                        // check if there is the user table, should be enough
                        rdbms.restoreList("select uzer from " + SportletUserImpl.class.getName() + " uzer");
                    } catch (PersistenceManagerException e) {
                        throw new BuildException("DB Creation dbError 6. " + NO_CORE_TABLES + " " + NOT_INSTALLED);
                    }
                }
            }

        } catch (MappingException e) {
            throw new BuildException("DB Creation dbError 4. " + MAPPING_ERROR + " " + NOT_INSTALLED);
        } catch (HibernateException e) {
            throw new BuildException("DB Creation dbError 5. " + MAPPING_ERROR + " " + NOT_INSTALLED);
        }
    }

    public void execute() throws BuildException {
        checkDatabase(false);
    }

    public void checkDBSetup(String config) throws PersistenceManagerException {
        this.configDir = config;
        try {
            checkDatabase(true);
        } catch (BuildException e) {
            throw new PersistenceManagerException(e.getMessage(), e);
        }
    }


}

