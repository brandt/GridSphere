/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence;

import java.util.List;

public interface PersistenceManagerRdbms {

    public void create(Object object) throws PersistenceManagerException;

    public void update(Object object) throws PersistenceManagerException;

    public Object restore(String query) throws PersistenceManagerException;

    /**
     * Restores objects from storage
     *
     * @return list of objects from OQL query
     * @throws PersistenceManagerException is a persistence error occurs
     */
    public List restoreList(String query) throws PersistenceManagerException;

    public void delete(Object object) throws PersistenceManagerException;

    public Object getObjectByOid(Class cl, String Oid) throws PersistenceManagerException;
}
