/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id: ConfigurationException.java 4496 2006-02-08 20:27:04Z wehrens $
 *
 * Is thrown when the needed settings for create/restore/update/delete such as connectionURL
 * are not set
 */

package org.gridlab.gridsphere.services.core.persistence;

public class ConfigurationException extends PersistenceManagerException {

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String msg) {
        super(msg);
    }

}

