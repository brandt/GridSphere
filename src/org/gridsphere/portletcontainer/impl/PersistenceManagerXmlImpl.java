/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PersistenceManagerXmlImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The PersistenceManagerXmlImpl provides easy access to marshal/unmarshal Java objects to XML files
 */
public class PersistenceManagerXmlImpl implements PersistenceManagerXml {

    protected Log log = LogFactory.getLog(PersistenceManagerXmlImpl.class);

    private List mappingPaths = new ArrayList();
    private String descriptorPath = null;

    /**
     * PersistenceManagerXmlImpl default constructor
     */
    private PersistenceManagerXmlImpl() {
    }

    /**
     * Creates an instance of PersistenceManagerXmlImpl from a descriptor and mapping URL
     *
     * @param descriptorPath the descriptor location
     * @param mappingPath    the mapping location
     */
    public PersistenceManagerXmlImpl(String descriptorPath, String mappingPath) {
        this.descriptorPath = descriptorPath;
        addMappingPath(mappingPath);
    }

    /**
     * Creates an instance of PersistenceManagerXmlImpl from a descriptor and mapping URL
     *
     * @param descriptorPath the descriptor location
     * @param mappingURL    the mapping location expressed as a URL
     */
    public PersistenceManagerXmlImpl(String descriptorPath, URL mappingURL) {
        this.descriptorPath = descriptorPath;
        addMappingPath(mappingURL);
    }

    public void addMappingPath(String path) {
        mappingPaths.add(path);
    }

    public void addMappingPath(URL path) {
        mappingPaths.add(path);
    }

    /**
     * Sets the mapping file path
     *
     * @param mappingPath the mapping file path
     */
    public void setMappingPath(String mappingPath) {
        mappingPaths.clear();
        mappingPaths.add(mappingPath);
    }

    /**
     * Sets the mapping file url
     *
     * @param mappingURL the mapping url
     */
    public void setMappingPath(URL mappingURL) {
        mappingPaths.clear();
        mappingPaths.add(mappingURL);
    }

    /**
     * Sets the descriptor file path
     *
     * @param descriptorPath the file path of the descriptor
     */
    public void setDescriptorPath(String descriptorPath) {
        this.descriptorPath = descriptorPath;
    }

    /**
     * Returns the filename of the mappingfile
     *
     * @return name of the mappingfile
     */
    public String getDescriptorPath() {
        return descriptorPath;
    }

    /**
     * Marshals the given object to an xml file
     *
     * @param object object to be marshalled
     * @throws PersistenceManagerException
     *                     if the configuration was wrong
     */
    public void save(Object object) throws PersistenceManagerException {
        try {
            Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(descriptorPath), "UTF-8"));
            FileWriter filewriter = new FileWriter(descriptorPath);
            Marshaller marshal = new Marshaller(w);
            Mapping map = new Mapping();
            for (Object mappingObj : mappingPaths) {
                log.debug("Loading mapping path " + mappingObj);
                if (mappingObj instanceof String) {
                    map.loadMapping((String) mappingObj);
                } else if (mappingObj instanceof URL) {
                    map.loadMapping((URL) mappingObj);
                }
            }
            marshal.setMapping(map);
            marshal.marshal(object);
            filewriter.close();
            Class cl = object.getClass();
            log.debug("Wrote object of type " + cl.getName() + " to XMLFile " + descriptorPath);
        } catch (ValidationException e) {
            throw new PersistenceManagerException("Validation Error", e);
        } catch (MarshalException e) {
            throw new PersistenceManagerException("Marshal Error: ", e);
        } catch (MappingException e) {
            throw new PersistenceManagerException("Mapping Error", e);
        } catch (IOException e) {
            throw new PersistenceManagerException("I/O Error", e);
        }
    }

    /**
     * restores an object from an xml file
     *
     * @return object which was unmarshalled
     * @throws PersistenceManagerException if restore was not succsessful
     */
    public Object load() throws PersistenceManagerException {
        Object object;
        try {
            log.debug("Using getConnectionURL() " + descriptorPath);
            InputSource xmlSource = new InputSource(descriptorPath);
            Mapping mapping = new Mapping();
            for (Object mappingObj : mappingPaths) {
                log.debug("Loading mapping path " + mappingObj);
                if (mappingObj instanceof String) {
                    mapping.loadMapping((String) mappingObj);
                } else if (mappingObj instanceof URL) {
                    mapping.loadMapping((URL) mappingObj);
                }
            }
            Unmarshaller unmarshal = new Unmarshaller(mapping);
            unmarshal.setValidation(true);
            unmarshal.setIgnoreExtraElements(true);
            unmarshal.setIgnoreExtraAttributes(true);
            object = unmarshal.unmarshal(xmlSource);
        } catch (MappingException e) {
            throw new PersistenceManagerException("Mapping Error", e);
        } catch (MarshalException e) {
            throw new PersistenceManagerException("Marshal Error", e);
        } catch (ValidationException e) {
            throw new PersistenceManagerException("Validation Error", e);
        } catch (IOException e) {
            throw new PersistenceManagerException("IO Error", e);
        }
        return object;
    }


}


