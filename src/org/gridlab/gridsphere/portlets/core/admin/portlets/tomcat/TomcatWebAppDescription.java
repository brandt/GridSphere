package org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat;

import java.util.StringTokenizer;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
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


