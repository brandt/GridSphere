/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.uniqueid;

import org.gridlab.gridsphere.portlet.service.PortletService;

public interface UniqueIDService extends PortletService {

    public int getUniqueIDAsInteger();

    public String getUniqueIDAsString();

}
