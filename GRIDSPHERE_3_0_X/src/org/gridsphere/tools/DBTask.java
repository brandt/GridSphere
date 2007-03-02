package org.gridsphere.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.gridsphere.services.core.persistence.impl.CreateDatabase;


/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: DBTask.java 4728 2006-04-10 04:37:52Z novotny $
 */

/**
 * Ant task to create/update the database.
 */
public class DBTask extends Task {

    private Log log = LogFactory.getLog(DBTask.class);

    private CreateDatabase createDB = new CreateDatabase();

    /**
     * Sets the configuration directory of the database. All mappingfiles and the hibernate.properties
     * are located in this directory.
     *
     * @param configDir full path to the configuration directory
     */
    public void setConfigDir(String configDir) {
        createDB.setConfigDir(configDir);
    }

    public void setAction(String action) {
        createDB.setAction(action);
    }

    public void execute() throws BuildException {
        log.info("Database:");
        log.info("Config: " + createDB.getConfigDir());
        log.info("Action: " + createDB.getAction());
        try {
            createDB.execute();
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }


}
