/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portlet.jsr.mvc.descriptor;

import org.gridsphere.provider.portlet.jsr.mvc.ActionPage;

import java.util.List;

public class ActionPageDefinition {


    private String name = "";
    private String mode = "";
    private String className = "";
    private ActionPage actionPage = null;
    private String state = "";
    private List results = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ActionPage getActionPage() {
        return actionPage;
    }

    public void setActionPage(ActionPage actionPage) {
        this.actionPage = actionPage;
    }

    public List getResultList() {
        return results;
    }

    public void setResultList(List results) {
        this.results = results;
    }

}