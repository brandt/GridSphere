/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence.hibernate;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

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

    private static Properties prop = new Properties();

    private static final ThreadLocal threadSession = new ThreadLocal();
    private static final ThreadLocal threadTransaction = new ThreadLocal();


    public PersistenceManagerRdbmsImpl() {
        ServletContext ctx = GridSphereConfig.getServletContext();
        String origPropsPath = ctx.getRealPath("/WEB-INF/persistence/hibernate.properties");
        String gsPropsPath = ctx.getRealPath("/WEB-INF/CustomPortal/database/hibernate.properties");
        String mappingPath = ctx.getRealPath("/WEB-INF/persistence");
        File propsFile = new File(gsPropsPath);
        try {
            if (!propsFile.exists()) {
                GridSphereConfig.copyFile(new File(origPropsPath), propsFile);
                log.info("Copying hibernate properties from " + origPropsPath + " to " + gsPropsPath);
            }
            prop.load(ctx.getResourceAsStream("/WEB-INF/CustomPortal/database/hibernate.properties"));
            Configuration cfg = loadConfiguration(mappingPath, prop);
            factory = cfg.buildSessionFactory();
        } catch (IOException e) {
            log.error("Unable to copy file from " + origPropsPath + " to " + gsPropsPath);
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
        log.info("Creating Hibernate RDBMS Impl using config in " + persistenceConfigDir);

        String filename = persistenceConfigDir + File.separator + "hibernate.properties";
        File file = new File(filename);

        log.debug("Loading properties from :" + file);
        Properties hibernateProperties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(file);
            hibernateProperties.load(fis);
            Configuration cfg = loadConfiguration(persistenceConfigDir, hibernateProperties);
            factory = cfg.buildSessionFactory();
        } catch (FileNotFoundException e) {
            log.error("Could not find Hibernate config file. Make sure you have a file  " + file);
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

            if (children == null) {
                // Either dir does not exist or is not a directory
            } else {
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
            org.gridlab.gridsphere.core.persistence.Session s = (org.gridlab.gridsphere.core.persistence.Session)threadSession.get();
            if (s == null) {
                Session session = factory.openSession();
                org.gridlab.gridsphere.core.persistence.Session gsSession = new SessionImpl(session);
                threadSession.set(gsSession);
            }
            return (org.gridlab.gridsphere.core.persistence.Session)threadSession.get();
        } catch (Exception e) {
            log.error("Could not open session", e);
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
            doTransaction(object, "", CMD_CREATE);
        } catch (Exception e) {
            log.error("Could not create object", e);
            throw new PersistenceManagerException(e);
        }
    }

    public void update(Object object) throws PersistenceManagerException {
        try {
            doTransaction(object, "", CMD_UPDATE);
        } catch (Exception e) {
            log.error("Could not update object", e);
            throw new PersistenceManagerException(e);
        }
    }

    public void saveOrUpdate(Object object) throws PersistenceManagerException {
        try {
            doTransaction(object, "", CMD_SAVEORUPDATE);
        } catch (Exception e) {
            log.error("Could not create or update object", e);
            throw new PersistenceManagerException(e);
        }
    }

    public Object restore(String query) throws PersistenceManagerException {
        List list = restoreList(query);
        if (list.size() == 0) {
            return null;
//            throw new PersistenceManagerException("No object found with given query.");
        }
        return restoreList(query).get(0);
    }


    public List restoreList(String query) throws PersistenceManagerException {
        try {
            return (List) doTransaction(null, query, CMD_RESTORE_LIST);
        } catch (Exception e) {
            log.error("Could not restore list", e);
            throw new PersistenceManagerException(e);
        }
    }

    public void delete(Object object) throws PersistenceManagerException {
        try {
            doTransaction(object, "", CMD_DELETE);
        } catch (Exception e) {
            log.error("Could not delete object", e);
            throw new PersistenceManagerException(e);
        }
    }

    public void deleteList(String query) throws PersistenceManagerException {
        try {
            doTransaction(null, query, CMD_DELETE_LIST);
        } catch (Exception e) {
            log.error("Could not delete list", e);
            throw new PersistenceManagerException(e);
        }
    }

    public void beginTransaction() throws PersistenceManagerException {
        org.gridlab.gridsphere.core.persistence.Transaction tx = (org.gridlab.gridsphere.core.persistence.Transaction) threadTransaction.get();
        if (tx == null) {
            tx = getSession().beginTransaction();
            threadTransaction.set(tx);
        }
    }

    public void commitTransaction() throws PersistenceManagerException {
        org.gridlab.gridsphere.core.persistence.Transaction tx = (org.gridlab.gridsphere.core.persistence.Transaction) threadTransaction.get();
        try {
            if ( tx != null && !tx.wasCommitted()
                    && !tx.wasRolledBack() )
                tx.commit();
            threadTransaction.set(null);
        } catch (Exception ex) {
            rollbackTransaction();
            throw new PersistenceManagerException(ex);
        }
    }

    public void rollbackTransaction() throws PersistenceManagerException {
        org.gridlab.gridsphere.core.persistence.Transaction tx = (org.gridlab.gridsphere.core.persistence.Transaction) threadTransaction.get();
        try {
            threadTransaction.set(null);
            if ( tx != null && !tx.wasCommitted()
                    && !tx.wasRolledBack() ) {
                tx.rollback();
            }
        } catch (Exception ex) {
            throw new PersistenceManagerException(ex);
        } finally {
            closeSession();
        }
    }

    private Object doTransaction(Object object, String query, int command) throws Exception {
        Session session = null;
        Transaction tx = null;
        Object result = null;
        Query q = null;

        try {
            session = factory.openSession();
            // Open a new Session, if this thread has none yet
            tx = null;
            tx = session.beginTransaction();
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
                    result = q.list();
                    break;
                case CMD_RESTORE:
                    q = session.createQuery(query);
                    result = q.list().get(0);
                    break;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error("Could not complete transaction", e);
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public void destroy() throws PersistenceManagerException {
        try {
            closeSession();
            factory.close();
        } catch (HibernateException e) {
            log.error("Could not close session factory", e);
            throw new PersistenceManagerException(e);
        }
    }

}