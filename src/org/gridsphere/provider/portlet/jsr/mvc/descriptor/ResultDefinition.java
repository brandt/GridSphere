/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: AuthModulesDescriptor.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.provider.portlet.jsr.mvc.descriptor;

public class ResultDefinition {

    private String result = "";
    private String state = "";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}

