/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.List;

public class ParameterList {

    protected transient static PortletLog log = SportletLog.getInstance(ParameterList.class);


    private List parameters = null;
    private int counter = 0;

    public void add(String table, String condition) {
        Parameter p = new Parameter(table, condition);
    }

    public Parameter getNextParameter() {
        Parameter p = (Parameter)parameters.get(counter);
        counter++;
        return p;
    }

    public boolean hasMore() {
        if (counter < parameters.size()) return true; else return false;
    }

    public void resetCounter() {
        counter = 0;
    }

    public int Size() {
        return parameters.size();
    }


}

