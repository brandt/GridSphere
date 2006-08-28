/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PersistenceManagerXml.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.persistence;

import java.io.IOException;
import java.util.List;
import java.net.URL;

public interface PersistenceManagerXml {

    public void setMappingPath(String mappingPath);

    public void setMappingPath(URL mappingURL);

    public void setDescriptorPath(String descriptorPath);

    public String getDescriptorPath();

    public void save(Object object) throws PersistenceManagerException, IOException;

    public Object load() throws IOException, PersistenceManagerException;
}
