/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.services.core.tomcat.impl;

import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.tomcat.TomcatManagerException;
import org.gridsphere.services.core.tomcat.TomcatManagerService;
import org.gridsphere.services.core.tomcat.TomcatWebAppResult;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.StringTokenizer;

public class TomcatManagerServiceImpl implements TomcatManagerService, PortletServiceProvider {

    private ServletContext ctx = null;

    public TomcatManagerServiceImpl() {
    }

    public void init(PortletServiceConfig config) {
        ctx = config.getServletContext();
    }

    public void destroy() {

    }

    public TomcatWebAppResult doCommand(PortletRequest req, PortletResponse res, String command) throws TomcatManagerException {
        TomcatWebAppResult result = null;
        try {
            StringWriter writer = new StringWriter();
            StoredPortletResponseImpl storedResponse = new StoredPortletResponseImpl((HttpServletRequest)req, (HttpServletResponse)res, writer);


            ServletContext context = ctx.getContext("/manager");

            System.err.println("" +  context.getServletContextName());
            //context.getRequestDispatcher("/list").include((HttpServletRequest)req, storedResponse);
            req.setAttribute("org.gridsphere.tomcat_hack", command);
            context.getRequestDispatcher(command).include((ServletRequest)req, storedResponse);
        
            System.err.println(writer.getBuffer());
            
            LineNumberReader reader = new LineNumberReader(new StringReader(writer.getBuffer().toString()));
            // get first line
            // should be something like:
            // OK - some information text
            String line = null;

            line = reader.readLine();
            System.err.println("line= " + line);
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


    public TomcatWebAppResult getWebAppList(PortletRequest req, PortletResponse res) throws TomcatManagerException {
        return doCommand(req, res, "/list");
    }

    public TomcatWebAppResult reloadWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, res, "/reload?path=" + context);
    }

    public TomcatWebAppResult removeWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, res, "/remove?path=" + context);
    }

    public TomcatWebAppResult startWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, res, "/start?path=" + context);
    }

    public TomcatWebAppResult stopWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, res, "/stop?path=" + context);
    }

    public TomcatWebAppResult deployWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, res, "/deploy?path=" + context);
    }

    public TomcatWebAppResult undeployWebApp(PortletRequest req, PortletResponse res, String context) throws TomcatManagerException {
        if (!context.startsWith("/")) context = "/" + context;
        return doCommand(req, res, "/undeploy?path=" + context);
    }

    public TomcatWebAppResult installWebApp(PortletRequest req, PortletResponse res, String context, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        if (!context.startsWith("/")) context = "/" + context;
	    return doCommand(req, res, "/deploy?war=" + warFile);
    }

    public TomcatWebAppResult installWebApp(PortletRequest req, PortletResponse res, String warFile) throws TomcatManagerException {
        //install?path=/foo&war=file:/path/to/foo
        return doCommand(req, res, "/install?war=" + warFile);
    }
}
