/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerInterface;
import org.gridlab.gridsphere.core.persistence.ConfigurationException;

public class BaseManager {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(BaseManager.class.getName());

    public BaseManager() {
        super();
    }

    public PersistenceManager getPM() throws ConfigurationException {

        PersistenceManager pm = new PersistenceManager("webapps/WEB-INF/conf/database.xml","portal");
        return pm;

    }

}

