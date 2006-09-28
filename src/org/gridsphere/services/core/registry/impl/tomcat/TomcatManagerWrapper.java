/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TomcatManagerWrapper.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.registry.impl.tomcat;

import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.portlet.jsrimpl.PortletContextImpl;

import javax.portlet.PortletRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.StringTokenizer;

public class TomcatManagerWrapper {

    private static TomcatManagerWrapper instance = new TomcatManagerWrapper();

    private TomcatManagerWrapper() {
    }

    public static TomcatManagerWrapper getInstance() {
        return instance;
    }

    public TomcatWebAppResult doCommand(PortletContext ctx, PortletRequest req, PortletResponse res, String command) throws TomcatManagerException {
        TomcatWebAppResult result = null;
        try {

            StringWriter writer = new StringWriter();
            StoredPortletResponseImpl storedResponse = new StoredPortletResponseImpl((HttpServletResponse)res, writer);



            ServletContext servCtx = ((PortletContextImpl)ctx).getServletContext();

            ServletContext context = servCtx.getContext("/manager");

            context.getRequestDispatcher(command).include((ServletRequest)req, storedResponse);

            //System.err.println(writer.getBuffer());


            LineNumberReader reader = new LineNumberReader(new StringReader(writer.getBuffer().toString()));
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
        } catch (Exception e) {
            throw new TomcatManagerException("Unable to perform command: " + command, e);
        }
        return result;
    }

    /**
     * Return the list of ui applications.
     */
    public TomcatWebAppResult getWebAppList(PortletContext ctx, PortletRequest req, PortletResponse res) throws TomcatManagerException {
        return doCommand(ctx, req, res, "/list");
    }

    public TomcatWebAppResult reloadWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(ctx, req, res, "/reload?path=" + context);
    }

    public TomcatWebAppResult removeWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(ctx, req, res, "/remove?path=" + context);
    }

    public TomcatWebAppResult startWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(ctx, req, res, "/start?path=" + context);
    }

    public TomcatWebAppResult stopWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(ctx, req, res, "/stop?path=" + context);
    }

    public TomcatWebAppResult deployWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(ctx, req, res, "/deploy?path=" + context);
    }

    public TomcatWebAppResult undeployWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(ctx, req, res, "/undeploy?path=" + context);
    }

    public TomcatWebAppResult installWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String context, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        if (!context.startsWith("/")) context = "/" + context;
	    return doCommand(ctx, req, res, "/deploy?war=" + warFile);
    }

    public TomcatWebAppResult installWebApp(PortletContext ctx, PortletRequest req, PortletResponse res, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        return doCommand(ctx, req, res, "/install?war=" + warFile);
    }
}
