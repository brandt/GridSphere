/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence.hibernate;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.QueryFilter;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.Configuration;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;

/**
 *
 */
public class PersistenceManagerRdbmsImpl implements PersistenceManagerRdbms {
    private static transient PortletLog log = SportletLog.getInstance(PersistenceManagerRdbmsImpl.class);

    private SessionFactory factory = null;
    private final static int CMD_DELETE = 1;
    private final static int CMD_DELETE_LIST = 2;
    private final static int CMD_RESTORE = 3;
    private final static int CMD_RESTORE_LIST = 4;
    private final static int CMD_UPDATE = 5;
    private final static int CMD_CREATE = 6;
    private final static int CMD_SAVEORUPDATE = 7;
    private final static int CMD_COUNT = 8;

    private static Properties prop = new Properties();

    private static final ThreadLocal threadSession = new ThreadLocal();
    private static final ThreadLocal threadTransaction = new ThreadLocal();

    public PersistenceManagerRdbmsImpl() {
        ServletContext ctx = GridSphereConfig.getServletContext();
        String gsPropsPath = ctx.getRealPath("/WEB-INF/CustomPortal/database/hibernate.properties");
        String mappingPath = ctx.getRealPath("/WEB-INF/persistence");
        try {
            prop.load(ctx.getResourceAsStream("/WEB-INF/CustomPortal/database/hibernate.properties"));
            Configuration cfg = loadConfiguration(mappingPath, prop);
            factory = cfg.buildSessionFactory();
        } catch (IOException e) {
            log.error("Unable to load file: " + gsPropsPath);
        } catch (HibernateException e) {
            log.error("Could not instantiate Hibernate Factory", e);
        }
        log.info("Creating Hibernate RDBMS Impl using config in " + gsPropsPath);
    }


    public void resetDatabase(String connURL) {

        ServletContext ctx = GridSphereConfig.getServletContext();
        String mappingPath = ctx.getRealPath("/WEB-INF/persistence");
        String gsPropsPath = ctx.getRealPath("/WEB-INF/CustomPortal/database/hibernate.properties");
        prop.setProperty("hibernate.connection.url", connURL);
        try {
            prop.store(new FileOutputStream(new File(gsPropsPath)), "hibernate.properties");
            if (factory != null) factory.close();
            DBTask task = new DBTask();
            task.setConfigDir(ctx.getRealPath(""));
            task.setAction(DBTask.ACTION_CREATE);
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Configuration cfg = loadConfiguration(mappingPath, prop);
            factory = cfg.buildSessionFactory();
        } catch (HibernateException e) {

        }
    }

    public PersistenceManagerRdbmsImpl(String persistenceConfigDir) {
        this(persistenceConfigDir, persistenceConfigDir);
    }

