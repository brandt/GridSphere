/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portlet.AbstractPortlet;

public interface RegisteredPortlet {

    public PortletConfig getPortletConfig();

    public PortletSettings getPortletSettings();

    public String getPortletName();

    public AbstractPortlet getActivePortlet();

}
