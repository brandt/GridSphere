/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.portlets.tomcat;

import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class TomcatManagerWrapper {

    private final static String USERNAME = "gridsphere";
    private final static String PASSWORD = "gridsphere";

    private String port = "8080";

    private static TomcatManagerWrapper instance = new TomcatManagerWrapper();
    private PortletManager pm = PortletManager.getInstance();

    private TomcatManagerWrapper() {
    }

    public static TomcatManagerWrapper getInstance() {
        return instance;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public TomcatWebAppResult doCommand(String command) throws TomcatManagerException {
        TomcatWebAppResult result = null;
        try {
            URL u = new URL("http://127.0.0.1:" + port + "/manager" + command);
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


        } catch (IOException e) {
            throw new TomcatManagerException("Unable to perform command: ", e);
        }
        return result;
    }

    /**
     * Return the list of ui applications.
     */
    public TomcatWebAppResult getWebAppList() throws TomcatManagerException {
        return doCommand("/list");
    }

    public List getPortletAppList() throws TomcatManagerException {
        List webapps = pm.getPortletWebApplicationNames();
        List l = new ArrayList();
        TomcatWebAppResult result = doCommand("/list");
        if (result != null) {

            Iterator it = result.getWebAppDescriptions().iterator();
            while (it.hasNext()) {
                TomcatWebAppDescription webAppDesc = (TomcatWebAppDescription)it.next();
                System.err.println(webAppDesc.toString());
                if (webapps.contains((webAppDesc.getContextPath()))) {
                    String desc = pm.getPortletWebApplicationDescription(webAppDesc.getContextPath());
                    webAppDesc.setDescription(desc);
                    l.add(webAppDesc);
                }
            }
        }
        return l;
    }

    public TomcatWebAppResult reloadWebApp(String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand("/reload?path=" + context);
    }

    public TomcatWebAppResult removeWebApp(String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand("/remove?path=" + context);
    }

    public TomcatWebAppResult startWebApp(String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand("/start?path=" + context);
    }

    public TomcatWebAppResult stopWebApp(String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand("/stop?path=" + context);
    }

    public TomcatWebAppResult deployWebApp(String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand("/deploy?path=" + context);
    }

    public TomcatWebAppResult undeployWebApp(String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand("/undeploy?path=" + context);
    }

    public TomcatWebAppResult installWebApp(String context, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand("/install?path=" + context + "&war=" + warFile);
    }

    public TomcatWebAppResult installWebApp(String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        return doCommand("/install?war=" + warFile);
    }
}
