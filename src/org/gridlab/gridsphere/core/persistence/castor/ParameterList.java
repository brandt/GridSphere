/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;

import java.util.List;

public class ParameterList {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(ParameterList.class.getName());

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

