/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import java.util.Date;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

public abstract class JobStatusMessenger implements JobStatusListener {

    public void jobStatusChanged(Job job) {
        sendMessage(job);
    }

    public abstract void sendMessage(Job job);
}
