/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * Helperobject for storing a Vector of Strings
 */


package org.gridlab.gridsphere.core.persistence.castor;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.exolab.castor.jdo.Database;

import java.util.Vector;

public class StringVector extends BaseObject {
    protected transient static PortletLog log = SportletLog.getInstance(StringVector.class);

    /**
     * @sql-size 128
     * @sql-name value
     */
    protected String Value  = new String();
    /**
     * @sql-name
     */
    private transient Object Reference = new Object();

    public StringVector() {
        super();
    }

    public StringVector(Object o,String value) {
        super();
        Reference = (BaseObject)o;
        Value = value;
    }

    public Object getReference() {
        return Reference;
    }

    public void setReference(Object reference) {
        Reference = reference;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}

