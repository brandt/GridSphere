/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: SportletServiceCollection.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet.service.spi.impl.descriptor;

import java.util.List;
import java.util.Vector;

/**
 * The <code>SportletServiceCollection</code> provides a list of
 * <code>SportletServiceDefinition</code> entries.
 */
public class PortletServiceCollection {

    private List<PortletServiceDefinition> servicesList = new Vector<PortletServiceDefinition>();

    /**
     * Sets the list of portlet service definitions
     *
     * @param servicesList a <code>Vector</code> containing
     *                     portlet service definitions
     * @see PortletServiceDefinition
     */
    public void setPortletServicesList(Vector<PortletServiceDefinition> servicesList) {
        this.servicesList = servicesList;
    }

    /**
     * Returns the list of portlet service definitions
     *
     * @return a list containing the portlet service definitions
     * @see PortletServiceDefinition
     */
    public List<PortletServiceDefinition> getPortletServicesList() {
        return servicesList;
    }

}
