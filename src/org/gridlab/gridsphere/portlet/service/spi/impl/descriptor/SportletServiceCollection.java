/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import java.util.Vector;
import java.util.List;

public class SportletServiceCollection {

    protected List servicesList = new Vector();

    public void setPortletServicesList(Vector servicesList) {
        this.servicesList = servicesList;
    }

    public List getPortletServicesList() {
        return servicesList;
    }

}
