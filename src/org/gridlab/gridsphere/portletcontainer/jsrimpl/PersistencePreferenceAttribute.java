package org.gridlab.gridsphere.portletcontainer.jsrimpl;

import javax.portlet.ReadOnlyException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class PersistencePreferenceAttribute {

    private String oid = null;
    protected boolean readonly = false;
    protected List values = new ArrayList();


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public boolean isReadOnly() {
        return readonly;
    }

    public void setValues(String[] values) throws ReadOnlyException {
        if (!readonly) {
            this.values = new ArrayList();
            for (int i = 0; i < values.length; i++) {
                this.values.add(values[i]);
            }
        } else {
            throw new ReadOnlyException("value is ReadOnly");
        }
    }

    public String[] getValues() {
        return (String[]) this.values.toArray();
    }

    public void setValue(String value) throws ReadOnlyException {
        setValues(new String[]{value});
    }

    public String getValue() {
        return (String) values.get(0);
    }
}
