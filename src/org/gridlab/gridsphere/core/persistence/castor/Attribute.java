/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;

public class Attribute extends BaseObject {

    private String Key;
    private String Value;
    private SportletUserImpl User;


    private String getID() {
        long l = System.currentTimeMillis();
        String n = new String().valueOf(l);
        return n;
    }

    public Attribute () {
        super();
    }

    public Attribute(String k, String v) {
        Key = k;
        Value = v;
        this.setOid(getID());
        System.out.println("OID: "+getOid());
    }

    public SportletUserImpl getUser() {
        return User;
    }

    public void setUser(SportletUserImpl user) {
        User = user;
        System.out.println("setUser:"+user.getID());
    }


    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

}

