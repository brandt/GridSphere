/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponseImpl;
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.services.ServletParsingService;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Iterator;
import java.util.Set;

public interface PortletRegistryService extends PortletService {

    public Set getRegisteredPortlets();

    public Set getRegisteredPortletIDs();

    public RegisteredPortlet getRegisteredPortlet(String portletID);

    public String registerPortlet(RegisteredPortlet registeredPortlet);

    public void unregisterPortlet(String portletID);

}
