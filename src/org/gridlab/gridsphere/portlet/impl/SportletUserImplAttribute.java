/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.core.persistence.UniqueID;
import org.gridlab.gridsphere.core.persistence.castor.Attribute;

/**
 * @table suiattribute
 * @depends org.gridlab.gridsphere.portlet.impl.SportletUserImpl
 */
public class SportletUserImplAttribute extends Attribute {

    /**
     * @sql-name sportletuser
     */
    private SportletUserImpl User = new SportletUserImpl();

    public SportletUserImplAttribute() {
        super();
    }

    public SportletUserImplAttribute(String k, String v) {
        this.setKey(k);
        this.setValue(v);
        this.setOid(UniqueID.get());
    }

    public SportletUserImpl getUser() {
        return User;
    }

    public void setUser(SportletUserImpl user) {
        User = user;
    }

}

