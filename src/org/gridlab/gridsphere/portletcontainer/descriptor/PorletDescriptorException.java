/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 *
 * this one is thrown if PortletDeploymentDescriptor failed
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portlet.PortletException;

public class PorletDescriptorException extends PortletException {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(PorletDescriptorException.class.getName());

    public  PorletDescriptorException () {
        super();
    }

    public  PorletDescriptorException (String msg) {
        super(msg);
    }


}

