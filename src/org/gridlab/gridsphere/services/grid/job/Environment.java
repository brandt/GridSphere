/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import java.util.Iterator;
import java.util.Vector;

public class Environment {

    private Vector variableList = new Vector();

    public Environment() {
    }

    public Iterator iterateVariables() {
        return this.variableList.iterator();
    }

    public EnvironmentVariable getVariable(String name) {
        Iterator variables = this.variableList.iterator();
        while (variables.hasNext()) {
            EnvironmentVariable variable = (EnvironmentVariable)variables.next();
            if (variable.getName().equals(name)) {
                return variable;
            }
        }
        return null;
    }

    public String getVariableValue(String name) {
        EnvironmentVariable variable = getVariable(name);
        if (variable == null) return "";
        return variable.getValue();
    }

    public boolean hasVariable(String name) {
        return this.variableList.contains(name);
    }

    public void putVariable(EnvironmentVariable variable) {
        String name = variable.getName();
        variableList.add(variable);
    }

    public void putVariable(String name, String value) {
        EnvironmentVariable variable = new EnvironmentVariable();
        variable.setName(name);
        variable.setValue(value);
        this.variableList.add(variable);
    }

    public void removeVariable(String name) {
        EnvironmentVariable variable = getVariable(name);
        if (variable == null) return;
        this.variableList.remove(variable);
    }

    public void clearVariables() {
        this.variableList.clear();
    }

    public int getNumberOfVariables() {
        return this.variableList.size();
    }

    public Vector getVariableList() {
        return this.variableList;
    }

    public void setVariableList(Vector variableList) {
        this.variableList = variableList;
    }
}
