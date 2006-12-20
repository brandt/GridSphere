package org.gridsphere.services.core.jcr;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.naming.NamingException;
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
    boolean exists(String gsid) throws NamingException, RepositoryException;


    /**
     * Returns all nodes as a list.
     *
     * @return List of nodes.
     */
    public List<String> getAllNodeNames();
}
