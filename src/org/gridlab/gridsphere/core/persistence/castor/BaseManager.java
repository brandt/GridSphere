/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerInterface;
import org.gridlab.gridsphere.core.persistence.ConfigurationException;
import org.gridlab.gridsphere.core.persistence.PersistenceInterface;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

public class BaseManager {

    protected transient static PortletLog log = SportletLog.getInstance(BaseManager.class);


    public BaseManager() {
        super();
    }

    public PersistenceInterface getPM() throws ConfigurationException {

        PersistenceInterface pm = new PersistenceManager("webapps/WEB-INF/conf/database.xml","portal");
        return pm;

    }

}

