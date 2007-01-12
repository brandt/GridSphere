package org.gridsphere.services.core.jcr;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.List;

public interface JCRService {


    /**
     * Returns the content of the nodename.
     *
     * @param nodename
     * @return content of the nodename
     */
    public String getContent(String nodename);

    /**
     * Returns a JCR session.
     *
     * @return jcr session
     * @throws RepositoryException
     * @throws NamingException
     */
    Session getSession() throws RepositoryException, NamingException;

    /**
     * Queryies the repository.
     *
     * @param query SQL query
     * @return Nodeiterator containing the results
     * @throws NamingException
     * @throws RepositoryException
     */
    NodeIterator query(String query, Session session) throws NamingException, RepositoryException;

    /**
     * Checks if the node with a given id exists.
     *
     * @param gsid
     * @return
     * @throws NamingException
     * @throws RepositoryException
     */
    boolean existsContentDocument(String gsid) throws NamingException, RepositoryException;

    /**
     * Dumps the content of the repository into one file in the specified directory. The name of the file
     * will be something like PortalContentBackup-YEAR.MONTH.DAY-HOUR:MIN
     *
     * @param fullPathBackupDir Directory for backupContent
     * @throws javax.naming.NamingException
     * @throws java.io.IOException
     * @throws javax.jcr.RepositoryException
     */
    public void backupContent(String fullPathBackupDir) throws NamingException, RepositoryException, IOException;

    /**
     * Imports the content of a file into the content repository. It will be deployed at the root level and will
     * replace existing nodes.
     *
     * @param fullPathFileName file with the content information
     * @throws javax.naming.NamingException
     * @throws java.io.IOException
     * @throws javax.jcr.RepositoryException
     */
    public void importContent(String fullPathFileName) throws NamingException, RepositoryException, IOException;

    public Node getGridSphereRootNode(Session s) throws RepositoryException;

    /** =============================================================================================================== */

    /**
     * Returns the document with the given path.
     *
     * @param path
     * @return
     */
    public ContentDocument getDocument(String path) throws ContentException;

    public ContentDocument getDocumentByUUID(String uuid) throws ContentException;

    /**
     * Save the given document.
     *
     * @param document
     */
    public void saveDocument(ContentDocument document) throws ContentException;

    /**
     * List documents in the path (relative to GridSphereDocRoot). Flat, non-recursive.
     *
     * @param path
     * @return
     */
    public List<ContentDocument> listChildContentDocuments(String path) throws ContentException;


    public void removeDocument(ContentDocument doc) throws ContentException;

    public void removeDocumentByUuid(String uuid) throws ContentException;
}


