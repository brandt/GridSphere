/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 23, 2002
 * Time: 1:18:04 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.registry.impl.tomcat;

import java.util.List;
import java.util.Vector;

public class TomcatWebAppResult {

    private String returnCode = "ERROR";
    private String description = "";
    private List webAppDescriptions = new Vector();

    public TomcatWebAppResult() {
    }

    public TomcatWebAppResult(String returnCode, String description) {
        this.returnCode = returnCode;
        this.description = description;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getDescription() {
        return description;
    }

    public void addWebAppDescriptor(String line) {
        TomcatWebAppDescription description = new TomcatWebAppDescription(line);
        webAppDescriptions.add(description);
    }

    public List getWebAppDescriptions() {
        return webAppDescriptions;
    }

}
