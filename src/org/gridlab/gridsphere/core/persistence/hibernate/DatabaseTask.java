package org.gridlab.gridsphere.core.persistence.hibernate;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.connection.DriverManagerConnectionProvider;
import net.sf.hibernate.tool.hbm2ddl.SchemaExport;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

/**
 * Test connection to the database and creates needed tables on it.
 */
public class DatabaseTask extends Task {

    private String configDir = null;
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


    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }

    private void Error(String msg) {
        System.out.println("\n\n\n" + msg + "\n\n\n");
    }

    public void execute() throws BuildException {
        Properties prop = new Properties();
        String propfilename = configDir + File.separator + "hibernate.properties";
        System.out.println("create db: " + propfilename);

        // try to load configfile
        try {
            FileInputStream fis = new FileInputStream(new File(propfilename));
            prop.load(fis);
        } catch (IOException e) {
            this.Error("DB Creation Error 1. " + CONFIGFILE_ERROR + " in " + propfilename);
            System.exit(1);
        }

        // try to get a db connection
        DriverManagerConnectionProvider dmcp = new DriverManagerConnectionProvider();
        try {
            dmcp.configure(prop);
            Connection con = dmcp.getConnection();
            dmcp.closeConnection(con);
        } catch (HibernateException e) {
            this.Error("DB Creation Error 2. " + DATABASE_CONNECTIN_NOT_VALID + " " + CHECK_PROPS + " " + NOT_INSTALLED);
            System.exit(1);
        } catch (SQLException e) {
            this.Error("DB Creation Error 3. " + CONNECTION_ERROR + " " + CHECK_PROPS + " " + NOT_INSTALLED);
            System.exit(1);
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
                new SchemaExport(cfg).create(false, true);
            }

        } catch (MappingException e) {
            this.Error("DB Creation Error 4. " + MAPPING_ERROR + " " + NOT_INSTALLED);
            System.exit(1);
        } catch (HibernateException e) {
            this.Error("DB Creation Error 5. " + MAPPING_ERROR + " " + NOT_INSTALLED);
            System.exit(1);
        }
    }
}
