/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence;

import java.io.IOException;
import java.util.List;

public interface PersistenceManagerXml {

    public void setMappingPath(String mappingPath);

    public List getMappingPaths();

    public void setDescriptorPath(String descriptorPath);

    public String getDescriptorPath();

    public void save(Object object) throws PersistenceManagerException, IOException;

    public Object load() throws IOException, PersistenceManagerException;
}
