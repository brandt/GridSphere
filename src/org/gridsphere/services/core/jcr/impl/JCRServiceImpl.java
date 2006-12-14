package org.gridsphere.services.core.jcr.impl;

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
import java.util.Hashtable;

public class JCRServiceImpl implements PortletServiceProvider, JCRService {

    private static Logger log = LogManager.getLogger(JCRServiceImpl.class);
    private String repositoryconfigpath = "";
    private String repositorypath = "";

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        repositoryconfigpath = config.getServletContext().getRealPath("WEB-INF/CustomPortal/portal/");
        repositorypath = config.getServletContext().getRealPath("WEB-INF/CustomPortal/content/");
        log.debug("JCR Path " + repositoryconfigpath);
    }

    public void destroy() {

    }


    public Session getSession() throws RepositoryException, NamingException {

        // todo all String, like homedir, username etc. could be serviceconfigparams (but we do not really need that now)
        String repHomeDir = "repository";

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.jackrabbit.core.jndi.provider.DummyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "localhost");

        InitialContext ctx;
        ctx = new InitialContext(env);

        RegistryHelper.registerRepository(ctx,
                "repo",
                repositoryconfigpath + File.separator + "contentrepository.xml",
                repositorypath + File.separator + repHomeDir,
                true);

        Repository repository = (Repository) ctx.lookup("repo");

        SimpleCredentials cred = new SimpleCredentials("userid", "".toCharArray());

        return repository.login(cred, null);
    }


    public NodeIterator query(String query, Session session) throws NamingException, RepositoryException {
        NodeIterator it = null;
        Workspace ws = session.getWorkspace();
        QueryManager qm = null;
        qm = ws.getQueryManager();
        Query q = qm.createQuery(query, Query.SQL);
        QueryResult res = q.execute();
        it = res.getNodes();

        return it;
    }


    public boolean exists(String gsid) throws NamingException, RepositoryException {
        boolean result = false;
        String query = "select * from nt:base where " + JCRNode.GSID + "='" + gsid + "'";
        Session session = getSession();
        NodeIterator it = query(query, session);
        if (it.hasNext()) result = true;
        return result;
    }
}
