/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor;


public class Parameter {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(Parameter.class.getName());

    private String Action = null;
    private String Table = null;
    private String Condition = null;

    public Parameter (String table, String condition) {
        this.Table = table;
        this.Condition = condition;
    }
    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }


}