    public PersistenceManagerRdbmsImpl(String pathTohibernateProperties, String pathToHibernateMappings) {
        log.info("Creating Hibernate RDBMS Impl using config in " + pathTohibernateProperties);
        String filename = pathTohibernateProperties + File.separator + "hibernate.properties";
        File file = new File(filename);
        log.debug("Loading properties from :" + file);
        Properties hibernateProperties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(file);
            hibernateProperties.load(fis);
            Configuration cfg = loadConfiguration(pathToHibernateMappings, hibernateProperties);
            factory = cfg.buildSessionFactory();
        } catch (FileNotFoundException e) {
            log.error("Could not find Hibernate config file. Make sure you have a file " + file);
        } catch (IOException e) {
            log.error("Could not load Hibernate config File", e);
        } catch (HibernateException e) {
            log.error("Could not instantiate Hibernate Factory", e);
        }
    }

    /**
     * Load the mappingfiles from the given dirctory location
     *
     * @param mappingPath the file path to find hibernate mapping files
     * @param hibernateProperties the hibernate properties
     * @return a hibernate configuration object
     */
    private Configuration loadConfiguration(String mappingPath, Properties hibernateProperties) {
        Configuration cfg = null;
        try {
            cfg = new Configuration();
            cfg.setProperties(hibernateProperties);
            File mappingdir = new File(mappingPath);
            String[] children = mappingdir.list();

            if (children != null) {
                // Create list from children array
                List filenameList = Arrays.asList(children);
                // Ensure that this list is sorted alphabetically
                Collections.sort(filenameList);
                for (Iterator filenames = filenameList.iterator(); filenames.hasNext();) {
                    String filename = (String) filenames.next();
                    if (filename.endsWith(".hbm.xml")) {
                        // Get filename of file or directory
                        log.debug("add hbm file :" + mappingPath + File.separator + filename);
                        cfg.addFile(mappingPath + File.separator + filename);
                    }
                }
            }

        } catch (MappingException e) {
            log.error("Could not load Hibernate mapping files", e);
        }

        return cfg;
    }


    /**
     * Creates a session to conduct operations on database
     *
     * @return session  Session to conduction operations on database
     * @throws PersistenceManagerException
     */

    public org.gridlab.gridsphere.core.persistence.Session getSession() throws PersistenceManagerException {
        try {
            net.sf.hibernate.Session s = (net.sf.hibernate.Session)threadSession.get();
            if (s == null) {
                s = factory.openSession();
                threadSession.set(s);
            }
            return new SessionImpl(s);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void closeSession() throws PersistenceManagerException {
        org.gridlab.gridsphere.core.persistence.Session s = (org.gridlab.gridsphere.core.persistence.Session) threadSession.get();
        threadSession.set(null);
        if (s != null && s.isOpen()) s.close();
    }

    public void create(Object object) throws PersistenceManagerException {
        try {
            doTransaction(object, "", CMD_CREATE, null);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void update(Object object) throws PersistenceManagerException {
        try {
            doTransaction(object, "", CMD_UPDATE, null);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void saveOrUpdate(Object object) throws PersistenceManagerException {
        try {
            doTransaction(object, "", CMD_SAVEORUPDATE, null);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public Object restore(String query) throws PersistenceManagerException {
        List list = restoreList(query);
        if (list.size() == 0) return null;
        return restoreList(query).get(0);
    }


    public List restoreList(String query) throws PersistenceManagerException {
        try {
            return (List) doTransaction(null, query, CMD_RESTORE_LIST, null);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public List restoreList(String query, QueryFilter queryFilter) throws PersistenceManagerException {
        try {
            return (List) doTransaction(null, query, CMD_RESTORE_LIST, queryFilter);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public int count(String query) throws PersistenceManagerException {
        try {
            Integer i = (Integer)doTransaction(null, query, CMD_COUNT, null);
            return i.intValue();
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void delete(Object object) throws PersistenceManagerException {
        try {
            doTransaction(object, "", CMD_DELETE, null);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void deleteList(String query) throws PersistenceManagerException {
        try {
            doTransaction(null, query, CMD_DELETE_LIST, null);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    private net.sf.hibernate.Session currentSession() throws net.sf.hibernate.HibernateException {
        Session s = (Session)threadSession.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) {
            s = factory.openSession();
            threadSession.set(s);
        }
        return s;
    }

    public void beginTransaction() throws PersistenceManagerException {
        try {
            net.sf.hibernate.Transaction transaction = currentSession().beginTransaction();
            threadTransaction.set(transaction);
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void commitTransaction() throws PersistenceManagerException {
        try {
            net.sf.hibernate.Transaction transaction = (net.sf.hibernate.Transaction)threadTransaction.get();
            if (transaction != null) transaction.commit();
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    public void rollbackTransaction() throws PersistenceManagerException {
        try {
            net.sf.hibernate.Transaction transaction = (net.sf.hibernate.Transaction)threadTransaction.get();
            if (!transaction.wasRolledBack()) {
                log.debug("Trying to rollback database transaction after exception");
                transaction.rollback();
            }
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

    private Object doTransaction(Object object, String query, int command, QueryFilter queryFilter) throws HibernateException {
        net.sf.hibernate.Session session = null;
        Object result = null;
        Query q = null;
        session = currentSession();

        switch (command) {
            case CMD_CREATE:
                session.save(object);
                break;
            case CMD_DELETE:
                session.delete(object);
                break;
            case CMD_DELETE_LIST:
                session.delete(query);
                break;
            case CMD_UPDATE:
                session.update(object);
                break;
            case CMD_SAVEORUPDATE:
                session.saveOrUpdate(object);
                break;
            case CMD_RESTORE_LIST:
                q = session.createQuery(query);
                if (queryFilter != null) {
                    q.setFirstResult(queryFilter.getFirstResult());
                    q.setMaxResults(queryFilter.getMaxResults());
                }
                result = q.list();
                break;
            case CMD_RESTORE:
                q = session.createQuery(query);
                result = q.list().get(0);
                break;
            case CMD_COUNT:
                q = session.createQuery(query);
                result = new Integer(q.list().size());
                break;
        }

        return result;
    }

    public void destroy() throws PersistenceManagerException {
        try {
            closeSession();
            factory.close();
        } catch (HibernateException e) {
            throw new PersistenceManagerException(e);
        }
    }

}