/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class TomcatManagerWrapper {

    private final static String USERNAME = "gridsphere";
    private final static String PASSWORD = "gridsphere";

    private static TomcatManagerWrapper instance = new TomcatManagerWrapper();
    private PortletManager pm = PortletManager.getInstance();

    private TomcatManagerWrapper() {
    }

    public static TomcatManagerWrapper getInstance() {
        return instance;
    }

    public TomcatWebAppResult doCommand(PortletRequest req, String command) throws TomcatManagerException {
        TomcatWebAppResult result = null;
        try {
            String serverName = req.getServerName();
            int serverPort = req.getServerPort();
            URL u = new URL("http://" + serverName + ":" + serverPort + "/manager" + command);
            URLConnection con = u.openConnection();

            String up = USERNAME + ":" + PASSWORD;
            String encoding = null;
            // check to see if sun's Base64 encoder is available.
            try {

                sun.misc.BASE64Encoder encoder =
                        (sun.misc.BASE64Encoder)
                        Class.forName("sun.misc.BASE64Encoder").newInstance();
                encoding = encoder.encode(up.getBytes());
            } catch (Exception ex) { // sun's base64 encoder isn't available
                throw new TomcatManagerException("No sun.misc.BASE64Encoder availoable in JDK!");
            }

            con.setRequestProperty("Authorization", "Basic " + encoding);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.connect();

            if (con instanceof HttpURLConnection) {
                HttpURLConnection httpConnection = (HttpURLConnection) con;
                // test for 401 result (HTTP only)
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    throw new TomcatManagerException("HTTP Authorization failure!");
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

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

    public List getPortletAppList(PortletRequest req) throws TomcatManagerException {
        List webapps = pm.getPortletWebApplicationNames();
        List l = new ArrayList();
        TomcatWebAppResult result = doCommand(req, "/list");
        if (result != null) {

            Iterator it = result.getWebAppDescriptions().iterator();
            while (it.hasNext()) {
                TomcatWebAppDescription webAppDesc = (TomcatWebAppDescription) it.next();
                //System.err.println(webAppDesc.toString());
                if (webapps.contains((webAppDesc.getContextPath()))) {
                    String desc = pm.getPortletWebApplicationDescription(webAppDesc.getContextPath());
                    webAppDesc.setDescription(desc);
                    l.add(webAppDesc);
                }
            }
        }
        return l;
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
        return doCommand(req, "/install?path=" + context + "&war=" + warFile);
    }

    public TomcatWebAppResult installWebApp(PortletRequest req, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        return doCommand(req, "/install?war=" + warFile);
    }
}
