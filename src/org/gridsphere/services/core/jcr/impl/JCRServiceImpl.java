package org.gridsphere.services.core.jcr.impl;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.jndi.RegistryHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.jcr.JCRNode;
import org.gridsphere.services.core.jcr.JCRService;

import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.util.*;


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

        } catch (NamingException e) {
            e.printStackTrace();
            log.error("Could not start repository.");
        } catch (RepositoryException e) {
            e.printStackTrace();
            log.error("Could not start repository.");
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
        } catch (RepositoryException e) {
            e.printStackTrace();
            log.error("");
        } catch (NamingException e) {
            e.printStackTrace();
            log.error("");
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
}
