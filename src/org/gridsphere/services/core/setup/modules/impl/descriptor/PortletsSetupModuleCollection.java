/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
package org.gridsphere.services.core.setup.modules.impl.descriptor;

import java.util.List;
import java.util.Vector;

/**
 * The <code>PortletsSetupModuleCollection</code> provides a list of
 * <code>PortletsSetupModuleDefinition</code> entries.
 */
public class PortletsSetupModuleCollection {

    private List modulesList = new Vector();

    /**
     * Sets the list of portlets setup module definitions
     *
     * @param modulesList a <code>Vector</code> containing
     *                     portlets setup module definitions
     * @see PortletsSetupModuleDefinition
     */
    public void setPortletsSetupModulesList(List modulesList) {
        this.modulesList = modulesList;
    }

    /**
     * Returns the list of portlets setup module definitions
     *
     * @return a list containing the portlets setup module definitions
     * @see PortletsSetupModuleDefinition
     */
    public List getPortletsSetupModulesList() {
        return modulesList;
    }
}
