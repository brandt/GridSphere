/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.parsing;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletApplication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import java.util.List;

public interface ServletParsingService extends PortletService {

    public PortletRequest getPortletRequest(HttpServletRequest request);

    public PortletResponse getPortletResponse(HttpServletResponse response);

    public PortletSettings getPortletSettings(ConcretePortletApplication concreteApp, List knownGroups, List knownRoles);

    public PortletConfig getPortletConfig(ServletConfig config);


}
