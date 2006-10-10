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
public class SportletServiceCollection {

    private List servicesList = new Vector();

    /**
     * Sets the list of portlet service definitions
     *
     * @param servicesList a <code>Vector</code> containing
     *                     portlet service definitions
     * @see SportletServiceDefinition
     */
    public void setPortletServicesList(Vector servicesList) {
        this.servicesList = servicesList;
    }

    /**
     * Returns the list of portlet service definitions
     *
     * @return a list containing the portlet service definitions
     * @see SportletServiceDefinition
     */
    public List getPortletServicesList() {
        return servicesList;
    }

}
