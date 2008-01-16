package org.gridsphere.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.gridsphere.services.core.persistence.impl.CreateDatabase;

/*
* @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
* @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
* @version $Id$
*/

/**
 * Ant task to create/update the database.
 *
 * @deprecated
 */
public class DBTask extends Task {

    private static Log log = LogFactory.getLog(DBTask.class);

    private CreateDatabase createDB = new CreateDatabase();

    public void setAction(String action) {
        createDB.setAction(action);
    }

    public void execute() throws BuildException {
        log.info("Database:");

        log.info("Action: " + createDB.getAction());
        try {
            createDB.execute();
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }


}
