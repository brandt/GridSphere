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
public class PortletPagesList {

    private List pages = new Vector();
    private String concretePortlet = "";
    private String basePageClassDir = "";

    /**
     * Sets the list of portlet service definitions
     *
     * @param pages a <code>Vector</code> containing
     * portlet service definitions
     *
     * @see SportletServiceDefinition
     */
    public void setPortletPages(Vector pages) {
        this.pages = pages;
    }

    /**
     * Returns the list of portlet service definitions
     *
     * @return list containing the portlet service definitions
     *
     * @see SportletServiceDefinition
     */
    public List getPortletPages() {
        return pages;
    }

    public void setConcretePortlet(String concretePortlet) {
        this.concretePortlet = concretePortlet;
    }

    public String getConcretePortlet() {
        return concretePortlet;
    }

    public void setBasePageClassDir(String basePageClassDir) {
        this.basePageClassDir = basePageClassDir;
    }

    public String getBasePageClassDir() {
        return basePageClassDir;
    }

}
