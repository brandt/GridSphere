package org.gridsphere.services.core.jcr;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.naming.NamingException;
import java.util.List;

public interface JCRService {


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

    boolean exists(String gsid) throws NamingException, RepositoryException;

    public List<String> getAllNodeNames();
}
