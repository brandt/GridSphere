package org.gridsphere.filters.impl.descriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.filters.PortalFilter;
import org.gridsphere.filters.PortalFilterConfig;
import org.gridsphere.filters.impl.PortalFilterConfigImpl;
import org.gridsphere.portletcontainer.impl.JavaXMLBindingFactory;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;

import javax.servlet.ServletConfig;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class PortalFilterDescriptor {

    private Log log = LogFactory.getLog(PortalFilterDescriptor.class);
    private List portalFilters = new ArrayList();
    private PersistenceManagerXml pmXML = null;
    private URL FILTER_MAPPING_PATH = getClass().getResource("/org/gridsphere/filters/portal-filters-mapping.xml");

    /**
     * Constructor disallows non-argument instantiation
     */
    private PortalFilterDescriptor() {
    }

    public PortalFilterDescriptor(ServletConfig config, String descriptorFile) throws PersistenceManagerException {
        pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorFile, FILTER_MAPPING_PATH);
        PortalFilterCollection portalFilterCollection = (PortalFilterCollection) pmXML.load();
        List portalFilterList = portalFilterCollection.getPortalFilterList();
        Iterator it = portalFilterList.iterator();
        while (it.hasNext()) {
            PortalFilterDefinition def = (PortalFilterDefinition)it.next();
            String filterImpl = def.getImplementation();
            try {
                PortalFilter filterClass = (PortalFilter)Class.forName(filterImpl).newInstance();
                PortalFilterConfig filterConfig = new PortalFilterConfigImpl(config, def.getConfigProperties());
                filterClass.init(filterConfig);
                portalFilters.add(filterClass);
            } catch (ClassNotFoundException e) {
                log.error("Unable to find filter class: " + filterImpl, e);
            } catch (InstantiationException e) {
                log.error("Unable to instantiate filter class: " + filterImpl, e);
            } catch (IllegalAccessException e) {
               log.error("Illegal access on filter class: " + filterImpl, e);
            }
        }
    }

    public List getPortalFilters() {
        return portalFilters;
    }

}
