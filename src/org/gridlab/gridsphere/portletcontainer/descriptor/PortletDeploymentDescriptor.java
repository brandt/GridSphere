/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Iterator;

public class PortletDeploymentDescriptor {

    public PortletDeploymentDescriptor() {
        load();
    }
    public PortletApp[] getPortletApp();

    public PortletApp createPortletApp() {
        return new PortletApp();
    }

    public void addPortletApp();

    public void load();

    public void save();

}
