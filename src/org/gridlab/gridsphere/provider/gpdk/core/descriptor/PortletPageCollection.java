/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.gpdk.core.descriptor;

import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDefinition;

import java.util.List;
import java.util.Vector;

/**
 * The <code>SportletServiceCollection</code> provides a list of
 * <code>SportletServiceDefinition</code> entries.
 */
public class PortletPageCollection {

    private List pagesList = new Vector();

    /**
     * Sets the list of portlet service definitions
     *
     * @param pagesList a <code>Vector</code> containing
     * portlet service definitions
     *
     * @see SportletServiceDefinition
     */
    public void setPortletPagesList(Vector pagesList) {
        this.pagesList = pagesList;
    }

    /**
     * Returns the list of portlet service definitions
     *
     * @return list containing the portlet service definitions
     *
     * @see SportletServiceDefinition
     */
    public List getPortletPagesList() {
        return pagesList;
    }

}
