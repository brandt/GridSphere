/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the needed settings for create/restore/update/delete such as connectionURL
 * are not set
 */

package org.gridlab.gridsphere.core.persistence;

public class ConfigurationException extends PersistenceException {

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String msg) {
        super(msg);
    }

}

