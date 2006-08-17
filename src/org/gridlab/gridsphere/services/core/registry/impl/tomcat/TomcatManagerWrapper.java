/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.registry.impl.tomcat;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManagerServiceImpl;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class TomcatManagerWrapper {

    private static String USERNAME = "gridsphere";
    private static String PASSWORD = "gridsphere";

    private static TomcatManagerWrapper instance = new TomcatManagerWrapper();
    private static PortletLog log = SportletLog.getInstance(TomcatManagerWrapper.class);

    private TomcatManagerWrapper() {
    }

    public static TomcatManagerWrapper getInstance() {
        return instance;
    }

    public static final void setCredentials(String name, String password) {
        USERNAME = name;
        PASSWORD = password;
    }

    public TomcatWebAppResult doCommand(PortletRequest req, String command) throws TomcatManagerException {
        TomcatWebAppResult result = null;
        try {
            String serverName = req.getServerName();
            String scheme = req.getScheme();
            int serverPort = req.getServerPort();


            HttpClient client = new HttpClient();
            client.getState().setCredentials(
                    null,
                    serverName,
                    new UsernamePasswordCredentials(USERNAME, PASSWORD)
            );

            String cmdStr = scheme + "://" + serverName + ":" + serverPort + "/manager" + command;
            GetMethod get = new GetMethod(cmdStr);
            log.debug("connecting to URL " + cmdStr);
            get.setDoAuthentication( true );

            // execute the GET
            int status = client.executeMethod( get );

            BufferedReader reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream()));
            // get first line
            // should be something like:
            // OK - some information text
            String line = null;

            line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line, "-");
            if (tokenizer.countTokens() == 2) {
                String rc = tokenizer.nextToken();
                String description = tokenizer.nextToken();
                result = new TomcatWebAppResult(rc, description);
            }

            while ((line = reader.readLine()) != null) {
                result.addWebAppDescriptor(line);
            }
            reader.close();

            // release any connection resources used by the method
            get.releaseConnection();


        } catch (IOException e) {
            throw new TomcatManagerException("Unable to perform command: ", e);
        }
        return result;
    }

    /**
     * Return the list of ui applications.
     */
    public TomcatWebAppResult getWebAppList(PortletRequest req) throws TomcatManagerException {
        return doCommand(req, "/list");
    }

    public TomcatWebAppResult reloadWebApp(PortletRequest req, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, "/reload?path=" + context);
    }

    public TomcatWebAppResult removeWebApp(PortletRequest req, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, "/remove?path=" + context);
    }

    public TomcatWebAppResult startWebApp(PortletRequest req, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, "/start?path=" + context);
    }

    public TomcatWebAppResult stopWebApp(PortletRequest req, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, "/stop?path=" + context);
    }

    public TomcatWebAppResult deployWebApp(PortletRequest req, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, "/deploy?path=" + context);
    }

    public TomcatWebAppResult undeployWebApp(PortletRequest req, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, "/undeploy?path=" + context);
    }

    public TomcatWebAppResult installWebApp(PortletRequest req, String context, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        if (!context.startsWith("/")) context = "/" + context;
	    return doCommand(req, "/deploy?war=" + warFile);
    }

    public TomcatWebAppResult installWebApp(PortletRequest req, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        return doCommand(req, "/install?war=" + warFile);
    }
}
