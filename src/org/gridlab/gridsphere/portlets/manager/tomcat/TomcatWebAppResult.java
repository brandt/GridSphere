/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 23, 2002
 * Time: 1:18:04 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.manager.tomcat;

import org.gridlab.gridsphere.portlet.PortletURI;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class TomcatWebAppResult {

    private String returnCode = "ERROR";
    private String description = "";
    private List webAppDescriptions = new Vector();

    public class TomcatWebAppDescription {

        private String contextPath = "";
        private String running = "";
        private String sessions = "";
        private String actions = "";

        public TomcatWebAppDescription(String line) {
            StringTokenizer tokenizer = new StringTokenizer(line, ":");
            if (tokenizer.countTokens() >= 3) {
                contextPath = tokenizer.nextToken();
                // get rid of first slash
                contextPath = contextPath.substring(1);
                sessions = tokenizer.nextToken();
                running = tokenizer.nextToken();
                actions = "start stop reload remove";
            }
        }

        public String getContextPath() {
            return contextPath;
        }

        public String getSessions() {
            return sessions;
        }

        public String getRunning() {
            return running;
        }

        public String getActions() {
            return actions;
        }
    }


    public TomcatWebAppResult() {}

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
