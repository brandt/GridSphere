package org.gridsphere.services.core.jcr.impl;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.jndi.RegistryHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.jcr.JCRNode;
import org.gridsphere.services.core.jcr.JCRService;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.radeox.api.engine.RenderEngine;
import org.radeox.api.engine.context.RenderContext;
import org.radeox.engine.BaseRenderEngine;
import org.radeox.engine.context.BaseRenderContext;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JCRServiceImpl implements PortletServiceProvider, JCRService {

    private static Logger log = LogManager.getLogger(JCRServiceImpl.class);
    private String repositoryconfigpath = "";
    private String repositorypath = "";
    private Repository repository = null;
    private SimpleCredentials cred = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        repositoryconfigpath = config.getServletContext().getRealPath("WEB-INF/CustomPortal/portal/");
        repositorypath = config.getServletContext().getRealPath("WEB-INF/CustomPortal/content/");
        log.debug("JCR Path " + repositoryconfigpath);

        String repHomeDir = "repository";
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.jackrabbit.core.jndi.provider.DummyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "localhost");

        InitialContext ctx;
        try {
            ctx = new InitialContext(env);
            RegistryHelper.registerRepository(ctx,
                    "repo",
                    repositoryconfigpath + File.separator + "contentrepository.xml",
                    repositorypath + File.separator + repHomeDir,
                    true);

            repository = (Repository) ctx.lookup("repo");
            cred = new SimpleCredentials("userid", "".toCharArray());
            Session s = repository.login(cred, null);

            Workspace ws = s.getWorkspace();
            Map nameSpaces = new HashMap();

            log.info("Registering " + JCRNode.PREFIX + " with Namespace " + JCRNode.NAMESPACE);
            String[] prefixes = ws.getNamespaceRegistry().getPrefixes();
            for (String prefixe : prefixes) {
                //System.out.println("PREFIX: " + prefixe + " " + ws.getNamespaceRegistry().getURI(prefixe));
                nameSpaces.put(prefixe, ws.getNamespaceRegistry().getURI(prefixe));
            }

            if (!nameSpaces.containsKey(JCRNode.PREFIX))
                ws.getNamespaceRegistry().registerNamespace(JCRNode.PREFIX, JCRNode.NAMESPACE);

            s.logout();

        } catch (Exception e) {
            log.error("Could not start repository!", e);
        }
    }

    public void destroy() {
        log.info("Shutting down content repository...");
        // just to make sure...
        try {
            Session s = getSession();
            RepositoryImpl r = (RepositoryImpl) s.getRepository();
            s.logout();
            r.shutdown();
        } catch (Exception e) {
            log.error("Error shutting down repository!", e);
        }
    }


    public String getRepositoryconfigpath() {
        return repositoryconfigpath;
    }

    public void setRepositoryconfigpath(String repositoryconfigpath) {
        this.repositoryconfigpath = repositoryconfigpath;
    }

    public String getRepositorypath() {
        return repositorypath;
    }

    public void setRepositorypath(String repositorypath) {
        this.repositorypath = repositorypath;
    }

    public Session getSession() throws RepositoryException, NamingException {
        return repository.login(cred, null);
    }


    public NodeIterator query(String query, Session session) throws NamingException, RepositoryException {
        Workspace ws = session.getWorkspace();
        QueryManager qm = ws.getQueryManager();
        Query q = qm.createQuery(query, Query.SQL);
        QueryResult res = q.execute();
        NodeIterator it = res.getNodes();
        return it;
    }


    public boolean exists(String gsid) throws NamingException, RepositoryException {
        boolean result = false;
        String query = "select * from nt:base where " + JCRNode.GSID + "='" + gsid + "'";
        Session session = getSession();
        NodeIterator it = query(query, session);
        if (it.hasNext()) result = true;
        session.logout();
        return result;
    }

    public List<String> getAllNodeNames() {
        List<String> names = new ArrayList<String>();
        String query = "select * from nt:base where " + JCRNode.GSID + " IS NOT NULL";
        Session session = null;
        try {
            session = getSession();
            NodeIterator it = query(query, session);
            while (it.hasNext()) {
                Node n = it.nextNode();
                names.add(n.getName());
            }
        } catch (Exception e) {
            log.error("Failed to get content nodes!", e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
        return names;
    }

    public String getContent(String contentFile) {
        // handle content management
        Session session = null;
        String output = null;
        try {
            session = getSession();
            Workspace ws = session.getWorkspace();
            QueryManager qm = ws.getQueryManager();
            String nodename = contentFile.substring(6, contentFile.length()); // remove 'jcr://'
            String query = "select * from nt:base where " + JCRNode.GSID + "='" + nodename + "'";
            Query q = qm.createQuery(query, Query.SQL);
            QueryResult result = q.execute();
            NodeIterator it = result.getNodes();
            if (it.hasNext()) {
                Node n = it.nextNode();
                output = n.getProperty(JCRNode.CONTENT).getString();
                String kit = n.getProperty(JCRNode.RENDERKIT).getString();
                if (kit.equals(JCRNode.RENDERKIT_RADEOX)) {
                    RenderContext context = new BaseRenderContext();
                    RenderEngine engine = new BaseRenderEngine();
                    output = engine.render(output, context);
                }
                if (kit.equals(JCRNode.RENDERKIT_TEXT)) {
                    output = "<pre>" + output + "</pre>";
                }
                if (kit.equals(JCRNode.RENDERKIT_HTML)) {
                    // do some wiki markup link replacement for links to other tabs/pages within the portal
                    // [[This|myRef]] will be <a href=".../myRef">This</a>
                    PortalConfigService portalConfigService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
                    String localPortalURLdeploy = portalConfigService.getProperty("gridsphere.deploy");
                    String localPortalURLcontext = portalConfigService.getProperty("gridsphere.context");
                    String patternFindLinks = "\\[{2}[A-Za-z0-9\\s]++\\|{1}[A-Za-z0-9/\\s]++\\|{1}[A-Za-z0-9/\\s]++\\]{2}";
                    for (Matcher m = Pattern.compile(patternFindLinks).matcher(output); m.find();) {
                        String match = m.toMatchResult().group().toString();
                        String match2 = match.substring(2, match.length() - 2); // subtract [[ and ]]
                        String name = match2.substring(0, match2.indexOf("|")); // get the name
                        String temp = match2.substring(match2.indexOf("|") + 1, match2.length());
                        String layout = temp.substring(0, temp.indexOf("|"));  // layout name
                        String id = temp.substring(temp.indexOf("|") + 1, temp.length()); // fragment id
                        String link = "";
                        try {
                            link = URLEncoder.encode(id, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        String replaceString = "<a href=\"/" + localPortalURLdeploy + "/" + localPortalURLcontext + "/"
                                + layout + "/" + link + "\">" + name + "</a>";
                        output = output.replace(match, replaceString);
                    }
                    output = "<div class=\"gridsphere-content\">" + output + "</div>";
                }
            } else {
                output = "Content " + contentFile + "not found!";
            }
        } catch (Exception e) {
            log.error(e);
            output = "<b>An error occurred retrieving content!</b>";
        } finally {
            if (session != null) session.logout();
        }
        return output;
    }
}
