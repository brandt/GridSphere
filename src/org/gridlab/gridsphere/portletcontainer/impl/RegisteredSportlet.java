/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;

public interface RegisteredSportlet extends RegisteredPortlet {

    public void setPortletConfig(PortletConfig portletConfig);

    public void setPortletSettings(PortletSettings portletSettings);

    public void setPortletName(String portletName);

    public void setActivePortlet(AbstractPortlet abstractPortlet);

}
