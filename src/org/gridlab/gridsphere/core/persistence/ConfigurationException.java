/*
 * @author <a href="oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Is thrown when the needed settings for create/restore/update/delete such as connectionURL
 * are not set
 */

package org.gridlab.gridsphere.core.persistence;

public class ConfigurationException extends Exception {

    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(ConfigurationException.class.getName());


    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String msg) {
        super(msg);
    }


}

