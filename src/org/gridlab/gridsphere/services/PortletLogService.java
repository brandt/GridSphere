/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Iterator;

/**
 * The AccessControlService provides access control information for users
 * concerning roles they have within the groups they belong to. Because
 * the AccessControlService doesn't modify any access control settings,
 * it is not implemented as a secure service.
 */
public interface PortletLogService extends PortletService {

    /**
     * Return a portlet log
     *
     * @return portlet log
     */
    public PortletLog getPortletLog();

}
