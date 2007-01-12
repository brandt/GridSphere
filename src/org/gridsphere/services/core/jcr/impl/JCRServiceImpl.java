package org.gridsphere.services.core.jcr.impl;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.jndi.RegistryHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.jcr.ContentDocument;
import org.gridsphere.services.core.jcr.ContentException;
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
import java.io.*;
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

            Node root = s.getRootNode();
            Node gsRootNode = null;
            if (!root.hasNode(JCRNode.GS_ROOT_NODE_NAME)) {
                gsRootNode = root.addNode(JCRNode.GS_ROOT_NODE_NAME);
                s.save();
                log.info("Adding GS Root Node with Path " + gsRootNode.getPath());
            } else {
                gsRootNode = root.getNode(JCRNode.GS_ROOT_NODE_NAME);
            }

            Node gsDocRootNode = null;
            if (!gsRootNode.hasNode(JCRNode.GS_ROOT_CONTENTDOCUMENT_NAME)) {
                gsDocRootNode = gsRootNode.addNode(JCRNode.GS_ROOT_CONTENTDOCUMENT_NAME);
                log.info("Adding GS ContentDocument Root Node with Path " + gsDocRootNode.getPath());
                s.save();
            } else {
                gsDocRootNode = gsRootNode.getNode(JCRNode.GS_ROOT_CONTENTDOCUMENT_NAME);
            }

            // load all .html files in repositorypath, create Nodes out of them (if they do not exist) and delete them
            File repDir = new File(repositorypath);
            String[] children = repDir.list();

            if (children != null) {
                // Create list from children array
                List filenameList = Arrays.asList(children);
                for (Iterator filenames = filenameList.iterator(); filenames.hasNext();) {
                    String filename = (String) filenames.next();
                    if (filename.endsWith(".html")) {
                        // Get filename of file or directory
                        String checkNodeName = filename.substring(0, filename.length() - 5);

                        if (!existsContentDocument(checkNodeName)) {

                            StringBuffer fileContent = new StringBuffer();

                            try {
                                BufferedReader in = new BufferedReader(new FileReader(repositorypath + File.separator + filename));
                                String str;
                                while ((str = in.readLine()) != null) {
                                    fileContent.append(str);
                                }
                                in.close();
                                ContentDocument doc = new ContentDocument();
                                doc.setContent(fileContent.toString());
                                doc.setTitle(checkNodeName);
                                doc.setAuthor("System");
                                doc.setMimeType(JCRNode.RENDERKIT_HTML);

                                saveDocument(doc);

                                log.info("Adding " + filename + " as document to ContentManagement.");
//                                File deleteFile = new File(repositorypath + File.separator + filename);
//                                deleteFile.delete();

                            } catch (IOException e) {
                                log.error("Could not read file " + filename);
                            }

                        }
                    }
                }

            }

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

    public boolean existsContentDocument(String path) {
        boolean result = false;
        try {
            getDocument(path);
            result = true;
        } catch (ContentException e) {
            result = false;
        }
        return result;
    }

    public String getContent(String nodename) {
        // handle content management
        String output = "";
        try {
            ContentDocument doc = getDocument(nodename);
            String kit = doc.getMimeType();
            output = doc.getContent();
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
            }
        } catch (ContentException e) {
            output = "Content " + nodename + "not found!";
        }
        return output;
    }

    /**
     * Checks the length of the String
     *
     * @param s       String to be checked
     * @param length  length which the result should have
     * @param filler  String to be appended/prepended
     * @param prepend should the filler prependend (otherwise append)
     * @return changed string
     */
    private String checkLength(String s, int length, String filler, boolean prepend) {
        if (s.length() != length) {
            for (int i = 1; i < length; i++) {
                if (prepend) {
                    s = filler + s;
                } else {
                    s = s + filler;
                }
            }
        }
        return s;
    }

    public void backupContent(String fullPathBackupDir) throws NamingException, RepositoryException, IOException {
        File bDir = new File(fullPathBackupDir);
        if (!bDir.exists()) {
            bDir.mkdirs();
        }
        if (bDir.exists() && bDir.isDirectory()) {
            Calendar c = new GregorianCalendar();
            int m = c.get(Calendar.MONTH) + 1;
            String month = checkLength(m + "", 2, "0", true);
            String backupName = "PortalContentBackup-" + c.get(Calendar.YEAR) + "." + month + "." +
                    checkLength(c.get(Calendar.DAY_OF_MONTH) + "", 2, "0", true) + "-" +
                    checkLength(c.get(Calendar.HOUR_OF_DAY) + "", 2, "0", true) + ":" +
                    checkLength(c.get(Calendar.MINUTE) + "", 2, "0", true) + ".xml";
            String filename = fullPathBackupDir + File.separator + backupName;
            Session s = null;
            try {
                s = getSession();
                FileOutputStream x = new FileOutputStream(new File(filename));
                s.exportSystemView("/" + JCRNode.GS_ROOT_NODE_NAME, x, false, false);
            } catch (RepositoryException e) {
                log.error("Repository Exception.");
                throw new RepositoryException(e);
            } catch (NamingException e) {
                log.error("Naming Excpetion.");
                throw new NamingException();
            } catch (FileNotFoundException e) {
                log.error("FileNotFoundException. " + filename);
                throw new FileNotFoundException();
            } catch (IOException e) {
                log.error("IOException.");
                throw new IOException("Backup failed. IOException.");
            } finally {
                if (s != null) s.logout();
            }
        } else {
            log.error("ContentBackup failed '" + fullPathBackupDir + "' is NOT a directory (most likely a file)");
            throw new IOException("Backup failed. Path " + fullPathBackupDir + " exists and is not a directoy.");
        }
    }


    public void importContent(String fullPathFileName) throws NamingException, RepositoryException, IOException {
        Session s = null;
        try {
            s = getSession();
            FileInputStream x = new FileInputStream(new File(fullPathFileName));
            s.importXML("/", x, javax.jcr.ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING);
            x.close();
            s.save();
        } catch (RepositoryException e) {
            log.error("Repository Exception.");
            throw new RepositoryException(e);
        } catch (NamingException e) {
            log.error("Naming Excpetion.");
            throw new NamingException();
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException. " + fullPathFileName);
            throw new FileNotFoundException();
        } catch (IOException e) {
            log.error("IOException.");
            throw new IOException("Backup failed. IOException.");
        } finally {
            if (s != null) s.logout();
        }
    }


    public Node getGridSphereRootNode(Session s) throws RepositoryException {
        Node n = s.getRootNode();
        Node gsRoot = n.getNode(JCRNode.GS_ROOT_NODE_NAME);
        return gsRoot;
    }


    public ContentDocument getDocument(String path) throws ContentException {
        Session session = null;
        ContentDocument result = null;
        try {
            session = getSession();
            Node node = (Node) session.getItem(JCRNode.GS_ROOT_CONTENTDOCUMENT_PATH + "/" + path);
            result = createDocument(node);
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ContentException("JCR Repository Error.");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ContentException("JCR Naming Error.");
        } finally {
            if (session != null) session.logout();
        }

        return result;
    }

    public void saveDocument(ContentDocument document) throws ContentException {
        Session session = null;
        Node node = null;

        try {
            session = getSession();
            if (document.getUuid() == null) {
                Node parentNode = (Node) session.getItem(document.getParentPath());
                node = parentNode.addNode(document.getTitle());
                node.addMixin("mix:referenceable"); // make it to have uuid's
            } else {
                node = session.getNodeByUUID(document.getUuid());
            }
            node.setProperty(JCRNode.CONTENT, document.getContent());
            node.setProperty(JCRNode.TITLE, document.getTitle());
            session.save();

        } catch (RepositoryException e) {
            throw new ContentException("JCR Repository Error.");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ContentException("JCR Naming Error.");
        } finally {
            if (session != null) session.logout();
        }
    }

    protected Node getGsDocRootNode(Session s) throws RepositoryException {
        Node rootNode = s.getRootNode();
        Node result = rootNode.getNode(JCRNode.GS_ROOT_NODE_NAME + "/" + JCRNode.GS_ROOT_CONTENTDOCUMENT_NAME);
        return result;
    }

    protected ContentDocument createDocument(Node n) throws ContentException {
        ContentDocument doc = new ContentDocument();
        try {
            doc.setContent(n.getProperty(JCRNode.CONTENT).getString());
            doc.setTitle(n.getProperty(JCRNode.TITLE).getString());
            doc.setUuid(n.getUUID());
        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ContentException("Could not create document from node.");
        }
        return doc;
    }

    public List<ContentDocument> listChildContentDocuments(String path) throws ContentException {
        Session session = null;
        String childPath = JCRNode.GS_ROOT_CONTENTDOCUMENT_PATH + path;
        List<ContentDocument> result = new ArrayList<ContentDocument>();
        try {
            session = getSession();
            Node node = (Node) session.getItem(childPath);
            NodeIterator ni = node.getNodes();
            while (ni.hasNext()) {
                Node n = ni.nextNode();
                ContentDocument doc = new ContentDocument();
                result.add(createDocument(n));
            }

        } catch (RepositoryException e) {
            e.printStackTrace();
            throw new ContentException("JCR Repository Error.");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ContentException("JCR Naming Error.");
        } finally {
            if (session != null) session.logout();
        }
        return result;
    }


    public ContentDocument getDocumentByUUID(String uuid) throws ContentException {
        Session session = null;
        ContentDocument doc = null;
        try {
            session = getSession();
            Node n = session.getNodeByUUID(uuid);
            doc = createDocument(n);
        } catch (RepositoryException e) {
            throw new ContentException("JCR Repository Error.");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ContentException("JCR Naming Error.");
        } finally {
            if (session != null) session.logout();
        }
        return doc;
    }

    public void removeDocument(ContentDocument doc) throws ContentException {
        removeDocumentByUuid(doc.getUuid());
    }

    public void removeDocumentByUuid(String uuid) throws ContentException {
        Session session = null;
        try {
            session = getSession();
            Node n = session.getNodeByUUID(uuid);
            n.remove();
            session.save();
        } catch (RepositoryException e) {
            throw new ContentException("JCR Repository Error.");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ContentException("JCR Naming Error.");
        } finally {
            if (session != null) session.logout();
        }
    }
}
