/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

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
