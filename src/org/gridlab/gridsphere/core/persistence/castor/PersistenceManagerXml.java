/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence.castor;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.gridlab.gridsphere.core.persistence.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.xml.sax.InputSource;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

/**
 * The Class provides easy access to marshal/unmarshal objects to XML files
 */
public class PersistenceManagerXml  {

    protected final static PortletLog log = SportletLog.getInstance(PersistenceManagerXml.class);

    private String mappingURL = null;
    private String descriptorURL = null;

    /**
     * PersistenceManagerXml default constructor
     */
    private PersistenceManagerXml() {}

    /**
     * Creates an instance of PersistenceManagerXml from a descriptor and mapping URL
     * @param descriptorURL the descriptor location
     * @param mappingURL the mapping location
     */
    private PersistenceManagerXml(String descriptorURL, String mappingURL) {
        this.mappingURL = mappingURL;
        this.descriptorURL = descriptorURL;
    }

    /**
     * Returns an instance of a PersistenceManagerXML from a descriptor and mapping URL
     * @param descriptorURL the descriptor location
     * @param mappingURL the mapping location
     * @return an instance of PersistenceManagerXml
     */
    public static PersistenceManagerXml createPersistenceManager(String descriptorURL, String mappingURL) {
        return new PersistenceManagerXml(descriptorURL, mappingURL);
    }

    /**
     * Return the mapping URL
     *
     * @return the mapping URL
     */
    public String getMappingURL() {
        return mappingURL;
    }

    /**
     * Returns the filename of the mappingfile
     *
     * @return name of the mappingfile
     */
    public String getDescriptorURL() {
        return descriptorURL;
    }

    /**
     * Updates an object in the xml file (same as create)
     *
     * @throws PersistenceManagerException if the update failed
     * @param object update 'object'
     */
    public void update(Object object) throws IOException, PersistenceManagerException {
        create(object);
    }

    /**
     * Marshals the given object to an xml file
     *
     * @param object object to be marshalled
     * @throws PersistenceManagerException if the configuration was wrong
     * @throws IOException if an I/O error occurs
     */
    public void create(Object object) throws PersistenceManagerException, IOException {
        try {
            FileWriter filewriter = new FileWriter(descriptorURL);

            Marshaller marshal = new Marshaller(filewriter);
            Mapping map = new Mapping();
            map.loadMapping(mappingURL);
            marshal.setMapping(map);
            marshal.marshal(object);
            filewriter.close();
            Class cl = object.getClass();
            log.debug("Wrote object of type " + cl.getName() + " to XMLFile " + descriptorURL);
        } catch (ValidationException e) {
            log.error("Unable to marshal object: " + e.getException().toString());
            throw new PersistenceManagerException("Validation Error" + e.getException().toString());
        } catch (MarshalException e) {
            log.error("Unable to marshal object: " + e.getException().toString());
            throw new PersistenceManagerException("Marshal Error: " + e.getException().toString());
        } catch (MappingException e) {
            log.error("Unable to marshal object: " + e.getException().toString());
            e.printStackTrace();
            throw new PersistenceManagerException("Mapping Error" + e.getException().toString());
        }

    }

    /**
     * restores an object from an xml file
     *
     * @return object which was unmarshalled
     * @throws PersistenceManagerException if restore was not succsessful
     * @throws IOException if there was a configurationerror
     */
    public Object restoreObject() throws  IOException, PersistenceManagerException {

        Object object = null;

        try {
            log.info("Using getConnectionURL() " + descriptorURL);
            FileReader filereader = null;

            filereader = new FileReader(descriptorURL);

            Mapping mapping = new Mapping();

            mapping.loadMapping(mappingURL);
            log.info("Using  getMappingFile()" + mappingURL);

            Unmarshaller unmarshal = new Unmarshaller(mapping);
            object = unmarshal.unmarshal(filereader);

        } catch (MappingException e) {
            log.error("MappingException: " + e.getException().toString());
            throw new PersistenceManagerException("Mapping Error" + e.getException().toString());
        } catch (MarshalException e) {
            log.error("MarshalException " + e.getException().toString());
            throw new PersistenceManagerException("Marshal Error" + e.getException().toString());
        } catch (ValidationException e) {
            log.error("ValidationException " + e.getException().toString());
            throw new PersistenceManagerException("Validation Error" + e.getException().toString());
        }
        return object;
    }


}


