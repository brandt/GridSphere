/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * this one is thrown if PortletDeploymentDescriptor failed
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portlet.PortletException;

public class PortletDeploymentDescriptorException extends PortletException {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(PortletDeploymentDescriptorException.class.getName());

    public  PortletDeploymentDescriptorException () {
        super();
    }

    public  PortletDeploymentDescriptorException (String msg) {
        super(msg);
    }


}

