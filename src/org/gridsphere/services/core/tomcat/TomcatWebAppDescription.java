package org.gridsphere.services.core.tomcat;

import org.gridsphere.portletcontainer.PortletStatus;

import java.util.StringTokenizer;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: TomcatWebAppDescription.java 4496 2006-02-08 20:27:04Z wehrens $
 */
public class TomcatWebAppDescription {

    public static final int UNKNOWN = 0;
    public static final int RUNNING = 1;
    public static final int STOPPED = 2;

    private String contextPath = "";
    private String running = "";
    private int runningState = 0;
    private String sessions = "";
    private String actions = "";
    private String description = "";
    private PortletStatus status = PortletStatus.SUCCESS;
    private String statusMessage = "";


    public TomcatWebAppDescription(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, ":");
        if (tokenizer.countTokens() >= 3) {
            contextPath = tokenizer.nextToken();
            // get rid of first slash
            contextPath = contextPath.substring(1);
            if (contextPath.equals("")) contextPath = "/";
            running = tokenizer.nextToken();
            sessions = tokenizer.nextToken();
            if (running.trim().equalsIgnoreCase("running")) runningState = RUNNING;
            if (running.trim().equalsIgnoreCase("stopped")) runningState = STOPPED;
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

    public int getRunningState() {
        return runningState;
    }

    public String getActions() {
        return actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PortletStatus getStatus() {
        return status;
    }

    public void setStatus(PortletStatus status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("contextPath= " + contextPath);
        sb.append("\nrunning= " + running);
        sb.append("\nrunningState= " + runningState);
        sb.append("\nsessions= " + sessions);
        sb.append("\nactions= " + actions);
        sb.append("\ndescription= " + description);
        sb.append("\nstatus= " + status);
        sb.append("\nstatus message= " + statusMessage);
        return sb.toString();
    }
}


